package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class Booking extends Booking_Base {
	private static int counter = 0;
	
	private String cancel;
	private LocalDate cancellationDate;


	public Booking(ActivityProvider provider, ActivityOffer offer) {
		checkArguments(provider, offer);
		
		setReference(provider.getCode() + Integer.toString(++Booking.counter));

		offer.addBooking(this);
		
		setActivityOffer(offer);
	}
	
	public void delete() {
		setActivityOffer(null);
		deleteDomainObject();
	}
	
 	private void checkArguments(ActivityProvider provider, ActivityOffer offer) {
		if (provider == null || offer == null) {
			throw new ActivityException();
		}
	}

	public String getCancellation() {
		return this.cancel;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}

	public String cancel() {
		this.cancel = "CANCEL" + getReference();
		this.cancellationDate = new LocalDate();
		return this.cancel;
	}

	public boolean isCancelled() {
		return this.cancel != null;
	}
}
