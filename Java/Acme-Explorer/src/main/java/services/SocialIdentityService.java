
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	//Managed repository ------------------------------------------------
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	//Managed services ------------------------------------------------
	@Autowired
	private ActorService				actorService;


	//Constructor -------------------------------------------------------
	public SocialIdentityService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public SocialIdentity create() {
		final SocialIdentity result = new SocialIdentity();

		return result;
	}

	public SocialIdentity findOne(final Integer socialIdentityID) {
		SocialIdentity result;
		result = this.socialIdentityRepository.findOne(socialIdentityID);
		return result;
	}

	public Collection<SocialIdentity> findAll() {
		Collection<SocialIdentity> result;
		result = this.socialIdentityRepository.findAll();
		return result;
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		SocialIdentity result;
		final Actor actor = this.actorService.findByPrincipal();

		result = this.socialIdentityRepository.save(socialIdentity);

		if (!actor.getSocialIdentities().contains(socialIdentity))
			actor.getSocialIdentities().add(result);

		return result;
	}
	public void delete(final SocialIdentity socialIdentity) {
		Assert.isTrue(socialIdentity.getId() != 0);

		final Actor actor = this.actorService.findByPrincipal();

		actor.getSocialIdentities().remove(socialIdentity);

		this.socialIdentityRepository.delete(socialIdentity);
	}
}
