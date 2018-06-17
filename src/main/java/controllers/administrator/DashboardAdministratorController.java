
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Status;
import services.AdministratorService;
import services.ApplicationService;
import services.LegalTextService;
import services.TripService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	AdministratorService administratorService;

	@Autowired
	TripService tripService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	LegalTextService legalTextService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		result = new ModelAndView("dashboard/display");

		final Object statisticsApplications = this.administratorService.findStatisticsApplicationsTrips();
		result.addObject("statisticsApplications", statisticsApplications);

		final Object statisticsManager = this.administratorService.findStatisticsManagerTrips();
		result.addObject("statisticsManager", statisticsManager);

		final Object statisticsPrice = this.administratorService.findStatisticsPriceTrips();
		result.addObject("statisticsPrice", statisticsPrice);

		final Object statisticsRanger = this.tripService.tripsPerRanger();
		result.addObject("statisticsRanger", statisticsRanger);

		final Object ratioDue = this.applicationService.getRatioWithStatus(Status.DUE);
		result.addObject("ratioDue", ratioDue);

		final Object ratioAccepted = this.applicationService.getRatioWithStatus(Status.ACCEPTED);
		result.addObject("ratioAccepted", ratioAccepted);

		final Object ratioCancelled = this.applicationService.getRatioWithStatus(Status.CANCELLED);
		result.addObject("ratioCancelled", ratioCancelled);

		final Object ratioPending = this.applicationService.getRatioWithStatus(Status.PENDING);
		result.addObject("ratioPending", ratioPending);

		final Object ratioTripsCancelled = this.tripService.getRatioOfTripsCancelled();
		result.addObject("ratioTripsCancelled", ratioTripsCancelled);

		final Collection<Object> ratio10Origen = this.tripService.getTripsWithMoreApplications();
		result.addObject("ratio10", ratio10Origen);

		final Collection<Object> statisticsLegalText = this.legalTextService.timesReferenced();
		result.addObject("statisticsLegalText", statisticsLegalText);

		return result;

	}

}
