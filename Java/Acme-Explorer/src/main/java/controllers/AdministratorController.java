/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// CreateUser -----------------------------------------------------------
	@RequestMapping("/createuser")
	public ModelAndView createUser() {

		ModelAndView result;

		final Actor actor = this.actorService.create();

		result = new ModelAndView("actor/create");
		result.addObject("actor", actor);

		return result;
	}
	// Create -----------------------------------------------------------
	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView result;
		final Administrator admin = this.administratorService.create();

		result = this.createEditModelAndView(admin);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		final Administrator admin = this.administratorService.findByPrincipal();

		result = this.createEditModelAndView(admin);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(admin, "actor.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView Delete(final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.administratorService.delete(admin);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(admin, "actor.commit.error");
			}
		return result;
	}

	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Administrator admin) {
		assert admin != null;
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String message) {
		ModelAndView result;
		assert admin != null;

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", admin);
		result.addObject("message", message);

		//		if (actor.getClass() == Explorer.class) {
		//			final Explorer e = (Explorer) actor;
		//			result.addObject("emergencyContacts", e.getEmergencyContacts());
		//		}
		return result;
	}

}
