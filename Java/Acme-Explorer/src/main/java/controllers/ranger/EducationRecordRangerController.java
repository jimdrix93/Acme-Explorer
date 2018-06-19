
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.EducationRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	@Autowired
	RangerService			rangerService;
	@Autowired
	CurriculumService		curriculumService;
	@Autowired
	EducationRecordService	educationRecordService;


	// Constructors -----------------------------------------------------------

	public EducationRecordRangerController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final EducationRecord educationRecord = this.educationRecordService.create();

		result = new ModelAndView("educationRecord/ranger/create");
		result.addObject("educationRecord", educationRecord);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		final EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (educationRecord.getId() != 0 && !ranger.getCurriculum().getEducationRecords().contains(educationRecord)))
			return this.createMessageModelAndView("educationRecord.confirm.not.have.permission", "/");

		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (educationRecord.getId() != 0 && !ranger.getCurriculum().getEducationRecords().contains(educationRecord)))
			return this.createMessageModelAndView("educationRecord.confirm.not.have.permission", "/");

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord, curriculum);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (educationRecord.getId() != 0 && !ranger.getCurriculum().getEducationRecords().contains(educationRecord)))
			return this.createMessageModelAndView("educationRecord.confirm.not.have.permission", "/");

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}
		return result;
	}
	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		assert educationRecord != null;
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/ranger/create");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);

		return result;
	}

}
