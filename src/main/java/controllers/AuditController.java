/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.TripService;
import domain.Audit;
import domain.Trip;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	AuditService	auditService;
	@Autowired
	TripService		tripService;


	// Constructors -----------------------------------------------------------

	public AuditController() {
		super();
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;
		final Collection<Audit> audits;
		final Trip trip = this.tripService.findOne(tripId);
		audits = trip.getAudits();

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);

		return result;
	}
}
