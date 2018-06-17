
package controllers.ranger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/curriculum")
public class RangerCurriculumController extends AbstractController {

	@Autowired
	RangerService		rangerService;
	@Autowired
	CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public RangerCurriculumController() {
		super();
	}

	// Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findOne(rangerId);
		final Curriculum curriculum = this.curriculumService.findOne(ranger.getCurriculum().getId());

		result = new ModelAndView("curriculum/display"); //TODO Realizar vista
		result.addObject("curriculum", curriculum);

		return result;
	}

}
