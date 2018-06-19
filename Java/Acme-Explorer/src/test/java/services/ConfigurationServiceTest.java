
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	// Tests ----------------------------------------------
	// Requeriments 36 & 37
	@Test
	public void testSave() {
		super.authenticate("admin");
		Configuration configuration = null;
		final Configuration savedConfiguration;
		Collection<Configuration> configurations = this.configurationService.findAll();

		for (final Configuration c : configurations) {
			configuration = c;
			break;
		}

		configuration.setFinderCached(3);
		configuration.setFinderReturn(100);

		savedConfiguration = this.configurationService.save(configuration);
		configurations = this.configurationService.findAll();
		Assert.notNull(savedConfiguration);
		Assert.isTrue(configurations.contains(savedConfiguration));
		super.unauthenticate();
	}

}
