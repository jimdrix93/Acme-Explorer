
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StageService;
import controllers.AbstractController;
import domain.Stage;

@Controller
@RequestMapping("/stage/manager")
public class StageManagerController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	StageService	stageService;


	// Constructors -----------------------------------------------------------
	public StageManagerController() {
		super();
	}

	// Creation -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Stage stage = this.stageService.create();

		result = new ModelAndView("stage/create");
		result.addObject("stage", stage);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int stageId) {
		ModelAndView result;
		final Stage stage = this.stageService.findOne(stageId);
		Assert.notNull(stage);

		result = this.createEditModelAndView(stage);
		result.addObject("stage", stage);
		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Stage stage, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(stage);

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				this.stageService.save(stage);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(stage, "trip.commit.error");
			}
		return result;
	}

	//	// Listing -----------------------------------------------------------
	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//
	//		ModelAndView result;
	//		Collection<Trip> trips;
	//		final int managerId = this.managerService.findByPrincipal().getId();
	//		trips = this.tripService.findTripsByManager(managerId);
	//
	//		result = new ModelAndView("trip/list");
	//		result.addObject("trips", trips);
	//		result.addObject("requestURI", "trip/manager/list.do");
	//
	//		return result;
	//	}

	// Ancillary methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Stage stage) {
		final ModelAndView result;
		result = this.createEditModelAndView(stage, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Stage stage, final String message) {
		final ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);
		result.addObject("message", message);

		return result;
	}
}
