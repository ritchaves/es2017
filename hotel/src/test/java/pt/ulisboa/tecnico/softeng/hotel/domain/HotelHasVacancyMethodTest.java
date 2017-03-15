package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelHasVacancyMethodTest {
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris");
		new Room(this.hotel, "01", Type.DOUBLE);
	}

	@Test
	public void hasVacancy() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 21);

		Room room = this.hotel.hasVacancy(Type.DOUBLE, arrival, departure);

		Assert.assertEquals("01", room.getNumber());
	}
	@Test (expected = HotelException.class)
	public void FailureSingle(){
		LocalDate arrival = new LocalDate(2016,12,19);
		LocalDate departure = new LocalDate(2016,12,21);
		
		Room room = this.hotel.hasVacancy(Type.SINGLE, arrival, departure);
		
		Assert.assertEquals("01", room.getNumber());
	}

	@Test
	public void SameDate() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 19);
		
		Room room = this.hotel.hasVacancy(Type.DOUBLE, arrival, departure);
		
		Assert.assertEquals("01", room.getNumber());
	}
	
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
