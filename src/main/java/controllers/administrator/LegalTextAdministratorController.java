
package controllers.administrator;

import java.util.Collection;
import java.util.LinkedList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.LegalTextService;
import controllers.AbstractController;
import domain.LegalText;

@Controller
@RequestMapping("/legaltext/administrator")
public class LegalTextAdministratorController extends AbstractController {

	@Autowired
	LegalTextService		legalTextService;
	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public LegalTextAdministratorController() {
		super();
	}
	//Create method -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final LegalText legalText = this.legalTextService.create();

		result = this.createEditModelAndView(legalText);

		return result;
	}
	// Edit methods -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalTextId) {
		ModelAndView result;
		final LegalText legalText = this.legalTextService.findOne(legalTextId);

		result = this.createEditModelAndView(legalText);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalText legalText, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalText legalText, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.delete(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");
			}
		return result;
	}
	// List method  -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		final Collection<LegalText> legalTextsDraft = new LinkedList<LegalText>();
		final Collection<LegalText> legalTextsFinalMode = new LinkedList<LegalText>();

		for (final LegalText lt : legalTexts)
			if (lt.getFinalMode())
				legalTextsFinalMode.add(lt);
			else
				legalTextsDraft.add(lt);

		result = new ModelAndView("legaltext/list");
		result.addObject("legalTextsDraft", legalTextsDraft);
		result.addObject("legalTextsFinalMode", legalTextsFinalMode);

		return result;
	}

	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final LegalText legalText) {
		ModelAndView result;

		result = this.createEditModelAndView(legalText, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalText legalText, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("legaltext/edit");

		result.addObject("legalText", legalText);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
