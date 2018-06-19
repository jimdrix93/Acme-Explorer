
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Status;
import domain.Trip;
import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	// Managed repositories -------------------------------------------------
	@Autowired
	private ApplicationRepository applicationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService tripService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private MyMessageService messageService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private StatusService statusService;
	@Autowired
	CreditCardService creditCardService;

	// Constructor ----------------------------------------------------------
	public ApplicationService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	// 13.1 An actor who is authenticated as an explorer must be able to:
	// Apply for a trip.
	public Application create(final int tripId) {
		final Application result;
		final Date moment;
		Explorer applicant;
		Trip trip;

		trip = this.tripService.findOne(tripId);

		result = new Application();
		moment = new Date(System.currentTimeMillis() - 1);
		applicant = this.explorerService.findByPrincipal();

		result.setMoment(moment);
		result.setApplicant(applicant);
		result.setTrip(trip);
		result.setStatus(Status.PENDING);

		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int applicationID) {
		Application result;

		result = this.applicationRepository.findOne(applicationID);
		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();
		return result;
	}

	@Transactional(propagation = Propagation.NEVER)
	public Application save(final Application application) {
		Assert.notNull(application); // application que quiere guardar
		Application savedApplication; // application una vez guardada
		Application oldApplication = null; // application existente en bbdd
											// (antes de
		// haberla modificado el usuario)
		Trip trip;
		Explorer explorer; // explorer de la application
		Explorer logedExplorer; // explorer logado
		Manager manager; // manager de la application
		Manager logedManager; // manager logado

		trip = application.getTrip();
		manager = trip.getManager();
		explorer = application.getApplicant();

		if (application.getId() != 0) {
			oldApplication = this.applicationRepository.findOne(application.getId());

			try {
				logedManager = this.managerService.findByPrincipal();
				Assert.isTrue(logedManager.getId() == manager.getId(), "Cannot change this application.");

				// El estado nuevo ha de estar dentro de los posibles a los que
				// puede pasar desde el estado que tenia anteriormente
				Assert.isTrue(
						this.getPossibleStatusManager(oldApplication.getStatus()).containsKey(application.getStatus()),
						"Cannot change the application's status");
			} catch (final Exception e) {
			}

			try {
				logedExplorer = this.explorerService.findByPrincipal();
				Assert.isTrue(logedExplorer.getId() == explorer.getId(), "Cannot change this application.");

				// El estado nuevo ha de estar dentro de los posibles a los que
				// puede pasar desde el estado que tenia anteriormente
				Assert.isTrue(
						this.getPossibleStatusExplorer(oldApplication.getStatus()).containsKey(application.getStatus()),
						"Cannot change the application's status");
			} catch (final Exception e) {
			}
		}

		final Date moment = new Date(System.currentTimeMillis() - 1);
		if (application.getId() == 0) {
			application.setStatus(Status.PENDING);
			application.setMoment(moment);

		}

		savedApplication = this.applicationRepository.save(application);

		if (application.getId() == 0) {
			trip.getApplications().add(savedApplication);
			this.tripService.save(trip);

			explorer.getApplications().add(savedApplication);
			this.explorerService.save(explorer);
		}

		if (application.getId() != 0)
			if (oldApplication.getStatus() != savedApplication.getStatus())
				this.messageService.sendNotificationByChangeStatus(savedApplication.getTrip().getManager(),
						savedApplication.getApplicant(), savedApplication);

		return savedApplication;
	}

	// Other business methods -------------------------------------------------
	public Collection<Application> findAppliByTrip(final int managerId) {
		// TODO
		final Collection<Application> result = new LinkedList<Application>();
		final Collection<Trip> trips = this.tripService.findTripsByManager(managerId);

		for (final Trip t : trips) {
			for (final Application a : t.getApplications())
				result.add(a);
			break;
		}
		return result;
	}

	public Collection<Application> findAppliByManager() {
		final Manager manager = this.managerService.findByPrincipal();

		Assert.notNull(manager);
		final Collection<Application> result = this.applicationRepository.findAppliByManager(manager.getId());

		Assert.notNull(result);
		return result;
	}

	// 13.2 An actor who is authenticated as an explorer must be able to:
	// List the applications that he or she made, grouped by status.
	public Collection<Application> findAppliByExplorer() {
		final Explorer applicant = this.explorerService.findByPrincipal();

		Assert.notNull(applicant);
		final Collection<Application> result = this.applicationRepository.findAppliByExplorer(applicant.getId());

		Assert.notNull(result);
		return result;
	}

	// 13.3 An actor who is authenticated as an explorer must be able to:
	// Enter a credit card to get an application accepted, as long as its status
	// is DUE
	public Application enterCreditCard(final int applicationId, final CreditCard cc) {
		Assert.notNull(cc);
		Application saved;

		final Application ap = this.applicationRepository.findOne(applicationId);
		Assert.notNull(ap);

		final Explorer applicant = this.explorerService.findByPrincipal();

		Assert.isTrue(ap.getApplicant().getId() == applicant.getId());

		ap.setCreditCard(cc);
		if (ap.getStatus() == Status.DUE)
			ap.setStatus(Status.ACCEPTED);

		saved = this.applicationRepository.save(ap);
		this.messageService.sendNotificationByChangeStatus(ap.getTrip().getManager(), ap.getApplicant(), ap);

		return saved;
	}

	public Double getRatioWithStatus(final Status status) {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Double res = this.applicationRepository.findRatioWithStatus(status);

		return res;
	}

	public HashMap<Status, String> getPossibleStatusManager(final Status status) {

		final Manager manager = this.managerService.findByPrincipal();
		Assert.notNull(manager);

		final HashMap<Status, String> possibleStatus = new HashMap<Status, String>();

		switch (status) {
		case PENDING:
			possibleStatus.put(Status.PENDING, this.statusService.getStatusToString(Status.PENDING));
			possibleStatus.put(Status.REJECTED, this.statusService.getStatusToString(Status.REJECTED));
			possibleStatus.put(Status.DUE, this.statusService.getStatusToString(Status.DUE));
			break;
		default:
			possibleStatus.put(status, this.statusService.getStatusToString(status));
		}
		return possibleStatus;
	}

	public HashMap<Status, String> getPossibleStatusExplorer(final Status status) {

		final Explorer explorer = this.explorerService.findByPrincipal();
		Assert.notNull(explorer);

		final HashMap<Status, String> possibleStatus = new HashMap<Status, String>();

		switch (status) {
		case ACCEPTED:
			possibleStatus.put(Status.ACCEPTED, "ACCEPTED");
			possibleStatus.put(Status.CANCELLED, "CANCELLED");
			break;
		default:
			possibleStatus.put(status, this.statusService.getStatusToString(status));
		}
		return possibleStatus;
	}
}
