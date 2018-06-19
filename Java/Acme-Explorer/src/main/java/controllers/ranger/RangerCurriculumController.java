
package controllers.ranger;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/curriculum/ranger")
public class RangerCurriculumController extends AbstractController {

	@Autowired
	RangerService			rangerService;
	@Autowired
	CurriculumService		curriculumService;
	@Autowired
	PersonalRecordService	personalRecordService;


	// Constructors -----------------------------------------------------------

	public RangerCurriculumController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final Ranger ranger = this.rangerService.findByPrincipal();

		if (ranger.getCurriculum() != null)
			return this.createMessageModelAndView("curriculum.confirm.have.curriculum", "/");

		final Curriculum curriculum = this.curriculumService.create();

		result = new ModelAndView("curriculum/ranger/create");
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		return result;
	}
	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		final Curriculum curriculum2 = this.curriculumService.findOne(curriculum.getId());
		final Ranger ranger = this.rangerService.findByPrincipal();
		if (!ranger.getCurriculum().equals(curriculum2))
			return this.createMessageModelAndView("curriculum.commit.not.delete", "/");

		try {
			this.curriculumService.delete(curriculum2);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum2, "curriculum.commit.error");
		}
		return result;
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

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		if (ranger.getCurriculum() == null)
			return this.createMessageModelAndView("curriculum.does.not.exit", "/");

		result = this.createEditModelAndView(curriculum);

		return result;
	}

	// Ancillary Methods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		assert curriculum != null;
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String message) {
		ModelAndView result;
		final Collection<EducationRecord> educationRecords = curriculum.getEducationRecords();
		final Collection<MiscellaneousRecord> miscellaneousRecords = curriculum.getMiscellaneousRecords();

		result = new ModelAndView("curriculum/ranger/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("educationRecords", educationRecords);
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		result.addObject("message", message);

		return result;
	}

}
