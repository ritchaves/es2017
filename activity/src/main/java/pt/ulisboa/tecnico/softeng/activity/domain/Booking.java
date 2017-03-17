package pt.ulisboa.tecnico.softeng.activity.domain;

import pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;

public class Booking {
	private static int counter = 0;

	private final String reference;

	public Booking(ActivityProvider provider, ActivityOffer offer) {
				
		this.reference = provider.getCode() + Integer.toString(++Booking.counter);
		offer.addBooking(this);

		if (!offer.hasVacancy()){
			throw new ActivityException("The Reservations must be equal or lower then the capacity");
		}
		
	}

	public String getReference() {
		return this.reference;
	}
}
