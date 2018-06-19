
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
import services.EndorserRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.Ranger;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	@Autowired
	RangerService			rangerService;
	@Autowired
	CurriculumService		curriculumService;
	@Autowired
	EndorserRecordService	endorserRecordService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordRangerController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final EndorserRecord endorserRecord = this.endorserRecordService.create();

		result = new ModelAndView("/endorserRecord/ranger/create");
		result.addObject("endorserRecord", endorserRecord);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		final EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		try {
			Assert.isTrue(curriculum == null || (endorserRecord.getId() != 0 && !curriculum.getEndorserRecords().contains(endorserRecord)));
		} catch (final Exception e) {
			return this.createEditModelAndView(endorserRecord, "endorserRecord.confirm.not.have.permission");
		}

		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (endorserRecord.getId() != 0 && !ranger.getCurriculum().getEndorserRecords().contains(endorserRecord)))
			return this.createMessageModelAndView("personalRecord.confirm.not.have.permission", "/");

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		try {
			Assert.isTrue(curriculum == null || (endorserRecord.getId() != 0 && !ranger.getCurriculum().getEndorserRecords().contains(endorserRecord)));
		} catch (final Exception e) {
			result = this.createEditModelAndView(endorserRecord, "No lo puedes borrar");
		}

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
		}
		return result;
	}
	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		assert endorserRecord != null;
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("/endorserRecord/ranger/create");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", message);

		return result;
	}

}
