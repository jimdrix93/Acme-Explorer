
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Administrator;
import domain.Auditor;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class NoteService {

	//Managed repository ------------------------------------------------
	@Autowired
	private NoteRepository			noteRepository;
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private TripService				tripService;


	//Constructor -------------------------------------------------------
	public NoteService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public Note create(final int tripId) {
		final Note result = new Note();
		final Date moment;
		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip, "trip must not be null");

		moment = new Date(System.currentTimeMillis() - 1);

		result.setTrip(trip);
		result.setMoment(moment);
		return result;
	}

	public Note findOne(final Integer noteID) {
		Note result;
		result = this.noteRepository.findOne(noteID);
		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;
		result = this.noteRepository.findAll();
		return result;
	}

	public Note save(final Note note) {
		Note result;
		Assert.notNull(note);
		Assert.notNull(note.getRemark());

		//		if(note.getReply()==null)final Auditor auditor = this.auditorService.findByPrincipal();

		final Auditor auditor = note.getReply() == null ? this.auditorService.findByPrincipal() : null;

		final Date moment = new Date(System.currentTimeMillis() - 1);

		if (auditor == null)
			note.setReplyDate(moment);
		else
			note.setMoment(moment);

		result = this.noteRepository.save(note);

		if (!(auditor == null)) {
			final Collection<Note> suNotas = auditor.getNotes();
			suNotas.add(result);
			auditor.setNotes(suNotas);
			this.auditorService.save(auditor);
		}
		return result;
	}
	public void delete(final Note note) {
		Assert.notNull(note);
		Assert.isTrue(note.getId() != 0);
		final Collection<Auditor> auditors = this.auditorService.findAll();

		for (final Auditor auditor : auditors)
			if (auditor.getNotes().contains(note)) {
				auditor.getNotes().remove(note);
				this.auditorService.save(auditor);
			}

		this.noteRepository.delete(note);
	}

	// Other methods -------------------------------------------------

	//32.1 & 33.1
	public Collection<Note> findNotesByAuditor(final Auditor auditor) {
		Collection<Note> result;
		result = this.noteRepository.findNotesByAuditor(auditor.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Note> findNotesByTrip(final int tripId) {
		Collection<Note> result;
		result = this.noteRepository.findNotesByTrip(tripId);
		Assert.notNull(result);
		return result;
	}

	//35.4
	public Object getMinMaxAverStandardNotesPerTrip() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Object res = this.noteRepository.findMinMaxAverStandardNotesPerTrip();
		return res;
	}

}
