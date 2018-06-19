
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.NoteService;
import controllers.AbstractController;
import controllers.TripController;
import domain.Auditor;
import domain.Note;

@Controller
@RequestMapping("/note")
public class NoteAuditorController extends AbstractController {

	@Autowired
	private NoteService		noteService;
	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private TripController	tripController;


	// Constructors -----------------------------------------------------------

	public NoteAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Note> notes;

		final Auditor auditor = this.auditorService.findByPrincipal();
		notes = this.noteService.findNotesByAuditor(auditor);

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/list.do");
		result.addObject("notes", notes);

		return result;
	}

	@RequestMapping(value = "/listByTrip", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;
		Collection<Note> notes;
		notes = this.noteService.findNotesByTrip(tripId);

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/list.do");
		result.addObject("notes", notes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;
		final Note note = this.noteService.findOne(noteId);

		result = new ModelAndView("note/edit");
		result.addObject("note", note);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		final ModelAndView result;
		final Note note = this.noteService.create(tripId);

		result = new ModelAndView("note/create");
		result.addObject("note", note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				//	result = this.tripController.list();
				if (note.getReply() == null)
					result = this.list();
				else
					result = this.list(note.getTrip().getId());
				//result = new ModelAndView("redirect: /list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;
	}

	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/edit");

		result.addObject("note", note);
		result.addObject("requestUri", "note/list.do");
		result.addObject("messageCode", messageCode);

		return result;
	}
}
