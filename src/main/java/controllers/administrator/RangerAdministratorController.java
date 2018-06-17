
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import controllers.AbstractController;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/administrator")
public class RangerAdministratorController extends AbstractController {

	@Autowired
	RangerService	rangerService;


	// Constructors -----------------------------------------------------------

	public RangerAdministratorController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Ranger ranger;

		ranger = this.rangerService.create();

		result = new ModelAndView("ranger/create");
		result.addObject("ranger", ranger);
		result.addObject("requestURI", "ranger/administrator/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "ranger.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Ranger ranger) {
		final ModelAndView result;
		result = this.createEditModelAndView(ranger, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Ranger ranger, final String message) {
		final ModelAndView result;

		result = new ModelAndView("ranger/create");
		result.addObject("manager", ranger);
		result.addObject("message", message);

		return result;
	}
}
