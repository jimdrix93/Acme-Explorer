
package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import domain.Application;
import domain.Explorer;
import domain.Manager;
import domain.Status;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class SurvivalClassService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	// Supporting services -------------------------------------------------
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private TripService				tripService;
	@Autowired
	private ExplorerService			explorerService;


	// Constructor ----------------------------------------------------------
	public SurvivalClassService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------
	public SurvivalClass create(final int tripId) {
		final SurvivalClass result;
		Trip trip;

		trip = this.tripService.findOne(tripId);

		result = new SurvivalClass();
		result.setTrip(trip);
		this.checkManagerPrincipal(result);
		final Date moment = new Date(System.currentTimeMillis() - 1);
		result.setMoment(moment);

		trip.getSurvivalClasses().add(result);
		this.tripService.save(trip);

		return result;
	}

	public Collection<SurvivalClass> findAll() {

		Collection<SurvivalClass> result;

		result = this.survivalClassRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SurvivalClass findOne(final int survivalClassId) {
		SurvivalClass result;

		result = this.survivalClassRepository.findOne(survivalClassId);
		Assert.notNull(result);

		return result;
	}

	public SurvivalClass save(final SurvivalClass survivalClass) {
		Assert.notNull(survivalClass);
		SurvivalClass saved;

		// Si vamos a editar una survival class que ya existe tenemos que
		// comprobar que pertenezca al manager del trip
		if (survivalClass.getId() != 0)
			this.checkManagerPrincipal(survivalClass);

		saved = this.survivalClassRepository.save(survivalClass);

		// Reflejamos los cambios en Trip
		final Trip trip = saved.getTrip();
		trip.getSurvivalClasses().add(saved);

		// Crear save de Trip
		this.tripService.save(trip);

		final Date moment = new Date(System.currentTimeMillis() - 1);
		saved.setMoment(moment);

		return saved;
	}

	public void delete(final SurvivalClass survivalClass) {
		this.checkManagerPrincipal(survivalClass);
		this.survivalClassRepository.delete(survivalClass);
	}

	// Other methods ---------------------------------------------------
	public void checkManagerPrincipal(final SurvivalClass survivalClass) {
		Manager manager;
		manager = this.managerService.findByPrincipal();
		Assert.isTrue(survivalClass.getTrip().getManager().equals(manager));
	}

	public void checkManagerPrincipal(final Collection<SurvivalClass> survivalClasses) {
		Manager manager;
		manager = this.managerService.findByPrincipal();
		for (final SurvivalClass survivalClass : survivalClasses)
			Assert.isTrue(survivalClass.getTrip().getManager().equals(manager));
	}

	// 43.1 Manage the survival classes that are associated with his or her
	// trips
	public Collection<SurvivalClass> findByTrip(final int tripId) {
		Collection<SurvivalClass> survivalClasses;
		survivalClasses = this.survivalClassRepository.findByTrips(tripId);

		return survivalClasses;
	}

	public void checkSurvivalClassExplorerPrincipal(final SurvivalClass survivalClass, final Explorer explorer) {

		for (final Application a : survivalClass.getTrip().getApplications())
			if (explorer.getApplications().contains(a)) {
				Assert.isTrue(a.getApplicant().equals(explorer), "applicant not equals explorer");
				Assert.isTrue(a.getStatus().equals(Status.ACCEPTED), "application not be accepted");

			}
	}

	// 44.1
	public void enrolASurvivalClass(final int survivalClassId) {

		final Explorer explorer = this.explorerService.findByPrincipal();
		final SurvivalClass survivalClass = this.survivalClassRepository.findOne(survivalClassId);
		this.checkSurvivalClassExplorerPrincipal(survivalClass, explorer);

		final LinkedList<Explorer> explorers = new LinkedList<Explorer>();
		explorers.addAll(survivalClass.getExplorers());
		explorers.add(explorer);
		survivalClass.setExplorers(explorers);

		final SurvivalClass saved = this.survivalClassRepository.save(survivalClass);
		Assert.notNull(saved);
	}

}
