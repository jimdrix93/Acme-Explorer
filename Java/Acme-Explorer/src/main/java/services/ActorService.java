
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Folder;

@Service
@Transactional
public class ActorService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private ActorRepository		actorRepository;

	// Services -------------------------------------------------------------
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor create() {
		UserAccount useraccount;
		final Administrator result;
		final Authority aut = new Authority();

		aut.setAuthority(Authority.EXPLORER);
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

	public Actor findOne(final int actorId) {
		Actor result;
		result = this.actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;

		result = this.actorRepository.save(actor);
		return result;
	}

	public void delete(final Actor actor) {
		assert actor != null;
		assert actor.getId() != 0;

		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------
	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;

	}

	public Actor findByUserName(final String username) {
		Assert.isTrue(username != "");

		final UserAccount us = this.userAccountService.findByUserName(username);
		Assert.notNull(us);

		return this.findByUserAccount(us);
	}

}
