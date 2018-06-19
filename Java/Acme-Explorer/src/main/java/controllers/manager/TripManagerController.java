
package controllers.manager;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.SponsorshipService;
import services.StageService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.LegalText;
import domain.Ranger;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/trip/manager")
public class TripManagerController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	TripService				tripService;
	@Autowired
	ManagerService			managerService;
	@Autowired
	StageService			stageService;
	@Autowired
	CategoryService			categoryService;
	@Autowired
	SurvivalClassService	survivalClassService;
	@Autowired
	LegalTextService		legalTextService;
	@Autowired
	RangerService			rangerService;
	@Autowired
	SponsorshipService		sponsorshipService;


	// Constructors -----------------------------------------------------------
	public TripManagerController() {
		super();
	}

	// Creation -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Trip trip = this.tripService.create();

		result = this.createEditModelAndView(trip);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		final Trip trip = this.tripService.findOneToEdit(tripId);
		Assert.notNull(trip);

		result = this.createEditModelAndView(trip);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				this.tripService.save(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;

		try {
			this.tripService.delete(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable ooops) {
			result = this.createEditModelAndView(trip, "trip.commit.error");
		}
		return result;
	}

	// Listing -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Trip> trips;
		final int managerId = this.managerService.findByPrincipal().getId();
		trips = this.tripService.findTripsByManager(managerId);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("actorId", managerId);
		result.addObject("requestURI", "trip/manager/list.do");
		result.addObject("currentDate", new Date(System.currentTimeMillis() - 1));

		return result;
	}

	// Ancillary methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Trip trip) {
		final ModelAndView result;
		result = this.createEditModelAndView(trip, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String message) {
		ModelAndView result;
		final Collection<Stage> stages = this.stageService.findStageWithoutTrip();
		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<Ranger> rangers = this.rangerService.findAll();
		final Collection<LegalText> legalTexts = this.legalTextService.findAllValid();
		final Date actualDate = new Date(System.currentTimeMillis() - 1);
		Boolean canBeCancelated = true;
		final Collection<Category> categoriesCopy = categories;

		if (trip.getId() != 0) {
			stages.addAll(trip.getStages());
			if (trip.getStartTrip().before(actualDate))
				canBeCancelated = false;
		}

		for (final Category c : categoriesCopy) {
			if (c.getName().equals("CATEGORY"))
				categories.remove(c);
			break;
		}

		result = new ModelAndView("trip/edit");
		result.addObject("trip", trip);
		result.addObject("category", categories);
		result.addObject("stages", stages);
		result.addObject("ranger", rangers);
		result.addObject("legalText", legalTexts);
		result.addObject("message", message);
		result.addObject("canBeCancelated", canBeCancelated);

		return result;
	}
}
