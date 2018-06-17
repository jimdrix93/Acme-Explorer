
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.Administrator;
import domain.LegalText;

@Service
@Transactional
public class LegalTextService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private LegalTextRepository		legalTextRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService			managerService;


	// Constructor -------------------------------------------------------------

	public LegalTextService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public LegalText create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final LegalText lt = new LegalText();

		//Moment of create a LegalText
		lt.setMoment(new Date(System.currentTimeMillis() - 1));

		return lt;
	}

	public LegalText findOne(final int legalTextID) {

		LegalText result;

		result = this.legalTextRepository.findOne(legalTextID);
		return result;
	}

	public Collection<LegalText> findAll() {

		Collection<LegalText> result;

		result = this.legalTextRepository.findAll();
		return result;
	}

	public LegalText save(final LegalText legalText) {

		if (legalText.getId() == 0) {
			final Administrator admin = this.administratorService.findByPrincipal();
			Assert.notNull(admin);
		}
		LegalText saved;

		//Moment of create a LegalText
		legalText.setMoment(new Date(System.currentTimeMillis() - 1));

		saved = this.legalTextRepository.save(legalText);

		return saved;
	}

	public void delete(final LegalText legalText) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(legalText);
		Assert.isTrue(legalText.getFinalMode() == false, "Can´t remove a final mode legalText");

		this.legalTextRepository.delete(legalText);
	}

	// Other business methods -------------------------------------------------

	public Collection<Object> timesReferenced() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Collection<Object> result;

		result = this.legalTextRepository.timesReferenced();

		return result;
	}

	public Collection<LegalText> findAllValid() {
		Collection<LegalText> result;

		result = this.legalTextRepository.findAllValid();
		return result;
	}
}
