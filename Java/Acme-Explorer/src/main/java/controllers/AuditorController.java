
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import domain.Auditor;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	AuditorService	auditorService;


	// Constructors -----------------------------------------------------------

	public AuditorController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Auditor auditor;

		auditor = this.auditorService.create();

		result = new ModelAndView("auditor/create");
		result.addObject("auditor", auditor);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditor, "auditor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor) {
		final ModelAndView result;
		result = this.createEditModelAndView(auditor, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Auditor auditor, final String message) {
		final ModelAndView result;

		result = new ModelAndView("auditor/create");
		result.addObject("auditor", auditor);
		result.addObject("message", message);

		return result;
	}

}
