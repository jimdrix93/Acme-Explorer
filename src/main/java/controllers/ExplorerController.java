
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import domain.Explorer;

@Controller
@RequestMapping("/explorer")
public class ExplorerController extends AbstractController {

	@Autowired
	ExplorerService	explorerService;


	// Constructors -----------------------------------------------------------

	public ExplorerController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Explorer explorer;

		explorer = this.explorerService.create();

		result = new ModelAndView("explorer/create");
		result.addObject("explorer", explorer);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "explorer.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer) {
		final ModelAndView result;
		result = this.createEditModelAndView(explorer, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {
		final ModelAndView result;

		result = new ModelAndView("explorer/create");
		result.addObject("explorer", explorer);
		result.addObject("message", message);

		return result;
	}

}
