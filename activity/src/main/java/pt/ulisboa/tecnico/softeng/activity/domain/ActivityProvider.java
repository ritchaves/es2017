package pt.ulisboa.tecnico.softeng.activity.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;

public class ActivityProvider {
	public static Set<ActivityProvider> providers = new HashSet<>();

	static final int CODE_SIZE = 6;

	private final String name;
	private final String code;
	private final Set<Activity> activities = new HashSet<>();

	public ActivityProvider(String code, String name) {
		checkArguments(code, name);

		this.code = code;
		this.name = name;

		ActivityProvider.providers.add(this);
	}

	private void checkArguments(String code, String name) {
		if (code == null || name == null || code.trim().equals("") || name.trim().equals("")) {
			throw new ActivityException();
		}

		if (code.length() != ActivityProvider.CODE_SIZE) {
			throw new ActivityException();
		}

		for (ActivityProvider activityProvider : providers) {
			if (activityProvider.getCode().equals(code) || activityProvider.getName().equals(name)) {
				throw new ActivityException();
			}
		}
	}

	String getName() {
		return this.name;
	}

	String getCode() {
		return this.code;
	}

	int getNumberOfActivities() {
		return this.activities.size();
	}

	void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public List<ActivityOffer> findOffer(LocalDate begin, LocalDate end, int age) {
		List<ActivityOffer> result = new ArrayList<>();
		for (Activity activity : this.activities) {
			result.addAll(activity.getOffers(begin, end, age));
		}
		return result;
	}

	public static String reserveActivity(LocalDate begin, LocalDate end, int age) {
		List<ActivityOffer> offers;
		for (ActivityProvider provider : ActivityProvider.providers) {
			offers = provider.findOffer(begin, end, age);
			if (!offers.isEmpty()) {
				return new Booking(provider, offers.get(0)).getReference();
			}
		}
		throw new ActivityException();
	}

	public static String cancelReservation(String activityConfirmation) {
		// TODO implement
		throw new ActivityException();
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		ActivityReservationData reservationData = new ActivityReservationData();
		for (ActivityProvider provider : providers){
			for(Activity activity : provider.activities){
				for(ActivityOffer actoffer : activity.offers){
					for(Booking booking : actoffer.getBookings()){
						if(reference.equals(booking.getReference())){
							reservationData.setReference(reference);
							reservationData.setName(provider.name);
							reservationData.setCode(provider.code);
							reservationData.setBegin(actoffer.getBegin());
							reservationData.setEnd(actoffer.getEnd());
							return reservationData;
						}	
					}
				}
			}		
		}
	throw new ActivityException();
	}	

}
