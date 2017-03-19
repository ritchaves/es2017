package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking {
	private static int counter = 0;

	private final String reference;
	private final LocalDate arrival;
	private final LocalDate departure;

	Booking(Hotel hotel, LocalDate arrival, LocalDate departure) {
		this.reference = hotel.getCode() + Integer.toString(++Booking.counter);
		this.arrival = arrival;
		this.departure = departure;
		if (this.arrival == null || this.departure == null){
			throw new HotelException();
		}
	}

	public String getReference() {
		return this.reference;
	}

	LocalDate getArrival() {
		return this.arrival;
	}

	LocalDate getDeparture() {
		return this.departure;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (arrival.isAfter(this.arrival) && arrival.isBefore(this.departure)) { /*Conflict Situation 1*/
			return true;
		}

		if (departure.isAfter(this.arrival) && departure.isBefore(this.departure)) { /*Conflict Situation 2*/
			return true;
		}

		if (arrival.isBefore(this.arrival) && departure.isAfter(this.departure)) { /*Conflict Situation 3*/
			return true;
		}

		return false;
	}
	
	boolean impossibility(LocalDate arrival, LocalDate departure) {
		if (departure.isBefore(arrival)){
			return true;
		}
		return false;
	}

}
