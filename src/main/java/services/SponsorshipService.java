package services;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import repositories.SponsorshipRepository;

@Service
@Transactional
public class SponsorshipService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private SponsorshipRepository sponsorshipRepository;

	@Autowired
	private TripService tripService;

	@Autowired
	private SponsorService sponsorService;

	// Supporting services -------------------------------------------------

	// Constructor ----------------------------------------------------------
	public SponsorshipService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------
	public Sponsorship create() {

		final Sponsorship result;

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll() {

		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Sponsorship> findAllByPrincipal(final int sponsorId) {

		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAllBySponsor(sponsorId);
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship saved;

		saved = this.sponsorshipRepository.save(sponsorship);

		if (sponsorship.getId() == 0) {
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			sponsor.getSponsorships().add(saved);
			this.sponsorService.save(sponsor);
		}

		return saved;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other methods -----------------------------------------------
	// Requeriment 45
	public Sponsorship getRandomSponsorship() {
		Sponsorship sponsorship;
		final Collection<Sponsorship> sponsorships = this.findAll();
		final Random randomGenerator = new Random();

		final int index = randomGenerator.nextInt(sponsorships.size());
		sponsorship = (Sponsorship) sponsorships.toArray()[index];

		return sponsorship;

	}

	public Sponsorship enterCreditCard(final int sponsorshipId, final CreditCard cc) {
		Assert.notNull(cc);
		Sponsorship saved;

		final Sponsorship sp = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(sp);

		sp.setCreditCard(cc);

		saved = this.sponsorshipRepository.save(sp);

		return saved;
	}

}
