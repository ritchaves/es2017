package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class RoomData {
	public static enum CopyDepth {
		SHALLOW, ROOM, BOOKING
	};
	
	public static enum Type {
		SINGLE, DOUBLE
	};

	private String number;
	
	//private List<RoomData> rooms = new ArrayList<>();
	private List<BookingData> bookings = new ArrayList<>();

	private pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type Type;

	public RoomData() {
	}

	public RoomData(Room room, CopyDepth depth) {
		this.number = room.getNumber();
		this.Type = room.getType();

		switch (depth) {
		/*case ROOM:
			for (Room room : hotel.getRoomSet()) {
				//this.adventures.add(new RoomData(adventure));
				this.rooms.add(new RoomData(room));
			}
			break;*/
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
/*TODO
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<RoomData> getAdventures() {
		return this.rooms;
	}

	public void setAdventures(List<RoomData> adventures) {
		this.rooms = adventures;
	}*/

	public List<BookingData> getBulks() {
		return this.bookings;
	}

	public void setBulks(List<BookingData> bulks) {
		this.bookings = bookings;
	}

}
