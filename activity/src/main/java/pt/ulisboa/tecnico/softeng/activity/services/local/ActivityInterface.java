package pt.ulisboa.tecnico.softeng.activity.services.local;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData.CopyDepth;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;


public class ActivityInterface {
	
/*	<---------------------- Activity Provider -------------------------->   */
	
	//Create provider
	@Atomic(mode = TxMode.WRITE)
	public static void createActivityProvider(ActivityProviderData activityProviderData) {
		new ActivityProvider(activityProviderData.getCode(), activityProviderData.getName());
	}
	
	//List Providers
	@Atomic(mode = TxMode.READ)
	public static List<ActivityProviderData> getActivityProviders() {
		List<ActivityProviderData> providers = new ArrayList<>();
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			providers.add(new ActivityProviderData(provider, CopyDepth.SHALLOW));
		}
		return providers;
	}
	
	// Get ProviderData by the code
	@Atomic(mode = TxMode.READ)
	public static ActivityProviderData getActivityProviderDataByCode(String activityProviderCode, CopyDepth depth) {
		ActivityProvider activityProvider = getActivityProviderByCode(activityProviderCode);

		if (activityProvider != null) {
			return new ActivityProviderData(activityProvider, depth);
		} else {
			return null;
		}
	}
	
	// Get Provider by code
	private static ActivityProvider getActivityProviderByCode(String code) {
		for (ActivityProvider activityProvider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			if (activityProvider.getCode().equals(code)) {
				return activityProvider;
			}
		}
		return null;
	}
	
	/*	<---------------------- Activity  -------------------------->   */

	//Create activity
	@Atomic(mode = TxMode.WRITE)
	public static void createActivity(String activityProviderCode, ActivityData activityData) {
		new Activity(getActivityProviderByCode(activityProviderCode), activityData.getName(), activityData.getMinAge(), activityData.getMaxAge(), activityData.getCapacity());
	}
	
	//List activities from a provider
	@Atomic(mode = TxMode.READ)
	public static List<ActivityData> getActivities(String activityProviderCode) {
		List<ActivityData> activities = new ArrayList<>();
		ActivityProvider provider = getActivityProviderByCode(activityProviderCode);
		for (Activity activity : provider.getActivitySet()) {
			activities.add(new ActivityData(provider, activity, CopyDepth.SHALLOW));
		}
		return activities;
	}
	
	// Get ActivityData by the name
	@Atomic(mode = TxMode.READ)
	public static ActivityData getActivityDataByName(String activityProviderCode, String activityName, CopyDepth depth) {
		Activity activity = getActivityByName(activityProviderCode, activityName);
		ActivityProvider provider = getActivityProviderByCode(activityProviderCode);

		if (activity != null) {
			return new ActivityData(provider, activity, depth);
		} else {
			return null;
		}
	}
	
	//Get Activity by the name
	private static Activity getActivityByName(String activityProviderCode, String name) {
		ActivityProvider provider = getActivityProviderByCode(activityProviderCode);
		for (Activity activity : provider.getActivitySet()) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		return null;
	}
	
	/*	<---------------------- Activity Reservation -------------------------->   */
	
	@Atomic(mode = TxMode.WRITE)
	public static String reserveActivity(LocalDate begin, LocalDate end, int age) {
		List<ActivityOffer> offers;
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			offers = provider.findOffer(begin, end, age);
			if (!offers.isEmpty()) {
				return new Booking(offers.get(0)).getReference();
			}
		}
		throw new ActivityException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static String cancelReservation(String reference) {
		Booking booking = getBookingByReference(reference);
		if (booking != null) {
			return booking.cancel();
		}
		throw new ActivityException();
	}

	@Atomic(mode = TxMode.READ)
	public static ActivityReservationData getActivityReservationData(String reference) {
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			for (Activity activity : provider.getActivitySet()) {
				for (ActivityOffer offer : activity.getActivityOfferSet()) {
					Booking booking = offer.getBooking(reference);
					if (booking != null) {
						return new ActivityReservationData(provider, offer, booking);
					}
				}
			}
		}
		throw new ActivityException();
	}

	private static Booking getBookingByReference(String reference) {
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			Booking booking = provider.getBooking(reference);
			if (booking != null) {
				return booking;
			}
		}
		return null;
	}
	
	/*	<---------------------- Activity Offer -------------------------->   */


	//Create Offer
	@Atomic(mode = TxMode.WRITE)
	public static void createActivityOffer(String activityProviderCode, String activityCode, ActivityOfferData activityOfferData) {
		Activity activity = getActivityByName(activityProviderCode,activityCode);
		new ActivityOffer(activity, activityOfferData.getBegin(), activityOfferData.getEnd());
	}
	
	//List Offers
	@Atomic(mode = TxMode.READ)
	public static List<ActivityOfferData> getActivityOffer(ActivityData activityData) {
		return activityData.getActivityOffers();}



}
