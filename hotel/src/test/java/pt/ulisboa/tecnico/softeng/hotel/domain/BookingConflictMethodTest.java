package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConflictMethodTest {
	Booking booking;
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres");

		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		this.booking = new Booking(this.hotel, arrival, departure);
	}
	
	

	@Test
	public void noConflictBefore() {
		LocalDate arrival = new LocalDate(2016, 12, 16);
		LocalDate departure = new LocalDate(2016, 12, 19);

		Assert.assertFalse(this.booking.conflict(arrival, departure));
	}

	@Test
	public void noConflictAfter() {
		LocalDate arrival = new LocalDate(2016, 12, 24);
		LocalDate departure = new LocalDate(2016, 12, 30);

		Assert.assertFalse(this.booking.conflict(arrival, departure));
	}
	
	@Test 
	public void conflict1() {
		LocalDate arrival = new LocalDate(2016, 12, 22);
		LocalDate departure = new LocalDate(2016, 12, 26);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}
	
	@Test 
	public void conflict2() {
		LocalDate arrival = new LocalDate(2016, 12, 16);
		LocalDate departure = new LocalDate(2016, 12, 22);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}
	
	@Test 
	public void conflict3() {
		LocalDate arrival = new LocalDate(2016, 12, 16);
		LocalDate departure = new LocalDate(2016, 12, 26);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
