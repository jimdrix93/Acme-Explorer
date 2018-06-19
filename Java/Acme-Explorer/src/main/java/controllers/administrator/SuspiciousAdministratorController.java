
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import services.AdministratorService;
import services.ManagerService;
import services.RangerService;

@Controller
@RequestMapping("/suspicious/administrator")
public class SuspiciousAdministratorController extends AbstractController {

	@Autowired
	RangerService rangerService;

	@Autowired
	ManagerService managerService;

	@Autowired
	AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public SuspiciousAdministratorController() {
		super();
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView result;
		
		final Collection<Manager> managers = this.managerService.findManagerBySuspicious(true);
		final Collection<Ranger> rangers = this.rangerService.findRangerBySuspicious(true);

		result = new ModelAndView("suspicious/list");
		result.addObject("managers", managers);
		result.addObject("rangers", rangers);

		return result;
	}

	
	@RequestMapping(value = "/banManager", method = RequestMethod.GET)
	public ModelAndView banManager(@RequestParam final int managerId) {
		ModelAndView result;
		
		try {
			Manager manager = this.managerService.findOne(managerId);
			manager.setAccountActivated(false);
			this.managerService.save(manager);
			result = this.createMessageModelAndView("suspicious.commit.ok", "suspicious/administrator/list.do");
		} catch ( final Throwable oops) {
			result = this.createMessageModelAndView(oops.getLocalizedMessage(), "suspicious/administrator/list.do");	
		}
		return result;
	}

	@RequestMapping(value = "/unbanManager", method = RequestMethod.GET)
	public ModelAndView unbanManager(@RequestParam final int managerId) {
		ModelAndView result;
		
		try {
			Manager manager = this.managerService.findOne(managerId);
			manager.setAccountActivated(true);
			this.managerService.save(manager);
			result = this.createMessageModelAndView("suspicious.commit.ok", "suspicious/administrator/list.do");
		} catch ( final Throwable oops) {
			result = this.createMessageModelAndView(oops.getLocalizedMessage(), "suspicious/administrator/list.do");	
		}
		return result;
	}
	
	@RequestMapping(value = "/banRanger", method = RequestMethod.GET)
	public ModelAndView banExplorer(@RequestParam final int rangerId) {
		ModelAndView result;
		
		try {
			Ranger ranger = this.rangerService.findOne(rangerId);
			ranger.setAccountActivated(false);
			this.rangerService.save(ranger);
			result = this.createMessageModelAndView("suspicious.commit.ok", "suspicious/administrator/list.do");
		} catch ( final Throwable oops) {
			result = this.createMessageModelAndView(oops.getLocalizedMessage(), "suspicious/administrator/list.do");	
		}
		return result;
	}

	@RequestMapping(value = "/unbanRanger", method = RequestMethod.GET)
	public ModelAndView unbaExplorer(@RequestParam final int rangerId) {
		ModelAndView result;
		
		try {
			Ranger ranger = this.rangerService.findOne(rangerId);
			ranger.setAccountActivated(true);
			this.rangerService.save(ranger);
			result = this.createMessageModelAndView("suspicious.commit.ok", "suspicious/administrator/list.do");
		} catch ( final Throwable oops) {
			result = this.createMessageModelAndView(oops.getLocalizedMessage(), "suspicious/administrator/list.do");	
		}
		return result;
	}

}