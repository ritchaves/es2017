package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class HotelData {
	public static enum CopyDepth {
		SHALLOW, ROOM, BOOKING
	};

	private String name;
	private String code;
	private List<RoomData> rooms = new ArrayList<>();
	//private List<BulkData> bulks = new ArrayList<>();

	public HotelData() {
	}

	public HotelData(Hotel hotel, CopyDepth depth) {
		this.name = hotel.getName();
		this.code = hotel.getCode();

		switch (depth) {
		case ROOM:
			for (Room room : hotel.getRoomSet()) {
				//this.adventures.add(new RoomData(adventure));
				//FIXME this.rooms.add(new RoomData(room));
			}
			break;
		/*case BOOKING:
			for (Booking bulkRoomBooking : broker.getRoomBulkBookingSet()) {
				this.bulks.add(new BulkData(bulkRoomBooking));
			}
			break;*/
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
	}

/*	public List<BulkData> getBulks() {
		return this.bulks;
	}

	public void setBulks(List<BulkData> bulks) {
		this.bulks = bulks;
	}*/

}
