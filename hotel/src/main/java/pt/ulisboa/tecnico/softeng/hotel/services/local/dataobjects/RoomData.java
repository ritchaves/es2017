package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
//import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData.CopyDepth;

public class RoomData {
	public static enum CopyDepth {
		SHALLOW, BOOKING
	};
	
	public static enum Type {
		SINGLE, DOUBLE
	};

	private String number;
	
	private pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type type;
	
	private List<BookingData> bookings = new ArrayList<>();

	public RoomData() {
	}

	public RoomData(Room room, pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData.CopyDepth depth) {
		this.number = room.getNumber();
		this.type = room.getType();

		switch (depth) {
		case BOOKING:
			for(Booking booking : room.getBookingSet()){
				this.bookings.add(new BookingData(room, booking));
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type getType() {
		return this.type;
	}

	public void setType(pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type type) {
		this.type = type;
	}

	public List<BookingData> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<BookingData> bookings) {
		this.bookings = bookings;
	}
}
