
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/personalRecord/ranger")
public class PersonalRecordRangerController extends AbstractController {

	@Autowired
	RangerService			rangerService;
	@Autowired
	CurriculumService		curriculumService;
	@Autowired
	PersonalRecordService	personalRecordService;


	// Constructors -----------------------------------------------------------

	public PersonalRecordRangerController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final PersonalRecord personalRecord = this.personalRecordService.create();

		result = new ModelAndView("personalRecord/ranger/create");
		result.addObject("personalRecord", personalRecord);

		return result;
	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findByPrincipal();
		final PersonalRecord personalRecord = ranger.getCurriculum().getPersonalRecord();

		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (personalRecord.getId() != 0 && !ranger.getCurriculum().getPersonalRecord().equals(personalRecord)))
			return this.createMessageModelAndView("personalRecord.confirm.not.have.permission", "/");

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord, curriculum);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		if (!ranger.getCurriculum().getPersonalRecord().equals(personalRecord))
			return this.createMessageModelAndView("personalRecord.confirm.not.have.permission", "/");

		try {
			this.personalRecordService.delete(personalRecord);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
		}
		return result;
	}

	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		assert personalRecord != null;
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("personalRecord/ranger/create");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", message);

		return result;
	}

}
