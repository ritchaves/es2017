package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;

public class ActivityData {
	
	public static enum CopyDepth {
		SHALLOW, OFFERS, BOOKINGS
	};
	
	private String name;
	private int minAge;
	private int maxAge;
	private int capacity;
	private List<ActivityOfferData> offers = new ArrayList<>();
	
	public ActivityData(){
		
	}
	
	public ActivityData(ActivityProvider provider, Activity activity, pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData.CopyDepth shallow) {
		this.name = activity.getName();
		this.minAge = activity.getMinAge();
		this.maxAge = activity.getMaxAge();
		this.capacity = activity.getCapacity();

		switch (shallow) {
		case OFFERS:
			for (ActivityOffer offer : activity.getActivityOfferSet()) {
				this.offers.add(new ActivityOfferData(provider, offer, CopyDepth.SHALLOW));
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}

	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ActivityOfferData> getActivityOffers() {
		return this.offers;
	}

	public void setAdventures(List<ActivityOfferData> offers) {
		this.offers = offers;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}
}
