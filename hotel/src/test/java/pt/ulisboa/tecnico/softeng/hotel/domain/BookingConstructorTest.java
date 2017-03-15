package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConstructorTest {

	@Test
	public void success() {
		Hotel hotel = new Hotel("XPTO123", "Londres");

		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 21);

		Booking booking = new Booking(hotel, arrival, departure);

		Assert.assertTrue(booking.getReference().startsWith(hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(arrival, booking.getArrival());
		Assert.assertEquals(departure, booking.getDeparture());
	}
	
	@Test (expected = HotelException.class)
	public void departureBeforeArrival() {
		Hotel hotel = new Hotel("XPTO123", "Londres");
		
		LocalDate arrival = new LocalDate(2016, 12, 18);
		LocalDate departure = new LocalDate(2016, 12, 16);
		
		Booking booking = new Booking(hotel, arrival, departure);

		Assert.assertTrue(booking.getReference().startsWith(hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(arrival, booking.getArrival());
		Assert.assertEquals(departure, booking.getDeparture());
		Assert.assertTrue(booking.impossibility(arrival, departure));
	}

	@Test (expected = HotelException.class)
	public void ifArrivalNull() {
		Hotel hotel = new Hotel("XPTO123", "Londres");
		
		LocalDate arrival = null;
		LocalDate departure = new LocalDate(2016, 12, 2 );
		
		Booking booking = new Booking(hotel, arrival, departure);
		Assert.assertTrue(booking.getReference().startsWith(hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(arrival, booking.getArrival());
		Assert.assertEquals(departure, booking.getDeparture());
		Assert.assertTrue(booking.impossibility(arrival, departure));
	}
	
	@Test (expected = HotelException.class)
	public void ifDepartureNull() {
		Hotel hotel = new Hotel("XPTO123", "Londres");
		
		LocalDate arrival = new LocalDate(2016, 12, 18);
		LocalDate departure = null;
		
		Booking booking = new Booking(hotel, arrival, departure);
		Assert.assertTrue(booking.getReference().startsWith(hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(arrival, booking.getArrival());
		Assert.assertEquals(departure, booking.getDeparture());
		Assert.assertTrue(booking.impossibility(arrival, departure));
		
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
