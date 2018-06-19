/*
 * CustomerController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.explorer;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

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
import domain.Application;
import domain.Status;
import services.ApplicationService;
import services.ExplorerService;
import services.MyMessageService;

@Controller
@RequestMapping("/application/explorer")
public class ApplicationExplorerController extends AbstractController {

	@Autowired
	ApplicationService applicationService;

	@Autowired
	MyMessageService messageService;

	@Autowired
	ExplorerService explorerService;

	// Constructors -----------------------------------------------------------

	public ApplicationExplorerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		result = new ModelAndView("application/list");

		final Collection<Application> applications = this.applicationService.findAppliByExplorer();

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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;

		final Application application = this.applicationService.create(tripId);

		result = this.createEditModelAndView(application, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		if (application.getApplicant().getId() != this.explorerService.findByPrincipal().getId())
			return this.createMessageModelAndView("application.commit.ko", "/application/explorer/list.do");

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

				result = this.createMessageModelAndView("application.commit.ok", "/application/explorer/list.do");
			} catch (final Throwable ooops) {

				result = this.createMessageModelAndView(ooops.getMessage(), "/application/explorer/list.do");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {

		ModelAndView result;

		result = new ModelAndView("application/edit");

		final HashMap<Status, String> possibleStatus = this.applicationService
				.getPossibleStatusExplorer(application.getStatus());

		result.addObject("possibleStatus", possibleStatus);
		result.addObject("application", application);
		result.addObject("requestUri", "application/explorer/edit.do");
		result.addObject("message", message);

		return result;
	}

}
