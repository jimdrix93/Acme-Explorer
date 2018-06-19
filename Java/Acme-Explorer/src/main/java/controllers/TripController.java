
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FinderService;
import services.TripService;
import domain.Audit;
import domain.Finder;
import domain.HasValue;
import domain.Note;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	@Autowired
	TripService		tripService;
	@Autowired
	ActorService	actorService;
	@Autowired
	FinderService	finderService;


	// Constructors -----------------------------------------------------------

	public TripController() {
		super();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(required = false) final Integer finderId) {
		ModelAndView result;
		final Collection<Trip> trips;
		Integer actorId;
		Finder finder;

		try {
			actorId = this.actorService.findByPrincipal().getId();
		} catch (final Exception ooops) {
			actorId = 0;
		}
		if (finderId != null) {
			finder = this.finderService.findOne(finderId);
			trips = finder.getTrips();
		} else
			trips = this.tripService.findPublicatedTrips();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		result.addObject("actorId", actorId);
		result.addObject("currentDate", new Date(System.currentTimeMillis() - 1));

		return result;
	}
	@RequestMapping(value = "/listTripCategory", method = RequestMethod.GET)
	public ModelAndView listTripCategory(@RequestParam final int categoryId) {

		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findTripByCategory(categoryId);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		//		final Actor actor = this.actorService.findByPrincipal();
		Collection<Note> notes;
		Collection<Audit> audits;
		Collection<Stage> stages;
		Collection<SurvivalClass> survivalClasses;
		Collection<Story> stories;
		Collection<HasValue> hasValues;

		trip = this.tripService.findOne(tripId);

		//		final Collection<Trip> trips = this.tripService.findPublicatedTrips();

		//		if (!actor.equals(trip.getManager()))
		//			if (!trips.contains(trip))
		//				return this.createMessageModelAndView("trip.commit.error", "/");

		notes = trip.getNotes();
		audits = trip.getAudits();
		stages = trip.getStages();
		survivalClasses = trip.getSurvivalClasses();
		stories = trip.getStories();
		hasValues = trip.getHasValues();

		result = new ModelAndView("trip/display");

		result.addObject("trip", trip);
		result.addObject("notes", notes);
		result.addObject("audits", audits);
		result.addObject("stages", stages);
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("stories", stories);
		result.addObject("hasValues", hasValues);

		return result;
	}
}
