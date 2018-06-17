
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.FolderType;
import domain.MyMessage;
import domain.Priority;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@Transactional
public class MyMessageServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private MyMessageService messageService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;

	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("manager2");

		final MyMessage message = this.messageService.create(26274);

		Assert.isNull(message.getBody(), "write the body of the message");
		Assert.isNull(message.getPriority(), "choose the priority");
		Assert.isNull(message.getSubject(), "write the subject of the message");

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		final MyMessage result, saved;
		Collection<MyMessage> messages;

		super.authenticate("manager2");

		result = this.messageService.create(26274);

		result.setBody("body1");
		result.setPriority(Priority.HIGH);
		result.setSubject("subject");

		saved = this.messageService.save(result);
		for (final Folder f : saved.getRecipient().getFolders())
			if (f.getMessages().contains(saved)) {
				messages = this.messageService.findAllByFolder(saved.getRecipient(), f);
				Assert.isTrue(messages.contains(saved), "Folder must not contains this message.");
			}

		Assert.isTrue(saved.getRecipient().getReceivedMessages().contains(saved), "the message isn't not in recipient");
		System.out.println(saved.getRecipient().getReceivedMessages());
		super.unauthenticate();

	}

	// DeleteNoTrash
	@Test
	public void testDelete() {

		super.unauthenticate();
		super.authenticate("explorer1");
		final MyMessage saved = this.messageService.findOne(26664);

		final Actor actor = this.actorService.findByPrincipal();
		Boolean trash = false;
		for (final Folder f : actor.getFolders())
			if (f.getMessages().contains(saved))
				if (!f.getFolderType().equals(FolderType.TRASH_BOX)) {
					f.getMessages().remove(saved);
					this.folderService.saveEdit(f);
				} else {
					trash = true;
					f.getMessages().remove(saved);
				}
		this.messageService.deleteTrash(saved, trash);

		for (final Folder f : actor.getFolders())
			if (f.getFolderType() == FolderType.TRASH_BOX)
				Assert.isTrue(f.getMessages().contains(saved), "Contains message");

		super.unauthenticate();
	}

	// DeleteTrash
	@Test
	public void testDeleteTrash() {

		super.unauthenticate();
		super.authenticate("explorer1");
		final MyMessage saved = this.messageService.findOne(26664);

		final Actor actor = this.actorService.findByPrincipal();
		Boolean trash = false;
		for (final Folder f : actor.getFolders())
			if (f.getMessages().contains(saved))
				if (!f.getFolderType().equals(FolderType.TRASH_BOX)) {
					f.getMessages().remove(saved);
					this.folderService.saveEdit(f);
				} else {
					trash = true;
					f.getMessages().remove(saved);
				}
		this.messageService.deleteTrash(saved, trash);
		// Change the folder in actor
		for (final Folder f : actor.getFolders())
			if (f.getMessages().contains(saved))
				if (!f.getFolderType().equals(FolderType.TRASH_BOX)) {
					f.getMessages().remove(saved);
					this.folderService.saveEdit(f);
				} else {
					trash = true;
					f.getMessages().remove(saved);
				}
		// remove from trash box
		this.messageService.deleteTrash(saved, trash);

		for (final Folder f : actor.getFolders())
			Assert.isTrue(!f.getMessages().contains(saved), "Contains message");

		super.unauthenticate();
	}

	@Test
	public void testBroadcast() {
		super.authenticate("admin");

		boolean found = false;
		final MyMessage m = new MyMessage();

		m.setBody("Esto es un mensaje broadcast.");
		m.setMoment(new Date());
		m.setPriority(Priority.HIGH);
		m.setSubject("Esto es un mensaje broadcast");

		this.messageService.broadcast(m);

		final Collection<Actor> actors = this.actorService.findAll();

		for (final Actor ac : actors) {
			final Collection<Folder> folders = ac.getFolders();

			for (final Folder folder : folders)
				if (folder.getFolderType() == FolderType.NOTIFICATION_BOX)
					for (final MyMessage men : folder.getMessages())
						if (men.getBody() == m.getBody() && men.getSubject() == m.getSubject()) {
							found = true;
							break;
						}
		}

		Assert.isTrue(found);

		super.unauthenticate();
	}

}
