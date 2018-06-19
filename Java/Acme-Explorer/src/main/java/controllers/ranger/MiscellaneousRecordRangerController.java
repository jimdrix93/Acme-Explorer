
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
import services.MiscellaneousRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class MiscellaneousRecordRangerController extends AbstractController {

	@Autowired
	RangerService				rangerService;
	@Autowired
	CurriculumService			curriculumService;
	@Autowired
	MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordRangerController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();

		result = new ModelAndView("miscellaneousRecord/ranger/create");
		result.addObject("miscellaneousRecord", miscellaneousRecord);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (miscellaneousRecord.getId() != 0 && !ranger.getCurriculum().getMiscellaneousRecords().contains(miscellaneousRecord)))
			return this.createMessageModelAndView("miscellaneousRecord.confirm.not.have.permission", "/");

		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (miscellaneousRecord.getId() != 0 && !ranger.getCurriculum().getMiscellaneousRecords().contains(miscellaneousRecord)))
			return this.createMessageModelAndView("miscellaneousRecord.confirm.not.have.permission", "/");

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord, curriculum);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();
		if (curriculum == null || (miscellaneousRecord.getId() != 0 && !ranger.getCurriculum().getMiscellaneousRecords().contains(miscellaneousRecord)))
			return this.createMessageModelAndView("miscellaneousRecord.confirm.not.have.permission", "/");

		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}
		return result;
	}
	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		assert miscellaneousRecord != null;
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/ranger/create");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", message);

		return result;
	}

}
