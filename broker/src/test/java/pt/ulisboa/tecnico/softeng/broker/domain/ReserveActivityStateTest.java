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
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;

@RunWith(JMockit.class)
public class ReserveActivityStateTest {
	private final LocalDate begin = new LocalDate(2017, 12, 19);
	private final LocalDate end = new LocalDate(2017, 12, 21);
	private static final String IBAN = "BK01987654321";
	private final int age = 21;
	private final int AMOUNT = 300;
	private Adventure adventure;
	private Adventure adventure2;
	
	@Injectable
	private Broker broker;
	
	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, this.age, IBAN, this.AMOUNT);
		this.adventure2 = new Adventure(this.broker, this.begin, this.begin, this.age, IBAN, this.AMOUNT);
		this.adventure.setState(State.RESERVE_ACTIVITY);
		this.adventure2.setState(State.RESERVE_ACTIVITY);
	}

	@Test
	public void readyToReserve(){
		Assert.assertEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
	}
	
	@Test
	public void confirmActivity(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		this.adventure.getAge();

		ActivityInterface.reserveActivity(begin, end, age);
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.BOOK_ROOM, this.adventure.getState());
	}
	
	@Test
	public void confirmSameDayActivity(@Mocked final ActivityInterface activityInterface) {
		this.adventure2.getBegin();
		this.adventure2.getEnd();
		this.adventure2.getAge();

		
		ActivityInterface.reserveActivity(begin, begin, age);
		
		this.adventure2.process();

		Assert.assertEquals(Adventure.State.CONFIRMED, this.adventure2.getState());
	}
	
	@Test
	public void reserveActivityException(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getEnd();
		this.adventure.getBegin();
		this.adventure.getAge();
		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new ActivityException();
			}
		};
		
		this.adventure.process();
		Assert.assertEquals(Adventure.State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void reserve5Exception(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		this.adventure.getAge();
		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();

			}
		};
		
		for (int i = 0; i < 5; i++) this.adventure.process();
		
		Assert.assertEquals(Adventure.State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void activityLessThan5Exception(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		this.adventure.getAge();
		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
			}
		};
		
		this.adventure.process();
		Assert.assertEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
	}
	
	@Test
	public void activityMoreThan5Exception(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		this.adventure.getAge();
		
		new Expectations() {
			{
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
				ActivityInterface.reserveActivity(begin, end, age);
				this.result = new RemoteAccessException();
			}
		};
		for (int i = 0; i < 6; i++)this.adventure.process();
		Assert.assertEquals(Adventure.State.CANCELLED, this.adventure.getState());
	}
}