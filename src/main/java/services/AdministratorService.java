
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Manager;
import domain.Ranger;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private FolderService			folderService;
	@Autowired
	private RangerService			rangerService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		UserAccount useraccount;
		final Administrator result;
		final Authority aut = new Authority();

		aut.setAuthority("ADMIN");
		useraccount = this.userAccountService.create();

		result = new Administrator();

		Collection<Folder> folders = new LinkedList<Folder>();
		folders = this.folderService.systemFolders();
		result.setFolders(folders);

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);
		result.setAccountActivated(true);

		return result;
	}

	public Administrator findOne(final int administratorid) {
		Administrator result;
		result = this.administratorRepository.findOne(administratorid);
		//		Assert.notNull(result);
		return result;
	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator adminSave;

		adminSave = this.administratorRepository.save(administrator);

		return adminSave;
	}

	public void delete(final Administrator administrator) {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin, "User must be logged.");
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));
		this.administratorRepository.delete(administrator);
		for (final Folder f : admin.getFolders())
			this.folderService.delete(f);
		if (!admin.getSocialIdentities().isEmpty())
			for (final SocialIdentity s : admin.getSocialIdentities())
				this.socialIdentityService.delete(s);

	}

	// Other methods ---------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Administrator findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}

	public Object findStatisticsApplicationsTrips() {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);

		final Object statistics = this.administratorRepository.statisticsApplicationsTrips();

		return statistics;
	}

	public Object findStatisticsManagerTrips() {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);

		final Object statistics = this.administratorRepository.statisticsManagerTrips();

		return statistics;
	}

	public Object findStatisticsPriceTrips() {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);
		final Object statistics = this.administratorRepository.statisticsPriceTrips();

		return statistics;
	}

	// 35.2
	public void banRanger(final Ranger ranger) {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);
		final Ranger rangerBan = this.rangerService.findOne(ranger.getId());
		Assert.notNull(rangerBan);
		Assert.isTrue(rangerBan.getSuspicious());
		ranger.setAccountActivated(false);
	}

	// 35.2
	public void banManager(final Manager manager) {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);
		final Manager managerBan = this.managerService.findOne(manager.getId());
		Assert.notNull(managerBan);
		Assert.isTrue(managerBan.getSuspicious());
		manager.setAccountActivated(false);
	}

	// 35.3
	public void unBanRanger(final Ranger ranger) {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);
		final Ranger rangerBan = this.rangerService.findOne(ranger.getId());
		Assert.notNull(rangerBan);
		Assert.isTrue(!ranger.getAccountActivated());
		ranger.setAccountActivated(true);
		ranger.setSuspicious(false);
	}
	// 35.3
	public void unBanManager(final Manager manager) {
		final Administrator admin = this.findByPrincipal();
		Assert.notNull(admin);
		final Manager managerBan = this.managerService.findOne(manager.getId());
		Assert.notNull(managerBan);
		Assert.isTrue(!manager.getAccountActivated());
		manager.setAccountActivated(true);
		manager.setSuspicious(false);
	}
}
