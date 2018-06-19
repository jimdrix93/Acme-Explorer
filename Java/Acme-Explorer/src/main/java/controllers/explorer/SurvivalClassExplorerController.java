
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SurvivalClassService;
import controllers.AbstractController;
import domain.SurvivalClass;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	@Autowired
	SurvivalClassService	survivalClassService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassExplorerController() {
		super();
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int tripId) {

		final ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		survivalClasses = this.survivalClassService.findByTrip(tripId);

		result = new ModelAndView("survivalClass/explorer/list");
		result.addObject("survivalClasses", survivalClasses);

		return result;
	}

	// Registration -----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		this.survivalClassService.enrolASurvivalClass(survivalClass.getId());
		result = new ModelAndView("application/explorer/register");
		result.addObject("survivalClass", survivalClass);

		return result;
	}

}
