
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Audit;
import domain.Category;
import domain.HasValue;
import domain.Manager;
import domain.Note;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private TripService			tripService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private LegalTextService	legalTextService;
	@Autowired
	private StageService		stageService;
	@Autowired
	private RangerService		rangerService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private FolderService		folderService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		Trip trip;
		super.authenticate("manager1");
		Manager manager;

		manager = this.managerService.findByPrincipal();
		manager.setFolders(this.folderService.systemFolders());
		Assert.notNull(manager);

		trip = this.tripService.create();

		Assert.notNull(trip.getTicker());
		Assert.isNull(trip.getTitle());
		Assert.isNull(trip.getDescription());
		Assert.isNull(trip.getRequirements());
		Assert.isNull(trip.getStartTrip());
		Assert.isNull(trip.getEndTrip());
		Assert.isNull(trip.getCancelationReason());
		Assert.isTrue(!trip.getCancelated());
		Assert.isNull(trip.getCategory());
		Assert.isNull(trip.getApplications());
		Assert.isNull(trip.getNotes());
		Assert.isNull(trip.getAudits());
		// Assert.isNull(trip.getSponsorship());
		Assert.isNull(trip.getRanger());
		Assert.notNull(trip.getManager());
		Assert.isNull(trip.getStages());
		Assert.isNull(trip.getSurvivalClasses());
		Assert.isNull(trip.getStories());
		Assert.isNull(trip.getHasValues());
		Assert.isNull(trip.getPublicationDate());

		super.unauthenticate();
	}

	@Test
	public void testSave() throws ParseException {
		final Trip trip;
		final Trip saved;
		final Stage saved2;
		final Collection<Trip> trips;

		super.authenticate("manager1");

		trip = this.tripService.create();
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		final Date publicationDate = sdf2.parse("2018/02/01 20:00");
		final Date startTrip = sdf2.parse("2018/05/01 20:00");
		final Date endTrip = sdf2.parse("2018/05/06 20:00");

		final Stage stage = new Stage();
		stage.setDescription("description");
		stage.setPrice(25.0);
		stage.setTitle("title");
		saved2 = this.stageService.save(stage);

		trip.setApplications(new LinkedList<Application>());
		trip.setAudits(new LinkedList<Audit>());
		trip.setCancelated(false);
		trip.setCategory(this.categoryService.findOne(26607)); // ID DB
		trip.setDescription("description");
		trip.setEndTrip(endTrip);
		trip.setStartTrip(startTrip);
		trip.setHasValues(new LinkedList<HasValue>());
		trip.setNotes(new LinkedList<Note>());
		trip.setRequirements(new String());
		trip.setStages(new LinkedList<Stage>());
		trip.getStages().add(saved2);
		trip.setStories(new LinkedList<Story>());
		trip.setSurvivalClasses(new LinkedList<SurvivalClass>());
		//		trip.setTicker("180225-XXPS");
		trip.setTitle("title");
		trip.setRanger(this.rangerService.findOne(26248)); // ID DB
		// trip.setSponsorship(this.sponsorshipService.getRandomSponsorship());
		trip.setPublicationDate(publicationDate);
		trip.setLegalText(this.legalTextService.findOne(26637)); //ID DB

		saved = this.tripService.save(trip);
		trips = this.tripService.findAll();
		Assert.isTrue(trips.contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		Trip trip;
		super.authenticate("manager1");
		trip = this.tripService.findOne(26678);

		this.tripService.delete(trip);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<Trip> trips;
		trips = this.tripService.findAll();
		Assert.notNull(trips);
		Assert.notEmpty(trips);
	}

	@Test
	public void testFindTripByCategory() {
		super.authenticate("admin");
		final Category category = this.categoryService.findOne(26610);
		Collection<Trip> trips;
		trips = this.tripService.findTripByCategory(category.getId());
		Assert.notEmpty(trips, "trips is empty");
		super.unauthenticate();
	}

	// 35.4 The ratio of trips with an audit record.
	@Test
	public void testGetRatioOfTripsWithAnAudit() {
		super.authenticate("admin");
		Double ratio;
		ratio = this.tripService.getRatioOfTripsWithAnAudit();
		Assert.notNull(ratio);
		super.unauthenticate();
	}

	// 14.6.4 The average, the minimum, the maximum, and the standard deviation
	// of
	// the number trips guided per ranger.
	@Test
	public void testTripsPerRanger() {
		super.authenticate("admin");
		Object result;
		result = this.tripService.tripsPerRanger();
		Assert.notNull(result);
		super.unauthenticate();
	}

	@Test
	public void testFindTripByKeyword() {
		final String s = "";
		Collection<Trip> trips;
		trips = this.tripService.findTripByKeyword(s);
		Assert.notEmpty(trips, "trips is empty");
	}
}
