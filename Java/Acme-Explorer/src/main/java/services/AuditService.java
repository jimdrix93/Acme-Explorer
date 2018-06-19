
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private AuditRepository			auditRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private TripService tripService;


	// Constructor -------------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Audit create(Integer tripId) {
		final Audit result = new Audit();
		final Auditor auditor = this.auditorService.findByPrincipal();

		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setFinalMode(false);
		result.setMoment(moment);
		result.setTrip(tripService.findOne(tripId));
		result.setAuditor(auditor);

		return result;
	}

	public Audit findOne(final int auditId) {
		Audit result;
		result = this.auditRepository.findOne(auditId);
		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> Audits;

		Audits = this.auditRepository.findAll();
		return Audits;
	}

	public Audit save(final Audit audit) {
		Audit result = null;

		if (audit.getId() != 0) {
			Audit oldAudit = this.auditRepository.findOne(audit.getId());
			Assert.isTrue(!oldAudit.isFinalMode(), "You cannot edit an Audit in final mode!");
		}
		
		final Auditor auditor = this.auditorService.findByPrincipal();
		Assert.notNull(auditor);
		result = this.auditRepository.save(audit);

		return result;
	}
	
	public Audit save(final Audit audit, final Trip trip) {
		Audit result = null;
		Assert.notNull(audit);
		
		if (audit.getId() != 0) {
			Audit oldAudit = this.auditRepository.findOne(audit.getId());
			Assert.isTrue(!oldAudit.isFinalMode(), "You cannot edit an Audit in final mode!");
		}
		
		Assert.isTrue(audit.getId() > 0);
		Assert.notNull(trip);

		result = this.auditRepository.save(audit);

		return result;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);
		//33.2 Once they are saved in final mode, they cannot be modified or deleted.
		//Assert.isTrue(!audit.isFinalMode());
		final Auditor auditor = audit.getAuditor();
		if (!audit.isFinalMode()) {
			auditor.getAudits().remove(audit);
			this.auditRepository.delete(audit);
			this.auditorService.save(auditor);

		}

	}

	// Other business methods -------------------------------------------------

	//33.2
	public Collection<Audit> findAuditByAuditor(final Auditor auditor) {
		Collection<Audit> result;
		result = this.auditRepository.findAuditByAuditor(auditor.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Audit> findAuditsByTrip(final Trip trip) {
		Collection<Audit> result;
		result = this.auditRepository.findAuditsByTrip(trip.getId());
		Assert.notNull(result);
		return result;
	}

	//35.4
	public Object getMinMaxAverStandardAuditsPerTrip() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Object res = this.auditRepository.findMinMaxAverStandardAuditsPerTrip();
		return res;
	}
}
