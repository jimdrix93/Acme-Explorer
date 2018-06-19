
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.ExplorerService;
import services.FinderService;
import services.TripService;
import controllers.AbstractController;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	FinderService			finderService;
	@Autowired
	TripService				tripService;
	@Autowired
	ExplorerService			explorerService;
	@Autowired
	ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------
	public FinderExplorerController() {
		super();
	}

	// Creation -----------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder = this.finderService.create();

		result = this.createEditModelAndView(finder);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {
		ModelAndView result;
		final Finder finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		result = this.createEditModelAndView(finder);

		return result;
	}

	// Search -----------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchButton")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder finderSaved;
		String redirectView;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				finderSaved = this.finderService.saveFinderExplorer(finder);
				//Create view
				redirectView = String.format("redirect:/trip/list.do?finderId=%d", finderSaved.getId());
				result = new ModelAndView(redirectView);
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView result;

		final Integer explorerId = this.explorerService.findByPrincipal().getId();

		final Collection<Finder> finders = this.finderService.findFinderExplorer(explorerId);
		final Integer pageSize = this.configurationService.findSizeFinder();

		result = new ModelAndView("finder/list");
		result.addObject("finder", finders);
		result.addObject("pageSize", pageSize);

		return result;
	}

	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		final ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
		result.addObject("message", message);
		result.addObject("requestURI", "finder/explorer/search.do");

		return result;
	}

}
