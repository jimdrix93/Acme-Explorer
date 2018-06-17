
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.MyMessage;
import services.AdministratorService;
import services.MyMessageService;

@Controller
@RequestMapping("/broadcast/administrator")
public class BroadcastAdministratorController extends AbstractController {

	@Autowired
	MyMessageService messageService;

	@Autowired
	AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public BroadcastAdministratorController() {
		super();
	}

	// Create actor
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final MyMessage broadcast = this.messageService.create(this.administratorService.findByPrincipal().getId());

		result = new ModelAndView("broadcast/create");
		result.addObject("myMessage", broadcast);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MyMessage myMessage, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("broadcast/create");
			result.addObject("myMessage", myMessage);

		} else
			try {
				this.messageService.broadcast(myMessage);
				result = this.createMessageModelAndView("broadcast.commit.ok", "/");
			} catch (final Throwable oops) {
				result = this.createMessageModelAndView(oops.getMessage(), "/");
			}
		return result;
	}
}