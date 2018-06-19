
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private RangerService		rangerService;

	@Autowired
	private TripService			tripService;
	@Autowired
	private MyMessageService	messageService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		Ranger ranger;

		ranger = this.rangerService.create();

		Assert.isTrue(ranger.getAccountActivated(), "Account activated is not true.");
		Assert.isTrue(!ranger.getSuspicious(), "Supicious must be false.");
		Assert.notNull(ranger.getTrips(), "Trips must be null.");
		Assert.notNull(ranger.getFolders(), "System Folders not created.");
		Assert.isNull(ranger.getAddress(), "Address must be null.");
		Assert.isNull(ranger.getEmail(), "Email must be null.");
		Assert.isNull(ranger.getName(), "Name must be null.");
		Assert.isNull(ranger.getPhone(), "Phone must be null.");
		Assert.notNull(ranger.getReceivedMessages(), "Received messages must be null.");
		Assert.notNull(ranger.getSendedMessages(), "Sended messages must be null.");
		Assert.notNull(ranger.getSocialIdentities(), "Social identities must be null.");
		//		Assert.isNull(ranger.getCurriculum(), "Curriculum must be null.");
		Assert.isNull(ranger.getSurname(), "Surname must be null.");

	}

	@Test
	public void testSave() {
		final Ranger ranger;
		final Ranger saved;
		final Collection<Ranger> rangers;
		super.authenticate("ranger6");
		ranger = this.rangerService.findByPrincipal();

		ranger.setName("rangerName");
		ranger.setSurname("rangerSurname");

		saved = this.rangerService.save(ranger);
		rangers = this.rangerService.findAll();
		Assert.isTrue(rangers.contains(saved), "Ranger not saved. Check data.");

		super.unauthenticate();
	}

	// 35.4 The ratio of rangers who have registered their curricula.
	@Test
	public void testGetRatioOfRangerHaveCurricula() {
		super.authenticate("admin");
		this.rangerService.getRatioOfRangerHaveCurricula();
		super.unauthenticate();
	}

	// 35.4 The ratio of rangers whose curriculum been endorsed.
	@Test
	public void testGetRatioOfRangerWithEndorser() {
		super.authenticate("admin");
		this.rangerService.getRatioOfRangerWithEndorser();
		super.unauthenticate();
	}

	//	35. An actor who is authenticated as an administrator must be able to:
	//		1.An actor is considered suspicious if he or she publishes some data that 
	//			includes a spam word.
	//	@Test
	//	public void testSuspicious() {
	//		final MyMessage result, saved;
	//		Ranger ranger, saved2;
	//		super.authenticate("ranger2");
	//		result = this.messageService.create(25370);// id explorer1
	//
	//		result.setBody("viagra");
	//		result.setPriority(Priority.HIGH);
	//		result.setSubject("viagra");
	//
	//		saved = this.messageService.save(result);
	//		ranger = this.rangerService.findOne(25350);// ID ranger2
	//
	//		saved2 = this.rangerService.save(ranger);
	//		Assert.isTrue(saved2.getSuspicious(), "he isn't suspicious");
	//		super.unauthenticate();
	//	}
	//	
	//	//30.1
	//	@Test
	//	public void testFindRangerByTrip() {
	//		Collection<Ranger> rangers;
	//		final Trip trip = this.tripService.findOne(25765);
	//		rangers = this.rangerService.findRangerByTrip(trip);
	//		Assert.notEmpty(rangers);
	//	}
}
