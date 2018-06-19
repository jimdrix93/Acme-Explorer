
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Folder;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services -------------------------------------------------
	@Autowired
	private FolderService			folderService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private MyMessageService		messageService;
	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private SponsorshipService		sponsorshipService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor ----------------------------------------------------------
	public SponsorService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------
	public Sponsor create() {

		final Sponsor result;
		final Authority aut = new Authority();
		UserAccount useraccount;

		aut.setAuthority("SPONSOR");
		useraccount = this.userAccountService.create();

		result = new Sponsor();

		Collection<Folder> folders = new LinkedList<Folder>();
		folders = this.folderService.systemFolders();
		result.setFolders(folders);

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);
		result.setAccountActivated(true);

		return result;
	}

	public Collection<Sponsor> findAll() {

		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(result);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		Sponsor result;

		final String countryCode = this.configurationService.findCountryCode();
		if (!sponsor.getPhone().contains(countryCode)) {
			final String phone = countryCode + sponsor.getPhone();
			sponsor.setPhone(phone);
		}

		result = this.sponsorRepository.save(sponsor);

		return result;
	}

	// Other methods -----------------------------------------------

	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;

	}

	public Sponsor findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}
}
