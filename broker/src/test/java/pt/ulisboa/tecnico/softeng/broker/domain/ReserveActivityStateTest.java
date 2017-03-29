package pt.ulisboa.tecnico.softeng.broker.domain;

import java.awt.print.Printable;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

@RunWith(JMockit.class)
public class ReserveActivityStateTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private final LocalDate begin = new LocalDate(2017, 12, 19);
	private final LocalDate end = new LocalDate(2017, 12, 21);
	private Adventure adventure;
	
	@Injectable
	private Broker broker;
	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");
		this.adventure = new Adventure(this.broker, this.begin, this.end, 21, "BK01987654321",300);
		//this.adventure.setState(State.RESERVE_ACTIVITY);
	}

	@Test
	public void success() {
		//System.out.println(adventure.getState());
		Assert.assertNotEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
		this.adventure.process();
		Assert.assertEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
		Assert.assertEquals(this.begin, this.adventure.getBegin());
		Assert.assertEquals(this.end, this.adventure.getEnd());
		Assert.assertEquals(21, this.adventure.getAge());
		Assert.assertEquals("BR01", this.adventure.getBroker().getCode());
		Assert.assertEquals(300, this.adventure.getAmount());
		//Assert.assertNotNull(this.adventure.getActivityConfirmation());
	}
}