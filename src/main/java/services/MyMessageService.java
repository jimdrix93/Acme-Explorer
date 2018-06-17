
package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Explorer;
import domain.Folder;
import domain.FolderType;
import domain.Manager;
import domain.MyMessage;
import domain.Priority;
import domain.Status;
import repositories.MyMessageRepository;

@Service
@Transactional
public class MyMessageService {

	// Managed repository ------------------------------------------------
	@Autowired
	private MyMessageRepository messageRepository;

	// Supporting services ------------------------------------------------
	@Autowired
	private FolderService folderService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private StatusService statusService;

	// Constructor -------------------------------------------------------
	public MyMessageService() {
		super();
	}

	// Methods CRUD ------------------------------------------------------
	public MyMessage create(final Integer recipientId) {
		Assert.notNull(recipientId, "recipient must not be null");
		Assert.isTrue(recipientId != 0, "recipient must be in DB");

		final MyMessage result = new MyMessage();
		Actor recipient;
		Actor sender;

		// Message recipient
		recipient = this.actorService.findOne(recipientId);
		Assert.notNull(recipient, "you can´t send a message whitout recipient");
		// setter recipient
		result.setRecipient(recipient);
		result.setCopy(false);

		// Find sender´s folder
		sender = this.actorService.findByPrincipal();
		result.setSender(sender);

		// Moment of sending
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public MyMessage findOne(final Integer messageID) {
		MyMessage result;
		Actor actor;
		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "user not valid");

		result = this.messageRepository.findOne(messageID);
		Assert.notNull(result, "must not be null");

		return result;
	}

	public MyMessage save(final MyMessage message) {
		MyMessage saved, savedCopy;
		final MyMessage copyMessage = new MyMessage();
		Actor sender;

		// message constraints
		Assert.notNull(message, "message must not be null");
		Assert.notNull(message.getSender(), "sender must not be null");
		Assert.notNull(message.getBody(), "write the body of the message");
		Assert.notNull(message.getPriority(), "choose the priority");
		Assert.notNull(message.getSubject(), "write the subject of the message");

		// Sender
		sender = this.actorService.findByPrincipal();
		Assert.isTrue(sender.equals(message.getSender()), "this isn't your message");
		Assert.isTrue(message.getId() == 0, "you can't edit the message");

		// Message moment
		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);

		// Copy Message
		copyMessage.setBody(message.getBody());
		copyMessage.setMoment(message.getMoment());
		copyMessage.setPriority(message.getPriority());
		copyMessage.setRecipient(message.getRecipient());
		copyMessage.setSender(message.getSender());
		copyMessage.setSubject(message.getSubject());
		copyMessage.setCopy(true);

		Folder folderSender = new Folder();
		Folder folderRecipient = new Folder();
		int spam = 0;

		// SpamWord
		for (final String s : this.configurationService.findAllSpamWord())
			if (message.getBody().contains(s) || message.getSubject().contains(s))
				spam++;

		if (spam > 0) {
			folderRecipient = this.folderService.findFolderOfActor(message.getRecipient(), "spam");
			folderSender = this.folderService.findFolderOfActor(copyMessage.getSender(), "spam");

		} else {
			folderRecipient = this.folderService.findFolderOfActor(message.getRecipient(), "in");
			folderSender = this.folderService.findFolderOfActor(copyMessage.getSender(), "out");
		}

		// Saved messages
		saved = this.messageRepository.save(message);
		savedCopy = this.messageRepository.save(copyMessage);

		// Add message to folder
		folderRecipient.getMessages().add(saved);
		this.folderService.saveWithMessage(folderRecipient, saved.getRecipient());
		folderSender.getMessages().add(savedCopy);
		this.folderService.saveWithMessage(folderSender, sender);

		Assert.notNull(saved, "message must not be null.");
		Assert.notNull(savedCopy, "messageCopy must not be null.");

		return saved;
	}

	public void delete(final MyMessage message) {
		Assert.notNull(message, "this message must not be null");
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "user not valid");

		for (final Folder f : actor.getFolders())
			if (f.getMessages().contains(message))
				if (f.getFolderType().equals(FolderType.TRASH_BOX)) {
					// Remove the folder's message
					f.getMessages().remove(message);
					this.folderService.saveEdit(f);
					this.messageRepository.delete(message.getId());
					break;
				} else {
					final Folder trashFolder = this.folderService.findFolderOfActor(actor, "trash");
					f.getMessages().remove(message);
					trashFolder.getMessages().add(message);
					this.folderService.saveEdit(f);
					this.folderService.saveEdit(trashFolder);
					break;
				}

	}

	public void deleteTrash(final MyMessage message, final Boolean trash) {
		Assert.notNull(message, "this message must not be null");
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "user not valid");
		Folder folderTrash = null;

		if (trash)
			this.messageRepository.delete(message.getId());
		else
			for (final Folder f : actor.getFolders())
				if (f.getFolderType().equals(FolderType.TRASH_BOX)) {
					folderTrash = f;
					folderTrash.getMessages().add(message);
					this.folderService.saveEdit(folderTrash);
					break;
				}
	}
	// Others methods ---------------------------------------------------

	public void broadcast(final MyMessage m) {
		// Solo puede hacerlo un administrador
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Collection<Actor> actors = this.actorService.findAll();

		for (final Actor ac : actors) {
			final Collection<Folder> folders = ac.getFolders();

			for (final Folder folder : folders) {
				// Para todos los actores menos el administrador que lo envia
				if (folder.getFolderType() == FolderType.NOTIFICATION_BOX && ac.getId() != admin.getId()) {
					final MyMessage newMessage = this.create(ac.getId());
					MyMessage saved;
					newMessage.setBody(m.getBody());
					newMessage.setMoment(m.getMoment());
					newMessage.setPriority(m.getPriority());
					newMessage.setRecipient(ac);
					newMessage.setSender(admin);
					newMessage.setSubject(m.getSubject());

					saved = this.messageRepository.save(newMessage);
					folder.getMessages().add(saved);
					this.folderService.saveWithMessage(folder, ac);
					break;
				}

				// Para el administrador: añado el mensaje de broadcast al out
				// box
				if (folder.getFolderType() == FolderType.OUT_BOX && ac.getId() == admin.getId()) {
					final MyMessage newMessage = this.create(ac.getId());
					MyMessage saved;
					newMessage.setBody("BROADCAST MESSAGE: " + m.getBody());
					newMessage.setMoment(m.getMoment());
					newMessage.setPriority(m.getPriority());
					newMessage.setRecipient(ac);
					newMessage.setSender(admin);
					newMessage.setSubject("BROADCAST: " + m.getSubject());

					saved = this.messageRepository.save(newMessage);
					folder.getMessages().add(saved);
					this.folderService.saveWithMessage(folder, ac);
					break;
				}
			}

		}

		// Añadimos a enviados el mensaje broadcast

	}

	public Collection<MyMessage> findAllByFolder(final Actor actor, final Folder folder) {
		Collection<MyMessage> result = new LinkedList<MyMessage>();
		Assert.notNull(actor, "user not valid");

		result = this.messageRepository.findAllByFolder(actor.getId(), folder.getName());

		return result;
	}

	// @Transactional(propagation = Propagation.NEVER)
	public void sendNotificationByChangeStatus(final Manager manager, final Explorer explorer, final Application ap) {
		final MyMessage savedNotificationToManager;
		final MyMessage savedNotificationToExplorer;

		Assert.notNull(manager, "Manager must not be null");
		Assert.notNull(explorer, "Explorer must not be null");

		final MyMessage notificationToManager = new MyMessage();
		final MyMessage notificationToExplorer = new MyMessage();

		notificationToManager.setMoment(new Date(System.currentTimeMillis() - 1));
		notificationToExplorer.setMoment(new Date(System.currentTimeMillis() - 1));

		notificationToManager.setSender(explorer);
		notificationToManager.setRecipient(manager);

		notificationToExplorer.setSender(manager);
		notificationToExplorer.setRecipient(explorer);

		notificationToExplorer.setCopy(false);
		notificationToManager.setCopy(false);

		notificationToExplorer.setPriority(Priority.NEUTRAL);
		notificationToManager.setPriority(Priority.NEUTRAL);

		final String subject = "Status change on application's trip with ticker: " + ap.getTrip().getTicker();
		notificationToManager.setSubject(subject);
		notificationToExplorer.setSubject(subject);

		notificationToManager.setBody("The " + explorer.getName() + "'s application changed the status to "
				+ this.statusService.getStatusToString(ap.getStatus()) + ".");
		notificationToExplorer.setBody("Your application has changed the status to "
				+ this.statusService.getStatusToString(ap.getStatus()) + ".");

		if (ap.getStatus() == Status.REJECTED) {
			notificationToManager.setBody(notificationToManager.getBody() + " " + ap.getRejectedReason());
			notificationToExplorer.setBody(notificationToExplorer.getBody() + " " + ap.getRejectedReason());
		}

		savedNotificationToManager = this.messageRepository.save(notificationToManager);
		savedNotificationToExplorer = this.messageRepository.save(notificationToExplorer);

		for (final Folder f : manager.getFolders())
			if (f.getFolderType().equals(FolderType.NOTIFICATION_BOX)) {
				f.getMessages().add(savedNotificationToManager);
				this.folderService.saveWithMessage(f, manager);
				break;
			}

		for (final Folder f : explorer.getFolders())
			if (f.getFolderType().equals(FolderType.NOTIFICATION_BOX)) {
				f.getMessages().add(savedNotificationToExplorer);
				this.folderService.saveWithMessage(f, explorer);
				break;
			}

	}

	public Collection<MyMessage> findReceivedMessages() {
		final Actor actor = this.actorService.findByPrincipal();
		Collection<MyMessage> result;
		result = this.messageRepository.findRecipientMessagesByActor(actor.getId());
		return result;
	}

	public Collection<MyMessage> findSendedMessages() {
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<MyMessage> result;

		result = this.messageRepository.findSendedMessagesByActor(actor.getId());

		return result;
	}

	public Collection<MyMessage> findAllMessagesByActor(final Actor actor) {

		final Collection<MyMessage> messages = new LinkedList<MyMessage>();
		final Collection<Folder> folders = this.folderService.findFolderByActor(actor);

		for (final Folder f : folders)
			messages.addAll(f.getMessages());

		return messages;
	}
}
