
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.CreditCard;
import services.ApplicationService;
import services.CreditCardService;

@Controller
@RequestMapping("/creditcard/explorer")
public class CreditCardExplorerController extends AbstractController {

	@Autowired
	CreditCardService creditCardService;

	@Autowired
	ApplicationService applicationService;

	// Constructors -----------------------------------------------------------

	public CreditCardExplorerController() {
		super();
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int applicationId) {

		ModelAndView result;
		CreditCard creditCard = null;

		try {

			creditCard = this.creditCardService.create();
			result = this.createEditModelAndView(creditCard, applicationId, null);

		} catch (final Exception e) {
			result = this.createMessageModelAndView(e.getMessage(), "/application/explorer/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final int applicationId, @Valid final CreditCard creditCard, final BindingResult binding) {

		ModelAndView result;
		CreditCard saved;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard, applicationId, null);
		else
			try {
				saved = this.creditCardService.save(creditCard);
				this.applicationService.enterCreditCard(applicationId, saved);

				result = this.createMessageModelAndView("creditcard.commit.ok", "application/explorer/list.do");

			} catch (final Throwable ooops) {
				// result = this.createEditModelAndView(creditCard,
				// applicationId, "creditcard.commit.error");
				result = this.createMessageModelAndView(ooops.getMessage(), "application/explorer/list.do");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final int applicationId,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("creditcard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		result.addObject("requestUri", "creditcard/explorer/edit.do");
		result.addObject("applicationId", applicationId);
		return result;
	}

}
