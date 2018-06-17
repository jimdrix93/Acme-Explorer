
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	@Autowired
	SocialIdentityService	socialIdentityService;
	@Autowired
	ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Create actor ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final SocialIdentity socialIdentity = this.socialIdentityService.create();

		result = new ModelAndView("socialIdentity/create");
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIdentityId) {
		ModelAndView result;
		SocialIdentity socialIdentity;
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<SocialIdentity> socialIdentities = actor.getSocialIdentities();

		socialIdentity = this.socialIdentityService.findOne(socialIdentityId);

		if (!socialIdentities.contains(socialIdentity))
			return this.createMessageModelAndView("socialIdentity.commit.error", "/application/explorer/list.do");

		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialIdentityService.save(socialIdentity);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}
		return result;
	}

	// Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialIdentityService.delete(socialIdentity);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable ooops) {
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity) {
		final ModelAndView result;
		result = this.createEditModelAndView(socialIdentity, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String message) {
		final ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}

	//Listing ----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<SocialIdentity> socialIdentities = actor.getSocialIdentities();

		result = new ModelAndView("socialIdentity/list");
		result.addObject("socialIdentities", socialIdentities);
		result.addObject("requestUri", "socialIdentity/list.do");

		return result;
	}
}
