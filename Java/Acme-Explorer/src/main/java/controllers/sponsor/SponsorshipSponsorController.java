/*
 * CustomerController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.sponsor;

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

import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;
import services.SponsorService;
import services.SponsorshipService;
import services.TripService;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	SponsorshipService sponsorshipService;

	@Autowired
	SponsorService sponsorService;

	@Autowired
	TripService tripService;

	// Constructors -----------------------------------------------------------

	public SponsorshipSponsorController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);

		ModelAndView result;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllByPrincipal(sponsor.getId());

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);

		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);

		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	// Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.create();

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship, null);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = this.createMessageModelAndView("sponsorship.commit.ok", "sponsorship/sponsor/list.do");

			} catch (final Throwable ooops) {

				result = this.createMessageModelAndView(ooops.getMessage() + ooops.getCause().getMessage(),
						"sponsorship/sponsor/list.do");
			}
		return result;
	}

	// Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship, null);
		else
			try {
				this.sponsorshipService.delete(sponsorship);
				result = this.createMessageModelAndView("sponsorship.commit.ok", "sponsorship/sponsor/list.do");
			} catch (final Throwable ooops) {

				result = this.createMessageModelAndView(ooops.getMessage(), "sponsorship/sponsor/list.do");
			}
		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);

		final Collection<Trip> trips = this.tripService.findAll();
		Assert.notNull(trips);
		result.addObject("trips", trips);

		result.addObject("message", message);

		return result;
	}
}
