
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import security.UserAccount;
import services.ActorService;
import services.UserAccountService;

@Controller
@RequestMapping("/userAccount")
public class UserAccountController extends AbstractController {

	@Autowired
	ActorService actorService;

	@Autowired
	UserAccountService userAccountService;

	// Constructors -----------------------------------------------------------

	public UserAccountController() {
		super();
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final Actor actor = this.actorService.findByPrincipal();
		final UserAccount userAccount = this.userAccountService.findByActor(actor);
		ModelAndView result;

		result = this.createEditModelAndView(userAccount, null);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@Valid final UserAccount userAccount, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(userAccount, null);
		else
			try {

				this.userAccountService.save(userAccount);

				result = this.createMessageModelAndView("userAccount.commit.ok", "/");
			} catch (final Throwable ooops) {

				result = this.createMessageModelAndView("userAccount.commit.ko", "/");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final UserAccount userAccount, final String message) {
		ModelAndView result;

		result = new ModelAndView("userAccount/edit");

		result.addObject("userAccount", userAccount);
		result.addObject("message", message);

		return result;
	}

}
