
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/administrator")
public class TagAdministratorController extends AbstractController {

	@Autowired
	TagService	tagService;


	public TagAdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Tag tag = this.tagService.create();

		result = new ModelAndView("tag/administrator/create");
		result.addObject("tag", tag);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		ModelAndView result;
		Tag tag;
		tag = this.tagService.findOne(tagId);

		result = new ModelAndView("tag/administrator/edit");
		result.addObject("tag", tag);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(tag);

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.save(tag);
				result = new ModelAndView("redirect:../list.do");

			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(tag, "tag.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Tag tag, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(tag);

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.delete(tag);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(tag, "tag.commit.error");
			}
		return result;
	}
	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final Tag tag) {
		ModelAndView result;

		result = this.createEditModelAndView(tag, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tag tag, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("tag/administrator/edit");

		result.addObject("tag", tag);
		result.addObject("message", messageCode);

		return result;
	}

}
