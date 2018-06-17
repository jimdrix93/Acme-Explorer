
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.EmergencyContact;
import domain.Explorer;
import domain.Finder;
import domain.MyMessage;
import domain.SocialIdentity;
import domain.Story;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private FinderService	finderService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		Explorer explorer;

		explorer = this.explorerService.create();

		Assert.isTrue(explorer.getAccountActivated());
		Assert.isNull(explorer.getAddress());
		Assert.isNull(explorer.getEmail());
		Assert.notNull(explorer.getFolders());
		Assert.isNull(explorer.getName());
		Assert.isNull(explorer.getPhone());
		Assert.notNull(explorer.getReceivedMessages());
		Assert.notNull(explorer.getSendedMessages());
		Assert.notNull(explorer.getSocialIdentities());
		//		Assert.isNull(explorer.getApplications());
		Assert.notNull(explorer.getEmergencyContacts());
		//		Assert.isNull(explorer.getFinders());
		//		Assert.isNull(explorer.getStories());
		Assert.isNull(explorer.getSurname());

	}

	@Test
	public void testSave() {
		final Explorer explorer;
		final Explorer saved;
		final Collection<Explorer> explorers;

		explorer = this.explorerService.create();

		explorer.setName("name1");
		explorer.setSurname("surname1");
		explorer.setEmail("email@gmail.com");
		explorer.setPhone("+34(95)6700958");
		explorer.setAddress("address1");
		explorer.setAccountActivated(true);
		explorer.setReceivedMessages(new LinkedList<MyMessage>());
		explorer.setSendedMessages(new LinkedList<MyMessage>());
		explorer.setApplications(new LinkedList<Application>());
		explorer.setEmergencyContacts(new LinkedList<EmergencyContact>());
		explorer.setFinders(new LinkedList<Finder>());
		explorer.setStories(new LinkedList<Story>());
		explorer.setSocialIdentities(new LinkedList<SocialIdentity>());

		saved = this.explorerService.save(explorer);
		explorers = this.explorerService.findAll();
		Assert.isTrue(explorers.contains(saved));
	}

}
