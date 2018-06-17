
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SurvivalClassService;
import controllers.AbstractController;
import domain.SurvivalClass;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	@Autowired
	SurvivalClassService	survivalClassService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassManagerController() {
		super();
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int tripId) {

		ModelAndView result;
		final Collection<SurvivalClass> survivalClasses = this.survivalClassService.findByTrip(tripId);

		result = new ModelAndView("survivalClass/manager/list");
		result.addObject("survivalClasses", survivalClasses);

		return result;
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.create(tripId);
		Assert.notNull(survivalClass);
		result = new ModelAndView("survivalClass/manager/create");
		result.addObject("survivalClass", survivalClass);

		return result;
	}

}
