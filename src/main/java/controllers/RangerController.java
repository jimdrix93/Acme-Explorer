
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RangerService;
import domain.Ranger;

@Controller
@RequestMapping("/ranger")
public class RangerController extends AbstractController {

	@Autowired
	RangerService		rangerService;
	@Autowired
	CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public RangerController() {
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
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "ranger.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findOne(rangerId);

		result = new ModelAndView("ranger/display");
		result.addObject("ranger", ranger);

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
		result.addObject("ranger", ranger);
		result.addObject("message", message);

		return result;
	}

}
