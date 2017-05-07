package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;

public class ActivityOfferData {
	public static enum CopyDepth {
		SHALLOW, BOOKINGS
	};
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	private int capacity;
	private List<ActivityReservationData> bookings = new ArrayList<>();
	
	public ActivityOfferData(){
		
	}
	
	public ActivityOfferData(ActivityProvider provider, ActivityOffer offer, pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData.CopyDepth shallow) {
		this.begin = offer.getBegin();
		this.end = offer.getEnd();
		this.setCapacity(offer.getCapacity());

		switch (shallow) {
		case BOOKINGS:
			for (Booking booking : offer.getBookingSet()) {
				this.bookings.add(new ActivityReservationData(provider, offer, booking));
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}

	}
	
	public LocalDate getBegin() {
		return this.begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}
	
	public List<ActivityReservationData> getBookings() {
		return bookings;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
