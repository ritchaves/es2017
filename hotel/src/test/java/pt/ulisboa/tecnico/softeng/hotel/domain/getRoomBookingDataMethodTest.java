package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;


public class getRoomBookingDataMethodTest {
	private String reference;
	private String hotelName;
	private String hotelCode;
	private String roomNumber;
	private String roomType;
	private LocalDate arrival = new LocalDate(2016, 12, 19);
	private LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
		this.hotel = new Hotel("123", "Hilton");
		this.room = new Room(this.hotel, "01", Type.DOUBLE);
		this.booking = new Booking( this.hotel , this.arrival , this.departure);
	}

/*	@Test
	public void success() {
		RoomBookingData bookingData = Hotel.getRoomBookingData(booking.getReference());
		
		Assert.assertEquals(this.reference, bookingData.getReference());
		Assert.assertEquals(this.hotelName, bookingData.getHotelName());
		Assert.assertEquals(this.hotelCode, bookingData.getHotelCode());
		Assert.assertEquals(this.roomNumber, bookingData.getRoomNumber());
		Assert.assertEquals(this.roomType, bookingData.getRoomType());
		Assert.assertEquals(this.arrival, bookingData.getArrival());
		Assert.assertEquals(this.departure, bookingData.getDeparture());
		
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(null, this.arrival, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(this.hotel, null, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(this.hotel, this.arrival, null);
	}

	@Test(expected = HotelException.class)
	public void departureBeforeArrival() {
		new Booking(this.hotel, this.arrival, this.arrival.minusDays(1));
	}

	@Test
	public void arrivalEqualDeparture() {
		new Booking(this.hotel, this.arrival, this.arrival);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
*/
}




