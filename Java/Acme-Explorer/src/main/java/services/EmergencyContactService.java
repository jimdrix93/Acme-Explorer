
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EmergencyContactRepository;
import domain.EmergencyContact;
import domain.Explorer;

@Service
@Transactional
public class EmergencyContactService {

	//Managed repository ------------------------------------------------
	@Autowired
	private EmergencyContactRepository	emergencyContactRepository;

	//Managed services ------------------------------------------------
	@Autowired
	private ExplorerService				explorerService;
	@Autowired
	private ConfigurationService		configurationService;


	//Constructor -------------------------------------------------------
	public EmergencyContactService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public EmergencyContact create() {
		final EmergencyContact result = new EmergencyContact();

		return result;
	}

	public EmergencyContact findOne(final Integer emergencyContactID) {
		EmergencyContact result;
		result = this.emergencyContactRepository.findOne(emergencyContactID);
		return result;
	}

	public Collection<EmergencyContact> findAll() {
		Collection<EmergencyContact> result;
		result = this.emergencyContactRepository.findAll();
		return result;
	}

	public EmergencyContact save(final EmergencyContact emergencyContact) {
		EmergencyContact result;
		Assert.notNull(emergencyContact);
		final Explorer explorer = this.explorerService.findByPrincipal();
		final String countryCode = this.configurationService.findCountryCode();
		if (!emergencyContact.getPhone().contains(countryCode)) {
			final String phone = countryCode + emergencyContact.getPhone();
			emergencyContact.setPhone(phone);
		}

		result = this.emergencyContactRepository.save(emergencyContact);
		if (emergencyContact.getId() == 0)
			explorer.getEmergencyContacts().add(result);

		return result;
	}
	public void delete(final EmergencyContact emergencyContact) {
		Assert.notNull(emergencyContact);
		Assert.isTrue(emergencyContact.getId() != 0);

		final Explorer explorer = this.explorerService.findByPrincipal();
		explorer.getEmergencyContacts().remove(emergencyContact);

		this.emergencyContactRepository.delete(emergencyContact);
	}
}
