/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService		auditService;
	@Autowired
	private AuditorService	auditorService;


	// Constructors -----------------------------------------------------------

	public AuditAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Audit> audits;

		final Auditor auditor = this.auditorService.findByPrincipal();
		audits = this.auditService.findAuditByAuditor(auditor);

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestUri", "audit/auditor/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		final ModelAndView result;
		final Audit audit = this.auditService.findOne(auditId);
		final Auditor auditor = this.auditorService.findByPrincipal();

		if (audit.getAuditor().getId() != auditor.getId() || audit.isFinalMode()) {
			result = this.createMessageModelAndView("audit.commit.error", "audit/auditor/list.do");
		} else {
			result = new ModelAndView("audit/auditor/edit");
			result.addObject("audit", audit);
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int tripId) {
		final ModelAndView result;
		final Audit audit = this.auditService.create(tripId);

		result = new ModelAndView("audit/auditor/create");
		result.addObject("audit", audit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(audit, null);
		else
			try {
				this.auditService.save(audit);
				result = this.createMessageModelAndView("audit.commit.ok", "audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}


	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView save(final Audit audit) {
		ModelAndView result;
		try {
			
			final Auditor auditor = this.auditorService.findByPrincipal();
			if (audit.getAuditor().getId() != auditor.getId() || audit.isFinalMode()) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			} else {
				this.auditService.delete(audit);
				result = this.createMessageModelAndView("audit.commit.ok", "audit/auditor/list.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "audit.commit.error");
		}
		return result;
	}

	
	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("audit/auditor/edit");

		result.addObject("audit", audit);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
