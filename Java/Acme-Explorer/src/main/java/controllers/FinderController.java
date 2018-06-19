
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.TripService;
import domain.Finder;

@Controller
@RequestMapping("/finder")
public class finderController extends AbstractController {

	@Autowired
	FinderService	finderService;
	@Autowired
	TripService		tripService;


	// Constructors -----------------------------------------------------------

	public finderController() {
		super();
	}

	@RequestMapping(value = "/search")
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder = this.finderService.create();

		result = this.createEditModelAndView(finder);

		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchButton")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder finderSaved;
		String redirectView;

<<<<<<< .mine
		trips = this.tripService.findTripByKeyword(finder.getSearch());

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "/finder/list");

||||||| .r28
		trips = this.tripService.findTripByKeyword(finder.getSearch());

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");

=======
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				finderSaved = this.finderService.save(finder);
				//Create view
				redirectView = String.format("redirect:/trip/list.do?finderId=%d", finderSaved.getId());
				result = new ModelAndView(redirectView);
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
>>>>>>> .r65
		return result;
	}

	//Ancillary Methods -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		final ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
<<<<<<< .mine
||||||| .r28
		result.addObject("requestURI", "trip/search.do");
=======
		result.addObject("message", message);
		result.addObject("requestURI", "finder/search.do");
>>>>>>> .r65

		return result;
	}

}
