
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
import domain.Manager;
import domain.MyMessage;
import domain.SocialIdentity;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private AuditorService		auditorService;
	@Autowired
	private NoteService			noteService;
	@Autowired
	private MyMessageService	messageService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		Manager manager;

		manager = this.managerService.create();

		Assert.isTrue(manager.getAccountActivated());
		Assert.isNull(manager.getAddress());
		Assert.isNull(manager.getEmail());
		Assert.notNull(manager.getFolders());
		Assert.isNull(manager.getName());
		Assert.isNull(manager.getPhone());
		Assert.isNull(manager.getReceivedMessages());
		Assert.isNull(manager.getSendedMessages());
		Assert.isNull(manager.getSocialIdentities());
		Assert.isTrue(!manager.getSuspicious());
		Assert.isNull(manager.getTrips());
		Assert.isNull(manager.getSurname());

	}

	@Test
	public void testSave() {
		super.authenticate("admin");

		final Manager manager;
		final Manager saved;
		final Collection<Manager> managers;

		manager = this.managerService.create();

		manager.setName("name1");
		manager.setSurname("surname1");
		manager.setEmail("email@gmail.com");
		manager.setPhone("+34(95)6700958");
		manager.setAddress("address1");
		manager.setSuspicious(false);
		manager.setAccountActivated(true);
		manager.setReceivedMessages(new LinkedList<MyMessage>());
		manager.setSendedMessages(new LinkedList<MyMessage>());
		manager.setTrips(new LinkedList<Trip>());
		manager.setSocialIdentities(new LinkedList<SocialIdentity>());

		saved = this.managerService.save(manager);
		managers = this.managerService.findAll();
		Assert.isTrue(managers.contains(saved));

		super.unauthenticate();
	}
	@Test
	public void testDelete() {

		super.authenticate("admin");

		Manager manager;
		final Manager manager2;
		final Collection<Manager> managers;

		manager = this.managerService.create();

		manager.setName("name1");
		manager.setSurname("surname1");
		manager.setEmail("email@gmail.com");
		manager.setPhone("+34(95)6700958");
		manager.setAddress("address1");
		manager.setSuspicious(false);
		manager.setAccountActivated(true);
		manager.setReceivedMessages(new LinkedList<MyMessage>());
		manager.setSendedMessages(new LinkedList<MyMessage>());
		manager.setTrips(new LinkedList<Trip>());
		manager.setSocialIdentities(new LinkedList<SocialIdentity>());

		manager2 = this.managerService.save(manager);
		this.managerService.delete(manager2);
		managers = this.managerService.findAll();

		Assert.isTrue(!managers.contains(manager2));
		Assert.isTrue(!managers.contains(manager));

		super.unauthenticate();
	}

	// 35.4 The ratio of suspicious managers.
	@Test
	public void testGetRatioOfManagerSupicious() {
		super.authenticate("admin");
		this.managerService.getRatioOfManagersSupicious();
		super.unauthenticate();
	}
	//	// 32.1
	//	@Test
	//	public void testFindNotesByAuditor() {
	//		super.authenticate("manager");
	//		this.managerService.findNotesByAuditor(this.auditorService.findOne(25733)); //ID auditor de bd
	//		super.unauthenticate();
	//	}
	// 32.1
	//	@Test
	//	public void testWriteReplyNote() {
	//		this.managerService.writeReplyNote(this.noteService.findOne(25776), "reply cambiada");
	//		Assert.isTrue(this.noteService.findOne(25776).getReply().equals("reply cambiada"));
	//	}

	//	35. An actor who is authenticated as an administrator must be able to:
	//		1.An actor is considered suspicious if he or she publishes some data that 
	//			includes a spam word.
	//	@Test
	//	public void testSuspicious() {
	//		final MyMessage result, saved;
	//		Manager manager, saved2;
	//		super.authenticate("manager2");
	//		result = this.messageService.create(25370);
	//
	//		result.setBody("viagra");
	//		result.setPriority(Priority.HIGH);
	//		result.setSubject("viagra");
	//
	//		saved = this.messageService.save(result);
	//		manager = this.managerService.findOne(25360);
	//
	//		saved2 = this.managerService.save(manager);
	//		Assert.isTrue(saved2.getSuspicious(), "he isn't suspicious");
	//		super.unauthenticate();
	//	}

}
