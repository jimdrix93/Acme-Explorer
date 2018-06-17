
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.FolderType;
import domain.MyMessage;

@Service
@Transactional
public class FolderService {

	// Managed repository ----------------------------------------------------
	@Autowired
	private FolderRepository	folderRepository;

	//Supporting services -----------------------------------------------------
	@Autowired
	private MyMessageService	messageService;
	@Autowired
	private ActorService		actorService;


	// Constructor -----------------------------------------------------------
	public FolderService() {
		super();
	}

	// methods CRUD ---------------------------------------------------------
	public Folder create() {
		final Folder folder = new Folder();

		final FolderType type = FolderType.CUSTOM;
		folder.setFolderType(type);

		folder.setChilds(new LinkedList<Folder>());
		folder.setMessages(new LinkedList<MyMessage>());

		return folder;
	}

	public Folder findOne(final Integer folderID) {
		Folder result;
		result = this.folderRepository.findOne(folderID);
		Assert.notNull(result);
		return result;
	}

	public Collection<Folder> findAll() {
		final Collection<Folder> result;
		result = this.folderRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		final Actor actor = this.actorService.findByPrincipal();

		Folder saved;
		saved = this.folderRepository.save(folder);
		actor.getFolders().add(saved);
		Assert.isTrue(actor.getFolders().contains(saved));

		this.actorService.save(actor);

		return saved;

	}

	public Folder saveWithParent(final Folder folder, final Folder parent) {
		Assert.notNull(folder);
		final Actor actor = this.actorService.findByPrincipal();

		Folder saved;
		saved = this.folderRepository.save(folder);
		actor.getFolders().add(saved);
		Assert.isTrue(actor.getFolders().contains(saved));

		parent.getChilds().add(saved);
		this.folderRepository.save(parent);

		this.actorService.save(actor);

		return saved;

	}

	public Folder saveMoving(final Folder folder, final Collection<Folder> folders) {
		Assert.notNull(folder);

		for (final MyMessage myMessage : folder.getMessages())
			for (final Folder oldFolder : folders)
				if (oldFolder.getMessages().contains(myMessage)) {
					oldFolder.getMessages().remove(myMessage);
					this.folderRepository.save(oldFolder);
				}

		Folder saved;
		saved = this.folderRepository.save(folder);

		return saved;

	}
	public Folder saveWithMessage(final Folder folder, final Actor actor) {
		Assert.notNull(folder);
		Assert.isTrue(actor.getFolders().contains(folder));

		Folder saved;
		saved = this.folderRepository.save(folder);

		Assert.isTrue(actor.getFolders().contains(saved));

		return saved;

	}

	public Folder saveEdit(final Folder folder) {
		Assert.notNull(folder);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getFolders().contains(folder));

		Folder saved;
		saved = this.folderRepository.save(folder);

		Assert.isTrue(actor.getFolders().contains(saved));

		return saved;

	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(folder.getFolderType().equals(FolderType.CUSTOM));
		Assert.isTrue(folder.getChilds().isEmpty(), "Folder contains folder childs");
		assert folder.getId() != 0;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getFolders().contains(folder));

		if (this.isChild(folder)) {
			final Folder folderParent = this.folderRepository.findFolderParent(folder.getId());
			folderParent.getChilds().remove(folder);
		}

		actor.getFolders().remove(folder);
		this.actorService.save(actor);
		this.folderRepository.delete(folder);
	}

	// Other methods --------------------------------------------------------

	public Collection<Folder> systemFolders() {

		final LinkedList<Folder> folders = new LinkedList<Folder>();
		Folder folderIn_Box, folderOut_Box, folderNotification_Box, folderTrash_Box, folderSpam_Box;
		final Folder savedFolderIn_Box, savedFolderOut_Box, savedFolderNotification_Box, savedFolderTrash_Box, savedFolderSpam_Box;

		folderIn_Box = new Folder();
		Assert.notNull(folderIn_Box);
		folderOut_Box = new Folder();
		Assert.notNull(folderOut_Box);
		folderNotification_Box = new Folder();
		Assert.notNull(folderNotification_Box);
		folderTrash_Box = new Folder();
		Assert.notNull(folderTrash_Box);
		folderSpam_Box = new Folder();
		Assert.notNull(folderSpam_Box);

		final LinkedList<Folder> childs1 = new LinkedList<Folder>();
		final LinkedList<Folder> childs2 = new LinkedList<Folder>();
		final LinkedList<Folder> childs3 = new LinkedList<Folder>();
		final LinkedList<Folder> childs4 = new LinkedList<Folder>();
		final LinkedList<Folder> childs5 = new LinkedList<Folder>();

		final LinkedList<MyMessage> messages1 = new LinkedList<MyMessage>();
		final LinkedList<MyMessage> messages2 = new LinkedList<MyMessage>();
		final LinkedList<MyMessage> messages3 = new LinkedList<MyMessage>();
		final LinkedList<MyMessage> messages4 = new LinkedList<MyMessage>();
		final LinkedList<MyMessage> messages5 = new LinkedList<MyMessage>();

		folderIn_Box.setChilds(childs1);
		folderIn_Box.setMessages(messages1);
		folderIn_Box.setName("in_box");
		folderIn_Box.setFolderType(FolderType.IN_BOX);

		folderOut_Box.setChilds(childs2);
		folderOut_Box.setMessages(messages2);
		folderOut_Box.setName("out_box");
		folderOut_Box.setFolderType(FolderType.OUT_BOX);

		folderNotification_Box.setChilds(childs3);
		folderNotification_Box.setMessages(messages3);
		folderNotification_Box.setName("notification_box");
		folderNotification_Box.setFolderType(FolderType.NOTIFICATION_BOX);

		folderTrash_Box.setChilds(childs4);
		folderTrash_Box.setMessages(messages4);
		folderTrash_Box.setName("trash_box");
		folderTrash_Box.setFolderType(FolderType.TRASH_BOX);

		folderSpam_Box.setChilds(childs5);
		folderSpam_Box.setMessages(messages5);
		folderSpam_Box.setName("spam_box");
		folderSpam_Box.setFolderType(FolderType.SPAM_BOX);

		savedFolderIn_Box = this.folderRepository.save(folderIn_Box);
		savedFolderOut_Box = this.folderRepository.save(folderOut_Box);
		savedFolderNotification_Box = this.folderRepository.save(folderNotification_Box);
		savedFolderTrash_Box = this.folderRepository.save(folderTrash_Box);
		savedFolderSpam_Box = this.folderRepository.save(folderSpam_Box);

		Assert.notNull(savedFolderIn_Box);
		Assert.notNull(savedFolderOut_Box);
		Assert.notNull(savedFolderNotification_Box);
		Assert.notNull(savedFolderTrash_Box);
		Assert.notNull(savedFolderSpam_Box);

		folders.add(savedFolderIn_Box);
		folders.add(savedFolderOut_Box);
		folders.add(savedFolderNotification_Box);
		folders.add(savedFolderTrash_Box);
		folders.add(savedFolderSpam_Box);

		return folders;

	}

	public Folder findFolderOfActor(final Actor sender, final String name) {
		Collection<Folder> folders;
		folders = this.folderRepository.findFoldersOfActor(sender.getId(), name);

		return folders.iterator().next();
	}

	public Collection<Folder> findFolderByActor(final Actor actor) {
		Collection<Folder> folders;
		folders = this.folderRepository.findFoldersByActor(actor.getId());

		return folders;
	}

	public Folder findByMessage(final MyMessage message) {
		Assert.notNull(message, "message must not be null");
		Folder folder;

		folder = this.folderRepository.findByMessage(message.getId());

		return folder;
	}

	public Collection<Folder> findCustomFolderByActor(final Actor actor) {
		Collection<Folder> customFolders;
		customFolders = this.folderRepository.findCustomFolderByActor(actor.getId(), FolderType.CUSTOM);

		return customFolders;
	}

	public boolean isChild(final Folder folder) {
		boolean result = false;
		final Folder folderParent = this.folderRepository.findFolderParent(folder.getId());
		if (folderParent != null)
			if (folderParent.getChilds().contains(folder))
				result = true;

		return result;
	}
}
