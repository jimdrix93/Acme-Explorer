
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HasValueService;
import domain.HasValue;

@Controller
@RequestMapping("/hasValue")
public class HasValueController extends AbstractController {

	@Autowired
	HasValueService	hasValueService;


	public HasValueController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tagId) {

		ModelAndView result;
		Collection<HasValue> hasValues;

		hasValues = this.hasValueService.findHasValuesByTag(tagId);

		result = new ModelAndView("hasValue/list");
		result.addObject("hasValues", hasValues);
		result.addObject("requestUri", "hasValue/list.do");

		return result;
	}
}
