/*
 * CustomerController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Application;
import domain.Status;
import domain.Trip;
import services.ApplicationService;
import services.ManagerService;
import services.MyMessageService;
import services.TripService;

@Controller
@RequestMapping("/application/manager")
public class ApplicationManagerController extends AbstractController {

	@Autowired
	ManagerService managerService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	TripService tripService;

	@Autowired
	MyMessageService messageService;

	// Constructors -----------------------------------------------------------

	public ApplicationManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer tripId) {
		final ModelAndView result;
		Collection<Application> applications;

		if (tripId == null)
			applications = this.applicationService.findAppliByManager();
		else {
			final Trip trip = this.tripService.findOne(tripId);
			applications = trip.getApplications();
		}

		result = new ModelAndView("application/list");

		final Collection<Application> acceptedApplications = new LinkedList<Application>();
		final Collection<Application> cancelledApplications = new LinkedList<Application>();
		final Collection<Application> dueApplications = new LinkedList<Application>();
		final Collection<Application> rejectedApplications = new LinkedList<Application>();
		final Collection<Application> pendingApplications = new LinkedList<Application>();

		for (final Application application : applications) {

			if (application.getStatus() == Status.ACCEPTED)
				acceptedApplications.add(application);
			if (application.getStatus() == Status.CANCELLED)
				cancelledApplications.add(application);
			if (application.getStatus() == Status.DUE)
				dueApplications.add(application);
			if (application.getStatus() == Status.REJECTED)
				rejectedApplications.add(application);
			if (application.getStatus() == Status.PENDING)
				pendingApplications.add(application);
		}

		result.addObject("acceptedApplications", acceptedApplications);
		result.addObject("cancelledApplications", cancelledApplications);
		result.addObject("dueApplications", dueApplications);
		result.addObject("rejectedApplications", rejectedApplications);
		result.addObject("pendingApplications", pendingApplications);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);

		if (application.getTrip().getManager().getId() != this.managerService.findByPrincipal().getId())
			return this.createMessageModelAndView("application.commit.ko", "/application/manager/list.do");

		result = this.createEditModelAndView(application, null);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application, null);
		else
			try {
				this.applicationService.save(application);

				result = this.createMessageModelAndView("application.commit.ok", "/application/manager/list.do");
			} catch (final Throwable ooops) {
				result = this.createMessageModelAndView(ooops.getMessage(), "/application/manager/list.do");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {

		ModelAndView result;

		result = new ModelAndView("application/edit");

		final HashMap<Status, String> possibleStatus = this.applicationService
				.getPossibleStatusManager(application.getStatus());

		result.addObject("possibleStatus", possibleStatus);
		result.addObject("application", application);
		result.addObject("requestUri", "application/manager/edit.do");
		result.addObject("message", message);

		return result;
	}

}
