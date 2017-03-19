package pt.ulisboa.tecnico.softeng.activity.domain;

import  pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActivityMatchAgeMethodTest {
	
	@Before
	public void setUp() {
		
	}

	@Test (expected = ActivityException.class)
	public void capacityTest() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		new Activity(provider, "Bush Walking", 18, 60, -1);
	}
	@Test (expected = ActivityException.class)
	public void ageMinTest() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		new Activity(provider, "Bush Walking", 13, 60, 2);
	}
	@Test (expected = ActivityException.class)
	public void ageMaxTest() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		new Activity(provider, "Bush Walking", 19, 101, 3);
	}
	
	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
