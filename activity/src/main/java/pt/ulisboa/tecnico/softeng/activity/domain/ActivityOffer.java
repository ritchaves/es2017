package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityOffer extends ActivityOffer_Base {

	public ActivityOffer(Activity activity, LocalDate begin, LocalDate end) {
		checkArguments(activity, begin, end);
		
		setBegin(begin);
		setEnd(end);
		setCapacity(activity.getCapacity());
		
		activity.addActivityOffer(this);
		
		setActivity(activity);
	}
	
	public void delete() {
		setActivity(null);
		deleteDomainObject();
	}

	private void checkArguments(Activity activity, LocalDate begin, LocalDate end) {
		if (activity == null || begin == null || end == null) {
			throw new ActivityException();
		}

		if (end.isBefore(begin)) {
			throw new ActivityException();
		}
	}

	public int getNumberOfBookings() {
		int count = 0;
		for (Booking booking : getBookingSet()) {
			if (!booking.isCancelled()) {
				count++;
			}
		}
		return count;
	}

	boolean available(LocalDate begin, LocalDate end) {
		return hasVacancy() && matchDate(begin, end);
	}
	
	public void addBooking(Booking booking) {
		if (this.getCapacity() == getNumberOfBookings()) {
			throw new ActivityException();
		}
		getBookingSet().add(booking);
	}
	
	boolean matchDate(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new ActivityException();
		}

		return begin.equals(getBegin()) && end.equals(getEnd());
	}

	boolean hasVacancy() {
		return this.getCapacity() > getNumberOfBookings();
	}

	public Booking getBooking(String reference) {
		for (Booking booking : getBookingSet()) {
			if (booking.getReference().equals(reference)
					|| (booking.isCancelled() && booking.getCancellation().equals(reference))) {
				return booking;
			}
		}
		return null;
	}

}
