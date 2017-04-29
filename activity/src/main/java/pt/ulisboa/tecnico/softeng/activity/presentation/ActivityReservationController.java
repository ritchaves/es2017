package pt.ulisboa.tecnico.softeng.activity.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;


@Controller
@RequestMapping(value = "/activity")
public class ActivityReservationController {
	private static Logger logger = LoggerFactory.getLogger(ActivityReservationController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String activityReservationForm(Model model) {
		logger.info("activityReservationForm");
		model.addAttribute("activityReservation", new ActivityReservationData());
	//	model.addAttribute("brokers", ActivityInterface.getActivityReservationData());
		return "brokers";
	}
}
