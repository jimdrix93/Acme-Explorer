
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Administrator;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------
	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	// Requeriments 43 & 44
	public Configuration findOne(final int configurationId) {
		Configuration configuration;
		configuration = this.configurationRepository.findOne(configurationId);
		Assert.notNull(configuration);
		return configuration;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> configurations;
		configurations = this.configurationRepository.findAll();
		Assert.notNull(configurations);
		return configurations;
	}

	public Configuration save(final Configuration configuration) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Configuration saved;

		saved = this.configurationRepository.save(configuration);

		Assert.notNull(saved, "Configuration haven`t been saved.");

		return saved;
	}

	//Other methods -----------------------------------------------------

	public Collection<String> findAllSpamWord() {
		final Collection<String> spamWord = new LinkedList<String>();
		for (final Configuration c : this.configurationRepository.findAll())
			spamWord.addAll(c.getSpamWord());
		Assert.notEmpty(spamWord, "There aren't spam words");
		return spamWord;
	}

	public double findVatTax() {
		double res;

		res = this.configurationRepository.findVatTax();
		Assert.notNull(res, "VatTax must not be null");
		return res;
	}

	public String findCountryCode() {
		String result;

		result = this.configurationRepository.findCountryCode();
		Assert.notNull(result, "Country code must not be null");

		return result;
	}

	public String findWelcomeMessage(final String locale) {
		String result = null;

		switch (locale) {
		case "es":
			result = this.configurationRepository.findWelcomeMessageEs();
			break;
		case "en":
			result = this.configurationRepository.findWelcomeMessageEn();
			break;
		}

		return result;
	}

	public String findLogo() {
		String result;

		result = this.configurationRepository.findLogo();

		return result;
	}

	public Integer findCacheTime() {
		Integer result;
		result = this.configurationRepository.findCacheTime();

		return result;
	}

	public Integer findSizeFinder() {
		Integer result;
		result = this.configurationRepository.findSizeFinder();

		return result;
	}

}
