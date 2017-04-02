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
	private LocalDate arrival = new LocalDate(2016, 12, 19);
	private LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres");
		new Room(this.hotel, "01", Type.DOUBLE);
		new Room(this.hotel , "02" , Type.SINGLE);
	}
	
	@After
	public void tearDown(){
		Hotel.hotels.clear();
	}

    @Test
	public void success() {
    	this.reference = Hotel.reserveRoom(Type.DOUBLE, arrival, departure);
		RoomBookingData bookingData = Hotel.getRoomBookingData(reference);
		
		Assert.assertEquals(bookingData.getReference(), this.reference);
		Assert.assertEquals(bookingData.getHotelName(), this.hotel.getName());
		Assert.assertEquals(bookingData.getHotelCode(), this.hotel.getCode());
		Assert.assertEquals(bookingData.getRoomNumber(), "01");
		Assert.assertEquals(bookingData.getRoomType(), Type.DOUBLE.toString());
		Assert.assertEquals(bookingData.getArrival(), this.arrival);
		Assert.assertEquals(bookingData.getDeparture(), this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullReference() {
		Hotel.getRoomBookingData(null);
	}

	@Test(expected = HotelException.class)
	public void whiteSpacesReference() {
		Hotel.getRoomBookingData("  ");
	}

	@Test(expected = HotelException.class)
	public void emptyReference() {
		Hotel.getRoomBookingData("");
	}

	@Test(expected = HotelException.class)
	public void referenceDoesNotExist() {
		Hotel.getRoomBookingData("64165416511198419");
	}

}




