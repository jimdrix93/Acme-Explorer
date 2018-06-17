
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class AuditorService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private AuditorRepository		auditorRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private NoteService				noteService;
	@Autowired
	private AuditService			auditService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -------------------------------------------------------------

	public AuditorService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Auditor create() {
		final Auditor result = new Auditor();
		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);
		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> auditors;

		auditors = this.auditorRepository.findAll();
		return auditors;
	}

	public Auditor save(final Auditor auditor) {
		Auditor result;

		final String countryCode = this.configurationService.findCountryCode();
		if (!auditor.getPhone().contains(countryCode)) {
			final String phone = countryCode + auditor.getPhone();
			auditor.setPhone(phone);
		}

		result = this.auditorRepository.save(auditor);
		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);

		this.auditorRepository.delete(auditor);
	}

	// Other business methods -------------------------------------------------

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Auditor result;

		result = this.auditorRepository.findByUserAccountId(userAccount.getId());

		return result;

	}

	public Auditor findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}

	public Auditor findOneToEdit(final int auditorId) {
		// TODO Auto-generated method stub
		Auditor result, auditor2;
		result = this.auditorRepository.findOne(auditorId);
		auditor2 = this.findByPrincipal();
		Assert.isTrue(auditor2.equals(result));
		return result;
	}
	//33.1
	public Collection<Note> findNotesAuditor(final Auditor auditor) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(auditor.getUserAccount().equals(userAccount));
		final Collection<Note> res = this.noteService.findNotesByAuditor(auditor);
		return res;
	}
	//33.2
	public Collection<Audit> findAuditAuditor(final Auditor auditor) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(auditor.getUserAccount().equals(userAccount));
		final Collection<Audit> res = this.auditService.findAuditByAuditor(auditor);
		return res;
	}
}
