
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;

@Controller
@RequestMapping("/trip/explorer")
public class FinderExplorerController extends AbstractController {

	@Autowired
	TripService	tripService;


	public FinderExplorerController() {
		super();
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("trip/search");
		result.addObject("requestUri", "trip/search.do");
		return result;
	}

}
