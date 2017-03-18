package pt.ulisboa.tecnico.softeng.activity.domain;
import  pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;

import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityMatchAgeMethodTest {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private Activity activity;

	@Before
	public void setUp() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		this.activity = new Activity(provider, "Bush Walking", 18, 80, 3);
	}

	@Test
	public void successIn() {
		Assert.assertTrue(this.activity.matchAge(50));
	}

	@Test(expected= ActivityException.class)
	
	public void invalidage() {
		
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		
		Activity activity = new Activity(provider, "Bush Walking", 18, 100, 3);
		
		Assert.assertTrue(this.activity.matchAge(17));
		
		Assert.assertTrue(this.activity.matchAge(101));
	}

	@Test(expected= ActivityException.class)

	public void incompatibleage() {
		
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");

		Activity activity = new Activity(provider, "Bush Walking", 60, 40, 3);

		Assert.assertTrue(activity.getCode().startsWith(this.provider.getCode()));

		Assert.assertTrue(activity.getCode().length() > ActivityProvider.CODE_SIZE);
		Assert.assertEquals(60, activity.getMinAge());
		
		Assert.assertEquals(40, activity.getMaxAge());
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
