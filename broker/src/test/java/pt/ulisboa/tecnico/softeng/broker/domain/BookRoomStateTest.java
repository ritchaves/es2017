package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.StrictExpectations;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;


@RunWith(JMockit.class)
public class BookRoomStateTest {
	private static final String IBAN = "BK01987654321";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, 20, IBAN, 300);
		this.adventure.setState(State.BOOK_ROOM);
	}
	@Test
	public void bookRoom() {

		Assert.assertEquals(Adventure.State.BOOK_ROOM, this.adventure.getState());

	}
	@Test
	public void confirmRoom(@Mocked final HotelInterface hotelInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		
		HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
		this.adventure.process();
		Assert.assertEquals(Adventure.State.CONFIRMED, this.adventure.getState());	
	}
	
	@Test
	public void roomHotelException(@Mocked final HotelInterface hotelInterface) {
		this.adventure.getEnd();
		this.adventure.getBegin();
		
		new StrictExpectations() {
			{
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new HotelException();
			}
		};
		
		this.adventure.process();
		Assert.assertEquals(Adventure.State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void room10Exception(@Mocked final HotelInterface hotelInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		
		new StrictExpectations() {
			{
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();

			}
		};
		for (int i = 0; i < 10; i ++)
			this.adventure.process();
		Assert.assertEquals(Adventure.State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void roomLessThan10Exception(@Mocked final HotelInterface hotelInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		
		new StrictExpectations() {
			{
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
			}
		};
		this.adventure.process();
		Assert.assertEquals(Adventure.State.BOOK_ROOM, this.adventure.getState());
	}
	
	@Test
	public void roomMoreThan10Exception(@Mocked final HotelInterface hotelInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		
		new Expectations() {
			{
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
				HotelInterface.reserveRoom(Room.Type.SINGLE, begin, end);
				this.result = new RemoteAccessException();
			}
		};
		for (int i = 0; i < 11; i ++)
			this.adventure.process();
		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

}