
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController {

	@Autowired
	ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public ManagerAdministratorController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Manager manager;

		manager = this.managerService.create();

		result = new ModelAndView("manager/administrator/create");
		result.addObject("manager", manager);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;
		System.out.println(binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager) {
		final ModelAndView result;
		result = this.createEditModelAndView(manager, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		final ModelAndView result;

		result = new ModelAndView("manager/administrator/create");
		result.addObject("manager", manager);
		result.addObject("message", message);

		return result;
	}
}
