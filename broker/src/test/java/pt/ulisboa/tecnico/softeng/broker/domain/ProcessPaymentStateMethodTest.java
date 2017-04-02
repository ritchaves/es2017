
package pt.ulisboa.tecnico.softeng.broker.domain;

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
	
@RunWith(JMockit.class)
public class ProcessPaymentStateMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, 20, IBAN, 300);
		this.adventure.setState(State.PROCESS_PAYMENT);
	}

	@Test
	public void readyToReserve(@Mocked final BankInterface bankInterface) {
		
		BankInterface.processPayment(IBAN , AMOUNT);
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
	}

	@Test
	public void processPaymentFirstBankException(@Mocked final BankInterface bankInterface) {
		this.adventure.getIBAN();
		this.adventure.getAmount();

		new Expectations() {
			{
				BankInterface.processPayment(IBAN , AMOUNT);
				this.result = new BankException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void processPaymentFirstRemoteAccessException(@Mocked final BankInterface bankInterface) {
		this.adventure.getIBAN();
		this.adventure.getAmount();

		new Expectations() {
			{
				BankInterface.processPayment(IBAN , AMOUNT);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(Adventure.State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void processPaymentNumberErrorsEquals3Exception(@Mocked final BankInterface bankInterface) {
		this.adventure.getIBAN();
		this.adventure.getAmount();

		new Expectations() {
			{	
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();	
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}


	@Test
	public void processPaymentNumberErrorsBelow3Exception(@Mocked final BankInterface bankInterface) {
		this.adventure.getIBAN();
		this.adventure.getAmount();

		new Expectations() {
			{	
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();	
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(Adventure.State.PROCESS_PAYMENT, this.adventure.getState());
	}
	
	@Test
	public void processPaymentNumberErrorsAbove3Exception(@Mocked final BankInterface bankInterface) {
		this.adventure.getIBAN();
		this.adventure.getAmount();

		new Expectations() {
			{	
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();	
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
					BankInterface.processPayment(IBAN , AMOUNT);
					this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
}

