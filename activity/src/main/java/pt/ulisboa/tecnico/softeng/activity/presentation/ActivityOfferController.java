package pt.ulisboa.tecnico.softeng.activity.presentation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData.CopyDepth;




@Controller
@RequestMapping(value = "/activityProviders/{activityProviderCode}/activities/{activityCode}/activityOffers")
public class ActivityOfferController {
	private static Logger logger = LoggerFactory.getLogger(ActivityOfferController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String showActivityOffer(Model model, @PathVariable String activityProviderCode, @PathVariable String activityCode) {
		logger.info("showActivityOffers code:{}", activityCode);
		
		ActivityData activityData = ActivityInterface.getActivityDataByName(activityProviderCode, activityCode, CopyDepth.ACTIVITIES);
		ActivityProviderData activityProviderData = ActivityInterface.getActivityProviderDataByCode(activityProviderCode, CopyDepth.ACTIVITIES);
		
		if (activityProviderData == null) {
			model.addAttribute("error", "Error: it does not exist an activity provider with the code " + activityProviderCode);
			model.addAttribute("activityProvider", new ActivityProviderData());
			model.addAttribute("activityProviders", ActivityInterface.getActivityProviders());
			return "activityProviders";
		}
		if (activityData == null) {
			model.addAttribute("error", "Error: it does not exist an activity with the code " + activityCode);
			model.addAttribute("activity", new ActivityData());
			model.addAttribute("activities", ActivityInterface.getActivities(activityProviderCode));
			return "activities";
		} else {
			model.addAttribute("activityOffer", new ActivityOfferData());
			model.addAttribute("activityProvider", activityProviderData);
			model.addAttribute("activity", activityData);
			return "activityOffers";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submitActivityOffer(Model model, @PathVariable String activityProviderCode,
			@PathVariable String activityCode, @ModelAttribute ActivityOfferData activityOfferData) {
		logger.info("activityOfferSubmit, activityCode:{} ,begin:{}, end:{}", activityCode,
				activityOfferData.getBegin(), activityOfferData.getEnd());

		try {
			ActivityInterface.createActivityOffer(activityProviderCode, activityCode, activityOfferData);
		} catch (ActivityException ae) {
			model.addAttribute("error", "Error: it was not possible to create the activityOffer");
			model.addAttribute("activityOffer", activityOfferData);
			model.addAttribute("activity", ActivityInterface.getActivityDataByName(activityProviderCode, activityCode, CopyDepth.ACTIVITIES));
			return "activities";
		}

		return "redirect:/activityProviders/" + activityProviderCode + "/activities" + activityCode + "/activityOffers";
	}
//	
//	@RequestMapping(method = RequestMethod.GET, value = "/activityOffers/{clientID}/bookings")
//	public String showBookingsByOffer(Model model, @PathVariable String activityProviderCode,
//			@PathVariable String activityCode, @ModelAttribute ActivityReservationData activityReservationData) {
//		logger.info("showBookingsByOffer activityCode:{}, bookingID:{}", activityCode, activityReservationData.getReference());
//		
//		ActivityData activityData = ActivityInterface.getActivityDataByName(activityProviderCode, activityCode, CopyDepth.ACTIVITIES);
//		List<ActivityOfferData> activityOfferDatas = ActivityInterface.getActivityOffer(activityData);
//		if (activityOfferDatas == null) {
//			model.addAttribute("error", "Error: it does not exist a activityOffer for the adventure with code" + activityCode);
//			model.addAttribute("activityOffer", new ActivityOfferData());
//			model.addAttribute("activityOffers", ActivityInterface.getActivityOffer(activityData));
//			return "activityOffers";
//		}
//		
//		else {	
//			for (ActivityOfferData activityOfferData : activityOfferDatas) {
//			model.addAttribute("booking",activityOfferData.getBookings());
//			}
//			return "bookings";
//		}
//	
//	}
}