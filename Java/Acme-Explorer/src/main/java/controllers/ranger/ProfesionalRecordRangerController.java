
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
import services.ProfessionalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.ProfessionalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class ProfesionalRecordRangerController extends AbstractController {

	@Autowired
	RangerService				rangerService;
	@Autowired
	CurriculumService			curriculumService;
	@Autowired
	ProfessionalRecordService	professionalRecordService;


	// Constructors -----------------------------------------------------------

	public ProfesionalRecordRangerController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final ProfessionalRecord professionalRecord = this.professionalRecordService.create();

		result = new ModelAndView("/professionalRecord/ranger/create");
		result.addObject("professionalRecord", professionalRecord);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		try {
			Assert.isTrue(curriculum == null || (professionalRecord.getId() != 0 && !curriculum.getProfessionalRecords().contains(professionalRecord)));
		} catch (final Exception e) {
			return this.createEditModelAndView(professionalRecord, "professionalRecord.confirm.not.have.permission");
		}

		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (professionalRecord.getId() != 0 && !ranger.getCurriculum().getProfessionalRecords().contains(professionalRecord)))
			return this.createMessageModelAndView("personalRecord.confirm.not.have.permission", "/");

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		try {
			Assert.isTrue(curriculum == null || (professionalRecord.getId() != 0 && !ranger.getCurriculum().getProfessionalRecords().contains(professionalRecord)));
		} catch (final Exception e) {
			result = this.createEditModelAndView(professionalRecord, "No lo puedes borrar");
		}

		try {
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
		}
		return result;
	}
	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		assert professionalRecord != null;
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("/professionalRecord/ranger/create");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", message);

		return result;
	}

}
