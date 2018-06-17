
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.ExplorerService;
import services.ManagerService;
import services.RangerService;
import services.SponsorService;
import domain.Actor;
import domain.Administrator;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	AdministratorService	administratorService;
	@Autowired
	ExplorerService			explorerService;
	@Autowired
	RangerService			rangerService;
	@Autowired
	ManagerService			managerService;
	@Autowired
	AuditorService			auditorService;
	@Autowired
	SponsorService			sponsorService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<Actor> actors = this.actorService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestUri", "actor/list.do");

		return result;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView result;
		final Actor actor = this.actorService.create();

		result = this.createEditModelAndView(actor);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final Actor actor = this.actorService.findByPrincipal();
		ModelAndView result;

		result = this.createEditModelAndView(actor);
		return result;
	}

	// SaveAdmin -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdmin(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(admin, "actor.commit.error");
			}
		return result;
	}

	// SaveExplorer -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveExplorer")
	public ModelAndView saveExplorer(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(explorer, "actor.commit.error");
			}
		return result;
	}

	// SaveRanger -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveRanger")
	public ModelAndView SaveRanger(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(ranger, "actor.commit.error");
			}
		return result;
	}
	// SaveManager -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveManager")
	public ModelAndView SaveManager(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(manager, "actor.commit.error");
			}
		return result;
	}

	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Actor actor) {
		assert actor != null;
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;
		assert actor != null;
		String actorType = "";

		result = new ModelAndView("actor/edit");

		if (actor.getClass().equals(Administrator.class)) {
			final Administrator administrator = (Administrator) actor;
			actorType = "administrator";
			result.addObject(actorType, administrator);
			result.addObject("actorType", actorType);
		} else if (actor.getClass().equals(Manager.class)) {
			final Manager manager = (Manager) actor;
			actorType = "manager";
			result.addObject(actorType, manager);
			result.addObject("actorType", actorType);
		} else if (actor.getClass().equals(Ranger.class)) {
			final Ranger ranger = (Ranger) actor;
			actorType = "ranger";
			result.addObject(actorType, ranger);
			result.addObject("actorType", actorType);
		} else if (actor.getClass().equals(Explorer.class)) {
			final Explorer explorer = (Explorer) actor;
			actorType = "explorer";
			result.addObject(actorType, explorer);
			result.addObject("actorType", actorType);
		}

		result.addObject("message", message);

		return result;
	}
}
