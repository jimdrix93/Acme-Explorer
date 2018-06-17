
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RangerService;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	RangerService		rangerService;
	@Autowired
	CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public CurriculumController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		ModelAndView result;

		Curriculum curriculum;
		PersonalRecord personalRecord;
		final Collection<ProfessionalRecord> professionalRecords;
		Collection<EducationRecord> educationRecords;
		Collection<EndorserRecord> endorserRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		curriculum = this.curriculumService.findOne(curriculumId);
		personalRecord = curriculum.getPersonalRecord();
		professionalRecords = curriculum.getProfessionalRecords();
		educationRecords = curriculum.getEducationRecords();
		endorserRecords = curriculum.getEndorserRecords();
		miscellaneousRecords = curriculum.getMiscellaneousRecords();

		result = new ModelAndView("curriculum/display");

		result.addObject("curriculum", curriculum);
		result.addObject("personalRecord", personalRecord);
		result.addObject("professionalRecord", professionalRecords);
		result.addObject("educationRecord", educationRecords);
		result.addObject("endorserRecord", endorserRecords);
		result.addObject("miscellaneousRecord", miscellaneousRecords);

		return result;
	}
}
