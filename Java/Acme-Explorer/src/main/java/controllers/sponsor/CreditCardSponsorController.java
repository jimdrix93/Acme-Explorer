
package controllers.sponsor;

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
import services.CreditCardService;
import services.SponsorshipService;

@Controller
@RequestMapping("/creditcard/sponsor")
public class CreditCardSponsorController extends AbstractController {

	@Autowired
	CreditCardService creditCardService;

	@Autowired
	SponsorshipService sponsorshipService;

	// Constructors -----------------------------------------------------------

	public CreditCardSponsorController() {
		super();
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int sponsorshipId) {

		ModelAndView result;
		CreditCard creditCard = null;

		try {

			creditCard = this.creditCardService.create();
			result = this.createEditModelAndView(creditCard, sponsorshipId, null);

		} catch (final Exception e) {
			result = this.createMessageModelAndView(e.getMessage(), "/sponsorship/sponsor/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final int sponsorshipId, @Valid final CreditCard creditCard, final BindingResult binding) {

		ModelAndView result;
		final CreditCard saved;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard, sponsorshipId, null);
		else
			try {
				saved = this.creditCardService.save(creditCard);
				this.sponsorshipService.enterCreditCard(sponsorshipId, saved);
				result = this.createMessageModelAndView("creditcard.commit.ok", "sponsorship/sponsor/list.do");

			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(creditCard, sponsorshipId, "creditcard.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final int sponsorshipId,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("creditcard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		result.addObject("requestUri", "creditcard/sponsor/edit.do");
		result.addObject("sponsorshipId", sponsorshipId);
		return result;
	}

}
