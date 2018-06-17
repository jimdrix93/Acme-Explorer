
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.MyMessage;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private RangerService			rangerService;


	// Tests ----------------------------------------------
	@Test
	public void testCreate() {

		Administrator administrator;

		administrator = this.administratorService.create();

		Assert.isTrue(administrator.getAccountActivated());
		Assert.isNull(administrator.getAddress());
		Assert.isNull(administrator.getEmail());
		Assert.notNull(administrator.getFolders());
		Assert.isNull(administrator.getName());
		Assert.isNull(administrator.getSurname());
		Assert.isNull(administrator.getPhone());
		Assert.isNull(administrator.getReceivedMessages());
		Assert.isNull(administrator.getSendedMessages());
		Assert.isNull(administrator.getSocialIdentities());
	}

	@Test
	public void testSave() {
		final Administrator administrator;
		final Administrator saved;
		final Collection<Administrator> administrators;

		administrator = this.administratorService.create();

		administrator.setName("name1");
		administrator.setSurname("surname1");
		administrator.setEmail("email@gmail.com");
		administrator.setPhone("+34(95)6700958");
		administrator.setAddress("address1");
		administrator.setAccountActivated(true);
		administrator.setReceivedMessages(new LinkedList<MyMessage>());
		administrator.setSendedMessages(new LinkedList<MyMessage>());
		administrator.setSocialIdentities(new LinkedList<SocialIdentity>());

		saved = this.administratorService.save(administrator);
		administrators = this.administratorService.findAll();
		Assert.isTrue(administrators.contains(saved));
		Assert.isTrue(!administrators.contains(administrator), "OldAdministrator mustn't be in db.");

	}

	// 35.1
	@Test
	public void testFindManagerBySuspicious() {
		super.authenticate("admin");
		this.managerService.findManagerBySuspicious(true);
		super.unauthenticate();
	}

	// 35.1
	@Test
	public void testFindRangerBySuspicious() {
		super.authenticate("admin");
		this.rangerService.findRangerBySuspicious(true);
		super.unauthenticate();
	}

	//	// 35.2
	//	@Test
	//	public void testBanManager() {
	//		super.authenticate("admin");
	//		this.administratorService.banManager(this.managerService.findOne(25360)); // id manager
	//																					// bd
	//		Assert.isTrue(!this.managerService.findOne(25360).getAccountActivated());
	//		super.unauthenticate();
	//	}

	// 35.2
	//	@Test
	//	public void testBanRanger() {
	//		super.authenticate("admin");
	//		this.administratorService.banRanger(this.rangerService.findOne(25351)); // id
	//																				// manager
	//																				// bd
	//		Assert.isTrue(!this.rangerService.findOne(25351).getAccountActivated());
	//		super.unauthenticate();
	//	}

	//	// 35.3
	//	@Test
	//	public void testUnBanManager() {
	//		super.authenticate("admin");
	//		this.administratorService.banManager(this.managerService.findOne(25360));// id manager bd
	//		this.administratorService.unBanManager(this.managerService.findOne(25360));
	//		Assert.isTrue(this.managerService.findOne(25360).getAccountActivated());
	//		super.unauthenticate();
	//	}

	//	// 35.3
	//	@Test
	//	public void testUnBanRanger() {
	//		super.authenticate("admin");
	//		this.administratorService.banRanger(this.rangerService.findOne(25351)); // id
	//																				// manager
	//																				// bd
	//		this.administratorService.unBanRanger(this.rangerService.findOne(25351));
	//		Assert.isTrue(this.rangerService.findOne(25351).getAccountActivated());
	//		super.unauthenticate();
	//	}

	@Test
	public void testFindStatisticsApplicationsTrips() {

		super.authenticate("admin");

		final Object statistics = this.administratorService.findStatisticsApplicationsTrips();

		Assert.notNull(statistics);
		super.unauthenticate();
	}

	@Test
	public void testFindStatisticsManagerTrips() {
		super.authenticate("admin");

		final Object statistics = this.administratorService.findStatisticsManagerTrips();

		Assert.notNull(statistics);
		super.unauthenticate();
	}

	@Test
	public void testFindStatisticsPriceTrips() {
		super.authenticate("admin");

		final Object statistics = this.administratorService.findStatisticsPriceTrips();

		Assert.notNull(statistics);
		super.unauthenticate();
	}
}
