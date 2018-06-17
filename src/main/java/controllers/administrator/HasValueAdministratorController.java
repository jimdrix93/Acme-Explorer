
package controllers.administrator;

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

import services.HasValueService;
import services.TagService;
import services.TripService;
import controllers.AbstractController;
import domain.HasValue;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/hasValue/administrator")
public class HasValueAdministratorController extends AbstractController {

	@Autowired
	HasValueService	hasValueService;
	@Autowired
	TagService		tagService;
	@Autowired
	TripService		tripService;


	public HasValueAdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final HasValue hasValue = this.hasValueService.create();
		final Collection<Tag> tags = this.tagService.findAll();
		final Collection<Trip> trips = this.tripService.findAll();

		result = new ModelAndView("hasValue/administrator/create");
		result.addObject("hasValue", hasValue);
		result.addObject("tags", tags);
		result.addObject("trips", trips);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int hasValueId) {
		ModelAndView result;
		HasValue hasValue;
		hasValue = this.hasValueService.findOne(hasValueId);
		final Collection<Tag> tags = this.tagService.findAll();
		final Collection<Trip> trips = this.tripService.findAll();
		result = new ModelAndView("hasValue/administrator/edit");
		result.addObject("hasValue", hasValue);
		result.addObject("tags", tags);
		result.addObject("trips", trips);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HasValue hasValue, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(hasValue);

		if (binding.hasErrors())
			result = this.createEditModelAndView(hasValue);
		else
			try {
				this.hasValueService.save(hasValue);
				result = new ModelAndView("redirect:../list.do?tagId=" + hasValue.getTag().getId());

			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(hasValue, "hasValue.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final HasValue hasValue, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(hasValue);

		if (binding.hasErrors())
			result = this.createEditModelAndView(hasValue);
		else
			try {
				this.hasValueService.delete(hasValue);
				result = new ModelAndView("redirect:../list.do?tagId=" + hasValue.getTag().getId());
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(hasValue, "hasValue.commit.error");
			}
		return result;
	}
	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final HasValue hasValue) {
		ModelAndView result;

		result = this.createEditModelAndView(hasValue, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final HasValue hasValue, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("hasValue/administrator/edit");
		final Collection<Tag> tags = this.tagService.findAll();
		final Collection<Trip> trips = this.tripService.findAll();
		result.addObject("hasValue", hasValue);
		result.addObject("requestUri", "redirect:../list.do?tagId=" + hasValue.getTag().getId());
		result.addObject("messageCode", messageCode);
		result.addObject("tags", tags);
		result.addObject("trips", trips);
		return result;
	}
}
