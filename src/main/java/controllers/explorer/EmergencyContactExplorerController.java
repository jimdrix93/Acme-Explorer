
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EmergencyContactService;
import services.ExplorerService;
import controllers.AbstractController;
import domain.EmergencyContact;
import domain.Explorer;

@Controller
@RequestMapping("/emergencyContact/explorer")
public class EmergencyContactExplorerController extends AbstractController {

	@Autowired
	EmergencyContactService	emergencyContactService;
	@Autowired
	ExplorerService			explorerService;


	// Constructors -----------------------------------------------------------

	public EmergencyContactExplorerController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final EmergencyContact emergencyContact = this.emergencyContactService.create();

		result = new ModelAndView("emergencyContact/explorer/create");
		result.addObject("emergencyContact", emergencyContact);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int emergencyContactId) {

		ModelAndView result;
		final Explorer explorer = this.explorerService.findByPrincipal();
		final EmergencyContact emergencyContact = this.emergencyContactService.findOne(emergencyContactId);
		Assert.isTrue(explorer.getEmergencyContacts().contains(emergencyContact));

		result = new ModelAndView("emergencyContact/explorer/edit");
		result.addObject("emergencyContact", emergencyContact);

		return result;
	}

	//SAVE --------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EmergencyContact emergencyContact, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(emergencyContact);
		else
			try {
				this.emergencyContactService.save(emergencyContact);
				result = new ModelAndView("redirect:../explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(emergencyContact, "emergencyContact.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EmergencyContact emergencyContact, final BindingResult binding) {
		ModelAndView result;

		try {
			this.emergencyContactService.delete(emergencyContact);
			result = new ModelAndView("redirect:../explorer/list.do");
		} catch (final Throwable ooops) {
			result = this.createEditModelAndView(emergencyContact, "emergencyContact.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final EmergencyContact emergencyContact) {
		final ModelAndView result;
		result = this.createEditModelAndView(emergencyContact, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final EmergencyContact emergencyContact, final String message) {
		final ModelAndView result;

		result = new ModelAndView("emergencyContact/explorer/create");
		result.addObject("emergencyContact", emergencyContact);
		result.addObject("message", message);

		return result;
	}

	//Listing ----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Collection<EmergencyContact> emergencyContacts = explorer.getEmergencyContacts();

		result = new ModelAndView("emergencyContact/explorer/list");
		result.addObject("emergencyContacts", emergencyContacts);
		result.addObject("requestUri", "emergencyContact/explorer/list.do");

		return result;
	}
}
