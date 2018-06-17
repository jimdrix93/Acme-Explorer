
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Administrator;
import domain.Application;
import domain.Audit;
import domain.Category;
import domain.Finder;
import domain.HasValue;
import domain.Manager;
import domain.Note;
import domain.Ranger;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TripService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private TripRepository			tripRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private RangerService			rangerService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private HasValueService			hasValueService;
	@Autowired
	private StoryService			storyService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private AuditService			auditService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private StageService			stageService;
	//	@Autowired
	//	private SponsorshipService		sponsorshipService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private SurvivalClassService	survivalClassService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private TagService				tagService;


	// Constructors -----------------------------------------------------
	public TripService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Trip create() {
		final Manager manager = this.managerService.findByPrincipal();
		Assert.notNull(manager, "manager must not be null");

		final Trip trip = new Trip();

		trip.setCancelated(false);
		trip.setManager(manager);
		trip.setTicker(this.generateTicker());
		manager.getTrips().add(trip);

		return trip;
	}
	// Ancillary method for generate Ticker
	public String generateTicker() {
		final Collection<Trip> trips = this.tripRepository.findAll();
		final Collection<String> tickers = new LinkedList<>();
		for (final Trip t : trips)
			tickers.add(t.getTicker());

		final Date date = new Date(System.currentTimeMillis() - 1);
		final SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
		final Random r = new Random();
		String randomLetter = "";
		String ticker = "";
		while (tickers.contains(ticker) || ticker == "") {
			for (int i = 0; i < 4; i++)
				randomLetter += String.valueOf((char) (r.nextInt(26) + 'A'));

			ticker = dt.format(date).toString() + "-" + randomLetter;
		}

		return ticker;
	}

	public Trip findOne(final int tripId) {
		Trip result;
		result = this.tripRepository.findOne(tripId);
		return result;
	}

	public Trip findOneToEdit(final int tripId) {
		Trip result;
		result = this.tripRepository.findOne(tripId);
		this.checkPrincipal(result);
		return result;
	}

	public Collection<Trip> findAll() {
		final Collection<Trip> result;
		result = this.tripRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Trip save(final Trip trip) {

		final Manager manager;
		final Trip saved;

		Assert.notNull(trip);

		Assert.isTrue(trip.getPublicationDate().before(trip.getStartTrip()), "the publication date can not be after the start date");
		Assert.isTrue(trip.getStartTrip().before(trip.getEndTrip()), "the start date can not be after the end date");
		Assert.isTrue(trip.getStartTrip().after(new Date(System.currentTimeMillis() - 1)));
		Assert.isTrue(!trip.getCategory().getName().equals("CATEGORY"), "Category not be the fictitious category called \"CATEGORY\".");

		double price = 0.0;
		for (final Stage stage : trip.getStages()) {
			stage.setUsed(true);
			price += stage.getPrice() + (stage.getPrice() * (this.configurationService.findVatTax() / 100));
		}
		trip.setPrice(price);

		saved = this.tripRepository.save(trip);

		manager = trip.getManager();
		manager.getTrips().add(saved);

		if (!trip.getAudits().isEmpty())
			for (final Audit au : trip.getAudits()) {
				au.setTrip(saved);
				this.auditService.save(au, trip);
			}
		if (!trip.getHasValues().isEmpty())
			for (final HasValue hv : trip.getHasValues()) {
				hv.setTrip(trip);
				this.hasValueService.save(hv);
			}
		if (!trip.getApplications().isEmpty())
			for (final Application ap : trip.getApplications())
				if (!ap.getTrip().equals(saved)) {
					ap.setTrip(saved);
					this.applicationService.save(ap);
				}

		if (trip.getStartTrip().before(new Date(System.currentTimeMillis() - 1))) {
			final Trip tripBD = this.findOne(trip.getId());
			Assert.isTrue(trip.getCancelated() == tripBD.getCancelated());
		}

		if (trip.getCancelated() == true)
			Assert.isTrue(!trip.getCancelationReason().equals(""), "If cancelated was selected, write a reason.");

		this.managerService.save(manager);

		return saved;
	}
	public void delete(final Trip trip) {
		assert trip != null;
		assert trip.getId() != 0;

		Assert.isTrue(this.tripRepository.exists(trip.getId()), "Trip does not exist in the database");
		final Date date = new Date(System.currentTimeMillis() - 1);
		Assert.isTrue(trip.getPublicationDate().compareTo(date) > 0, "can't delete a published trip");

		Manager manager;
		Category category;
		Ranger ranger;
		//		final Sponsorship sponsorship;
		this.checkPrincipal(trip);

		manager = this.managerService.findByPrincipal();
		manager.getTrips().remove(trip);
		this.managerService.save(manager);

		category = trip.getCategory();
		category.getTrips().remove(trip);
		this.categoryService.save(category);

		if (trip.getRanger() != null) {
			ranger = trip.getRanger();
			ranger.getTrips().remove(trip);
			this.rangerService.save(ranger);
		}

		//		if (trip.getSponsorship() != null) {
		//			sponsorship = trip.getSponsorship();
		//			sponsorship.getTrips().remove(trip);
		//			this.sponsorshipService.save(sponsorship);
		//		}

		for (final Stage stage : trip.getStages()) {
			trip.getStages().remove(stage);
			this.stageService.delete(stage);
		}
		if (!trip.getAudits().isEmpty())
			for (final Audit audit : trip.getAudits())
				this.auditService.delete(audit);
		if (!trip.getNotes().isEmpty())
			for (final Note note : trip.getNotes())
				this.noteService.delete(note);
		if (!trip.getStories().isEmpty())
			for (final Story story : trip.getStories())
				this.storyService.delete(story);
		if (!trip.getHasValues().isEmpty())
			for (final HasValue hasValue : trip.getHasValues())
				this.hasValueService.delete(hasValue);
		if (!trip.getSurvivalClasses().isEmpty())
			for (final SurvivalClass survivalClass : trip.getSurvivalClasses())
				this.survivalClassService.delete(survivalClass);

		this.tripRepository.delete(trip);
	}
	// Other business methods -------------------------------------------------

	public Collection<Trip> findTripByKeyword(final String word) {
		Collection<Trip> result = new ArrayList<Trip>();

		result = this.tripRepository.findTripByKeyword(word);

		return result;
	}

	public Collection<Trip> findTripByCategory(final int category) {
		Collection<Trip> result;
		result = this.tripRepository.findTripByCategory(category);

		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findTripsByManager(final int managerId) {
		Collection<Trip> result;
		result = this.managerService.findOne(managerId).getTrips();
		Assert.notNull(result);
		return result;
	}

	public void cancelTrip(final Trip trip) {
		final Date current = new Date(System.currentTimeMillis());
		Assert.isTrue(!trip.getCancelated());
		if (trip.getStartTrip().after(current))
			trip.setCancelated(true);
		Assert.notNull(trip.getCancelationReason());
	}

	public void checkPrincipal(final Trip trip) {
		Manager manager;
		manager = this.managerService.findByPrincipal();
		Assert.isTrue(trip.getManager().equals(manager));
	}

	// 35.4 The ratio of trips with an audit record.
	public Double getRatioOfTripsWithAnAudit() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double ratio;
		ratio = this.tripRepository.findByAnAudit();
		Assert.isTrue(0.0 <= ratio && ratio <= 100.0);
		return ratio;
	}

	// 34.1 ...that is, the trips that meet the search criteria.
	public Collection<Trip> findTripsByFinder(final Finder finder) {
		Collection<Trip> res;
		res = this.tripRepository.findTripsByFinder(finder.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Trip> findPublicatedTrips() {
		Collection<Trip> trips;
		trips = this.tripRepository.findPublicatedTrips();
		return trips;
	}

	public Object tripsPerRanger() {
		final Administrator admin = this.administratorService.findByPrincipal();

		final Object result = this.tripRepository.tripsPerRanger();

		return result;
	}

	public Double getRatioOfTripsCancelled() {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double res;

		res = this.tripRepository.findRatioOfTripCancelled();

		return res;
	}

	public Collection<Object> getTripsWithMoreApplications() {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Collection<Object> res = this.tripRepository.findTripsWithMoreApplications();

		return res;
	}

	public Collection<Tag> getPossibleTags() {
		final Collection<Tag> tags = this.tagService.findAll();
		return tags;
	}
}
