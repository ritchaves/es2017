package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;


public class BookingData {
	private String reference;
	private String cancellation;
	private LocalDate cancellationDate;

	public BookingData() {
	}

	public BookingData(ActivityOffer offer, Booking booking) {
		this.reference = booking.getReference();
		this.cancellation = booking.getCancel();
		this.cancellationDate = booking.getCancellationDate();
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCancellation() {
		return this.cancellation;
	}

	public void setCancellation(String cancellation) {
		this.cancellation = cancellation;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}

	public void setCancellationDate(LocalDate cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

}