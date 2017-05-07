package pt.ulisboa.tecnico.softeng.activity.presentation;

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
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData.CopyDepth;


@Controller
@RequestMapping(value = "/activityProviders/{code}/activities")
public class ActivityController {
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String showActivities(Model model, @PathVariable String code) {
		logger.info("showActivities code:{}", code);
		
		ActivityProviderData activityProviderData = ActivityInterface.getActivityProviderDataByCode(code, CopyDepth.ACTIVITIES);
		
		if (activityProviderData == null) {
			model.addAttribute("error", "Error: it does not exist an activity provider with the code " + code);
			model.addAttribute("activityProvider", new ActivityProviderData());
			model.addAttribute("activityProviders", ActivityInterface.getActivityProviders());
			return "activityProviders";
		} else {
			model.addAttribute("activity", new ActivityData());
			model.addAttribute("activityProvider", activityProviderData);
			return "activities";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submitActivity(Model model, @PathVariable String code,
			@ModelAttribute ActivityData activityData) {
		logger.info("activitySubmit activityProviderCode:{}, name:{}, minAge:{}, maxAge:{}, capacity:{}", code,
				activityData.getName(), activityData.getMinAge(), activityData.getMaxAge(), activityData.getCapacity());

		try {
			ActivityInterface.createActivity(code, activityData);
		} catch (ActivityException ae) {
			model.addAttribute("error", "Error: it was not possible to create the activity");
			model.addAttribute("activity", activityData);
			model.addAttribute("activityProvider", ActivityInterface.getActivityProviderDataByCode(code, CopyDepth.ACTIVITIES));
			return "activities";
		}

		return "redirect:/activityProviders/" + code + "/activities";
	}
}

