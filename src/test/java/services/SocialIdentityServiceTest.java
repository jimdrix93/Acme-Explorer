
package services;

import java.text.ParseException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private SocialIdentityService	socialIdentityService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.create();

		Assert.isNull(socialIdentity.getLink());
		Assert.isNull(socialIdentity.getNick());
		Assert.isNull(socialIdentity.getPhoto());
		Assert.isNull(socialIdentity.getSocialNetwork());

	}

	@Test
	public void testSave() throws ParseException {
		final SocialIdentity socialIdentity;
		final SocialIdentity saved;
		final Collection<SocialIdentity> socialIdentitys;

		super.authenticate("manager1");

		socialIdentity = this.socialIdentityService.create();

		socialIdentity.setSocialNetwork("Faisbuuk");
		socialIdentity.setNick("Pepe lopez");
		socialIdentity.setLink("http://www.noseque.com");
		socialIdentity.setPhoto("http://www.noseque.com");

		saved = this.socialIdentityService.save(socialIdentity);
		socialIdentitys = this.socialIdentityService.findAll();
		Assert.isTrue(socialIdentitys.contains(saved));
		super.unauthenticate();
	}
	@Test
	public void testDelete() throws ParseException {
		final SocialIdentity socialIdentity;
		final SocialIdentity saved;
		final Collection<SocialIdentity> socialIdentitys;

		super.authenticate("manager1");

		socialIdentity = this.socialIdentityService.create();

		socialIdentity.setSocialNetwork("Faisbuuk");
		socialIdentity.setNick("Pepe lopez");
		socialIdentity.setLink("http://www.noseque.com");
		socialIdentity.setPhoto("http://www.noseque.com");

		saved = this.socialIdentityService.save(socialIdentity);
		this.socialIdentityService.delete(saved);
		socialIdentitys = this.socialIdentityService.findAll();

		Assert.isTrue(!socialIdentitys.contains(socialIdentity));
		Assert.isTrue(!socialIdentitys.contains(saved));
		super.unauthenticate();
	}
}
