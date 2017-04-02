package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.broker.domain.AdventureState;

public class BulkRoomBooking {
	private static final int MAX_REMOTE_ERRORS = 10;
	private static final int MAX_HOTEL_EXCEPTIONS = 3;
	private final Set<String> references = new HashSet<>();
	private final int number;
	private final LocalDate arrival;
	private final LocalDate departure;
	private boolean cancelled = false;

	public BulkRoomBooking(int number, LocalDate arrival, LocalDate departure) {
		this.number = number;
		this.arrival = arrival;
		this.departure = departure;
	}

	public Set<String> getReferences() {
		return this.references;
	}

	public int getNumber() {
		return this.number;
	}

	public LocalDate getArrival() {
		return this.arrival;
	}

	public LocalDate getDeparture() {
		return this.departure;
	}
	
	int numOfRemoteErrors = 0;
	int numberOfHotelExceptions = 0;
	
	int getNumOfRemoteErrors() {
		return this.numOfRemoteErrors;
	}
	
	int getNumOfHotelExceptions() {
		return this.numberOfHotelExceptions;
	}

	void incNumOfRemoteErrors() {
		this.numOfRemoteErrors++;
	}
	
	void incNumofHotelExceptions() {
		this.numberOfHotelExceptions++;
	}
	
	void resetNumOfRemoteErrors() {
		this.numOfRemoteErrors = 0;
	}
	
	void resetNumOfHotelExceptions() {
		this.numberOfHotelExceptions = 0;
	}
	
	public void processBooking() {
		if (this.cancelled) {
			return;
		}
		
		this.getNumOfRemoteErrors();
		this.getNumOfHotelExceptions();

		try {
			this.references.addAll(HotelInterface.bulkBooking(this.number, this.arrival, this.departure));
			this.resetNumOfHotelExceptions();
			this.resetNumOfRemoteErrors();
			return;
		} catch (HotelException he) {
			this.incNumofHotelExceptions();
			if (this.numberOfHotelExceptions == MAX_HOTEL_EXCEPTIONS) {
				this.cancelled = true;
				return;
			}
			this.resetNumOfRemoteErrors();
			return;
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();
			if (this.numOfRemoteErrors == MAX_REMOTE_ERRORS) {
				this.cancelled = true;
				return;
			}
			this.resetNumOfHotelExceptions();
			return;
		}
	}

	public String getReference(String type) {
		if (this.cancelled) {
			return null;
		}

		for (String reference : this.references) {
			RoomBookingData data = null;
			try {
				data = HotelInterface.getRoomBookingData(reference);
				// this.numberOfRemoteErrors = 0;
			} catch (HotelException he) {
				// this.numberOfRemoteErrors = 0;
			} catch (RemoteAccessException rae) {
				// this.numberOfRemoteErrors++;
				// if (this.numberOfRemoteErrors == MAX_REMOTE_ERRORS) {
				// this.cancelled = true;
				// }
			}

			if (data != null && data.getRoomType().equals(type)) {
				this.references.remove(reference);
				return reference;
			}
		}
		return null;
	}
}
