
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.TripService;
import domain.Finder;
import domain.Trip;

@Controller
@RequestMapping("/trip")
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
	public ModelAndView search(@Valid final Finder finder) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findTripByKeyword(finder.getSearch());

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "/finder/list");

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

		return result;
	}

}
