package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;

@RunWith(JMockit.class)
public class UndoStateTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, 20, IBAN, 300);
		this.adventure.setState(State.UNDO);
	}

	@Test
	public void cancelledPayment(@Mocked final BankInterface bankInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
		
		new StrictExpectations() {
			{
				BankInterface.cancelPayment(this.anyString);
				this.result = PAYMENT_CANCELLATION;
				this.times = 1;
			}
		};
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void cancelledActivity(@Mocked final ActivityInterface activityInterface) {
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);
		this.adventure.setRoomCancellation(ROOM_CANCELLATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CANCELLATION);
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		
		new StrictExpectations() {
			{
				ActivityInterface.cancelReservation(this.anyString);
				this.result = ACTIVITY_CANCELLATION;
				this.times = 1;
			}
		};
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
	
	@Test
	public void cancelledRoom(@Mocked final HotelInterface hotelInterface) {
		this.adventure.setPaymentConfirmation(PAYMENT_CONFIRMATION);
		this.adventure.setPaymentConfirmation(PAYMENT_CANCELLATION);
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		this.adventure.setActivityCancellation(ACTIVITY_CANCELLATION);
		this.adventure.setRoomConfirmation(ROOM_CONFIRMATION);

		new StrictExpectations() {
			{
				HotelInterface.cancelBooking(this.anyString);
				this.result = ROOM_CANCELLATION;
				this.times = 1;
			}
		};
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

}
