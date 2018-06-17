
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import domain.Tag;

@Controller
@RequestMapping("/tag")
public class TagController extends AbstractController {

	@Autowired
	TagService	tagService;


	public TagController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Tag> tags;

		tags = this.tagService.findAll();

		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("requestUri", "tag/list.do");

		return result;
	}
}
