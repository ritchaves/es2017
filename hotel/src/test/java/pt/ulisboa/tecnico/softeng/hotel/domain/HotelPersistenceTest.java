package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class HotelPersistenceTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final String ROOM_NUMBER = "01";
	
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	
	private Hotel hotel;

	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		this.hotel = new Hotel(HOTEL_CODE, HOTEL_NAME);
		Booking booking = new Booking(this.hotel, this.arrival, this.departure);
		Room room = new Room(this.hotel, ROOM_NUMBER, Type.DOUBLE);
	}
	
	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		for (Hotel hotel : FenixFramework.getDomainRoot().getHotelSet()) {
			this.hotel = hotel;
		}
		
		//test Hotel
		assertEquals(1, FenixFramework.getDomainRoot().getHotelSet().size());
		assertEquals(HOTEL_NAME, hotel.getName());
		assertEquals(HOTEL_CODE, hotel.getCode());
		
		//test Booking 
		assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		assertEquals(this.arrival, booking.getArrival());
		assertEquals(this.departure, booking.getDeparture());
		
		//test Room
		Assert.assertEquals(this.hotel, room.getHotel());
		Assert.assertEquals(ROOM_NUMBER, room.getNumber());
		Assert.assertEquals(Type.DOUBLE, room.getType());
		Assert.assertEquals(1, this.hotel.getNumberOfRooms());
		
		
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Hotel hotel : FenixFramework.getDomainRoot().getHotelSet()) {
			hotel.delete();
		}
	}

}
