
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.EmergencyContact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.MyMessage;
import domain.SocialIdentity;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class ExplorerService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private ExplorerRepository		explorerRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private TripService				tripService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private MyMessageService		messageService;
	@Autowired
	private StoryService			storyService;
	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -------------------------------------------------------------

	public ExplorerService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Explorer create() {

		// Para ver si es un admin o alguien no logeado quien lo crea se mira en
		// los controladores

		UserAccount useraccount;
		final Explorer result;
		final Authority authority = new Authority();

		authority.setAuthority("EXPLORER");
		useraccount = this.userAccountService.create();
		final Collection<Authority> authorities = new LinkedList<Authority>();
		authorities.add(authority);
		useraccount.setAuthorities(authorities);

		result = new Explorer();

		Collection<Folder> folders = new LinkedList<Folder>();
		folders = this.folderService.systemFolders();
		result.setFolders(folders);

		final Collection<Application> applications = new LinkedList<Application>();
		final Collection<EmergencyContact> emergencyContacts = new LinkedList<EmergencyContact>();
		final Collection<Finder> finders = new LinkedList<Finder>();
		final Collection<MyMessage> receivedMessages = new LinkedList<MyMessage>();
		final Collection<MyMessage> sendedMessages = new LinkedList<MyMessage>();
		final Collection<SocialIdentity> socialIdentities = new LinkedList<SocialIdentity>();
		final Collection<Story> stories = new LinkedList<Story>();

		result.setApplications(applications);
		result.setEmergencyContacts(emergencyContacts);
		result.setFinders(finders);
		result.setReceivedMessages(receivedMessages);
		result.setSendedMessages(sendedMessages);
		result.setSocialIdentities(socialIdentities);
		result.setStories(stories);

		result.setUserAccount(useraccount);
		result.setAccountActivated(true);

		return result;
	}

	public Collection<Explorer> findAll() {

		Collection<Explorer> result;

		result = this.explorerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Explorer findOne(final int explorerId) {
		Explorer result;

		result = this.explorerRepository.findOne(explorerId);
		Assert.notNull(result);

		return result;
	}

	public Explorer save(final Explorer explorer) {
		Assert.notNull(explorer);
		Explorer result;
		if (explorer.getId() == 0) {
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();

			explorer.getUserAccount().setPassword(encoder.encodePassword(explorer.getUserAccount().getPassword(), null));
		}

		final String countryCode = this.configurationService.findCountryCode();
		if (!explorer.getPhone().contains(countryCode)) {
			final String phone = countryCode + explorer.getPhone();
			explorer.setPhone(phone);
		}

		result = this.explorerRepository.saveAndFlush(explorer);

		return result;
	}

	// Other business methods -------------------------------------------------
	public Explorer findByPrincipal() {
		Explorer result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Explorer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Explorer result;
		result = this.explorerRepository.findByUserAccountId(userAccount.getId());
		return result;

	}

	public Explorer findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}

	public Explorer findOneToEdit(final int explorerId) {
		// TODO Auto-generated method stub
		Explorer result, explorer2;
		result = this.explorerRepository.findOne(explorerId);
		explorer2 = this.findByPrincipal();
		Assert.isTrue(explorer2.equals(result));
		return result;
	}

	// 34.1
	public Collection<Finder> findFindersExplorer(final Explorer explorer) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(explorer.getUserAccount().equals(userAccount));
		final Collection<Finder> res = this.finderService.findFindersByExplorer(explorer);
		return res;
	}

	// 34.1 ...that is, the trips that meet the search criteria.
	public Collection<Trip> findTripsFinder(final Finder finder) {
		final Collection<Trip> res = this.tripService.findTripsByFinder(finder);
		return res;
	}
}
