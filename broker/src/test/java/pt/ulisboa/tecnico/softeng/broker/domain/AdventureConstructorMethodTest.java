package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;
//import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureConstructorMethodTest {
	private Broker broker;

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");
	}

	@Test
	public void success() {
		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		Adventure adventure = new Adventure(this.broker, begin, end, 20, "BK011234567", 300);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(begin, adventure.getBegin());
		Assert.assertEquals(end, adventure.getEnd());
		Assert.assertEquals(20, adventure.getAge());
		Assert.assertEquals("BK011234567", adventure.getIBAN());
		Assert.assertEquals(300, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getBankPayment());
		Assert.assertNull(adventure.getActivityBooking());
		Assert.assertNull(adventure.getRoomBooking());
	}
	
	@Test(expected = BrokerException.class)
	public void invalidDate() {
		LocalDate begin = new LocalDate(2016, 12, 21);
		LocalDate end = new LocalDate(2016, 12, 19);
		
		new Adventure(this.broker, begin, end, 20, "BK011234567", 300);
	
	}
	
	@Test(expected = BrokerException.class)
	public void invalidAge() {
		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		new Adventure(this.broker, begin, end, 1, "BK011234567", 300);
	}
	
	@Test(expected = BrokerException.class)
	public void invalidValue() {
		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		new Adventure(this.broker, begin, end, 20, "BK011234567", 0);
		
	}
	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}
