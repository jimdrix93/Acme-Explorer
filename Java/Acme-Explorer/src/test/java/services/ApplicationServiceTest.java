
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.Status;
import domain.Trip;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private TripService tripService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ExplorerService explorerService;

	// Test ------------------------------------------------
	@Test
	public void testCreate() {
		super.authenticate("explorer1");
		Application result;

		final List<Trip> trips = new ArrayList<Trip>(this.tripService.findAll());

		result = this.applicationService.create(trips.get(0).getId());

		Assert.isNull(result.getRejectedReason());

		super.unauthenticate();
	}

	// @Test
	// public void testSave() {
	//
	// super.authenticate("explorer1");
	// Application application = null;
	// Application saved = null;
	// final Collection<Application> applications;
	// final Collection<Application> applicationsExplorer =
	// this.explorerService.findByPrincipal().getApplications();
	//
	// // Id trip1
	// for (final Application ap : applicationsExplorer)
	// application = ap; // Id application1
	//
	// try {
	// saved = this.applicationService.save(application);
	// } catch (final Exception ex) {
	// this.applicationService.findAll();
	// }
	// applications = this.applicationService.findAll();
	// Assert.isTrue(applications.contains(saved), "applications not contains
	// application");
	//
	// super.unauthenticate();
	// }

	@Test
	public void testFindAppliByExplorer() {
		super.authenticate("explorer1");
		Collection<Application> applications;

		applications = this.applicationService.findAppliByExplorer();
		Assert.isTrue(!applications.isEmpty());
		super.unauthenticate();

	}

	@Test
	public void testFindAppliByManager() {
		super.authenticate("manager1");

		final Collection<Application> applications = this.applicationService.findAppliByManager();
		Assert.isTrue(!applications.isEmpty());
		System.out.println(applications);
		super.unauthenticate();

	}

	// The ratio de applications with status PENDING, DUE, ACCEPTEP, CANCELLED
	@Test
	public void testFindRatioWithStatus() {

		super.authenticate("admin");

		Double res;
		res = this.applicationService.getRatioWithStatus(Status.PENDING);
		Assert.notNull(res);

		res = this.applicationService.getRatioWithStatus(Status.DUE);
		Assert.notNull(res);

		res = this.applicationService.getRatioWithStatus(Status.ACCEPTED);
		Assert.notNull(res);

		res = this.applicationService.getRatioWithStatus(Status.CANCELLED);
		Assert.notNull(res);

		super.unauthenticate();
	}
}
