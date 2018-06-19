
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Story;
import domain.Trip;
import services.StoryService;
import services.TripService;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	@Autowired
	StoryService storyService;
	@Autowired
	TripService tripService;

	// Constructors -----------------------------------------------------------

	public StoryExplorerController() {
		super();
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int tripId) {

		ModelAndView result;
		Story story = null;

		try {
			final Trip trip = this.tripService.findOne(tripId);

			story = this.storyService.create(trip);
			result = this.createEditModelAndView(story, null);

		} catch (final Exception e) {
			result = this.createMessageModelAndView(e.getMessage(), "/application/explorer/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story, null);
		else
			try {
				this.storyService.save(story);
				result = this.createMessageModelAndView("story.commit.ok", "application/explorer/list.do");

			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String message) {

		ModelAndView result;

		result = new ModelAndView("story/explorer/edit");
		result.addObject("story", story);
		result.addObject("message", message);

		return result;
	}

}
