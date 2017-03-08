package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {

	@Before
	public void setUp() {

	}

	@Test
	public void success() {
		Hotel hotel = new Hotel("XPTO123", "Londres");

		Assert.assertEquals("Londres", hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}
	
	@Test (expected = HotelException.class)
	public void failure() {
		Hotel hotel = new Hotel("XPTO1233", "Londres");

		Assert.assertEquals("Londres", hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
