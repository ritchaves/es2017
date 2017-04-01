package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;

@RunWith(JMockit.class)
public class ReserveActivityStateTest {
	private final LocalDate begin = new LocalDate(2017, 12, 19);
	private final LocalDate end = new LocalDate(2017, 12, 21);
	private final int age = 30;
	private Adventure adventure;
	
	@Injectable
	private Broker broker;
	
	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, 21, "BK01987654321",300);
		this.adventure.setState(State.RESERVE_ACTIVITY);
	}

	@Test
	public void readyToReserve(){
		Assert.assertEquals(Adventure.State.RESERVE_ACTIVITY, this.adventure.getState());
	}
	
	@Test
	public void confirmActivityWithRoom(@Mocked final ActivityInterface activityInterface) {
		this.adventure.getBegin();
		this.adventure.getEnd();
		this.adventure.getAge();

		ActivityInterface.reserveActivity(begin, end, age);
		
		this.adventure.process();

		Assert.assertEquals(Adventure.State.BOOK_ROOM, this.adventure.getState());
	}
}