
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import repositories.CreditCardRepository;

@Service
@Transactional
public class CreditCardService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SponsorshipService sponsorshipService;

	// Constructor -------------------------------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public CreditCard create() {
		final CreditCard result = new CreditCard();

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;
		result = this.creditCardRepository.findOne(creditCardId);
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> creditCards;

		creditCards = this.creditCardRepository.findAll();
		return creditCards;
	}

	public CreditCard save(final CreditCard creditCard) {
		CreditCard result = null;

		result = this.creditCardRepository.save(creditCard);
		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		this.creditCardRepository.delete(creditCard);

	}
}
