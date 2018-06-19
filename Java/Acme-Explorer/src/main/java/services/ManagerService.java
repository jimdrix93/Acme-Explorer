
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Auditor;
import domain.Folder;
import domain.Manager;
import domain.MyMessage;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed repositories ---------------------------------------------
	@Autowired
	private ManagerRepository		managerRepository;
	// Supporting services ----------------------------------------------------
	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private NoteService				noteService;

	// Supporting services ---------------------------------------------
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor ------------------------------------------------------
	public ManagerService() {
		super();
	}

	// Methods CRUD -----------------------------------------------------
	public Manager create() {

		final Manager result;
		UserAccount useraccount;

		final Authority aut = new Authority();

		aut.setAuthority("MANAGER");
		useraccount = this.userAccountService.create();

		result = new Manager();

		Collection<Folder> folders = new LinkedList<Folder>();
		folders = this.folderService.systemFolders();
		result.setFolders(folders);

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);
		result.setAccountActivated(true);
		result.setSuspicious(false);

		return result;

	}

	public Collection<Manager> findAll() {

		Collection<Manager> result;

		result = this.managerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);
		Assert.notNull(result);

		return result;
	}

	public Manager save(final Manager manager) {

		if (manager.getId() == 0) {
			final Administrator admin = this.administratorService.findByPrincipal();
			Assert.notNull(admin);
			Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();

			manager.getUserAccount().setPassword(encoder.encodePassword(manager.getUserAccount().getPassword(), null));
		}
		Assert.notNull(manager);
		Manager result;

		this.suspicious(manager);

		final String countryCode = this.configurationService.findCountryCode();
		if (!manager.getPhone().contains(countryCode)) {
			final String phone = countryCode + manager.getPhone();
			manager.setPhone(phone);
		}

		result = this.managerRepository.save(manager);

		return result;
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);

		this.managerRepository.delete(manager);
	}

	// Other methods ---------------------------------------------------

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);

		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {

		Manager result;

		result = this.managerRepository.findByUserAccountId(userAccount.getId());

		return result;

	}

	public Manager findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}

	public Manager findOneToEdit(final int managerId) {
		// TODO Auto-generated method stub
		Manager result, manager2;
		result = this.managerRepository.findOne(managerId);
		manager2 = this.findByPrincipal();
		Assert.isTrue(manager2.equals(result));
		return result;
	}

	// 32.1
	public Collection<Note> findNotesByAuditor(final Auditor auditor) {
		Assert.notNull(auditor);
		final Collection<Note> res = this.noteService.findNotesByAuditor(auditor);
		return res;
	}

	// 32.1
	public void writeReplyNote(final Note note, final String reply) {
		Assert.notNull(note);
		note.setReply(reply);
	}

	// 35.4 The ratio of suspicious managers.
	public Double getRatioOfManagersSupicious() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double ratio;
		ratio = this.managerRepository.getRatioOfManagersSupicious();
		Assert.isTrue(0.0 <= ratio && ratio <= 100.0, "Ratio of manager supicious must be between (0,100)");
		return ratio;
	}

	// 35.1
	public Collection<Manager> findManagerBySuspicious(final Boolean suspicious) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Collection<Manager> res = this.managerRepository.findManagerBySuspicious(suspicious);
		return res;
	}

	public void suspicious(final Manager manager) {
		final Collection<String> spam = this.configurationService.findAllSpamWord();
		final LinkedList<String> strings = new LinkedList<String>();

		if (manager.getTrips().isEmpty())
			for (final Trip trip : manager.getTrips()) {
				strings.add(trip.getDescription());
				strings.add(trip.getTitle());
				strings.add(trip.getCancelationReason());
				strings.add(trip.getRequirements());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						manager.setSuspicious(true);
						break;
					}
			}

		final LinkedList<MyMessage> sendedMessages = new LinkedList<MyMessage>(manager.getSendedMessages());

		if (!sendedMessages.isEmpty())
			for (final MyMessage m : sendedMessages) {
				strings.add(m.getBody());
				strings.add(m.getSubject());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						manager.setSuspicious(true);
						break;
					}
			}

	}
}
