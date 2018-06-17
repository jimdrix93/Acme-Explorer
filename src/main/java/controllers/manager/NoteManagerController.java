/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.NoteService;
import controllers.AbstractController;
import domain.Note;

@Controller
@RequestMapping("/note/manager")
public class NoteManagerController extends AbstractController {

	@Autowired
	private NoteService		noteService;
	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public NoteManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;
		Collection<Note> notes;
		final Integer managerId = this.managerService.findByPrincipal().getId();

		notes = this.noteService.findNotesByTrip(tripId);

		result = new ModelAndView("note/manager/list");
		result.addObject("notes", notes);
		result.addObject("managerId", managerId);

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);

		result = new ModelAndView("note/manager/edit");
		result.addObject("note", note);

		return result;
	}

}
