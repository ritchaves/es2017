package pt.ulisboa.tecnico.softeng.activity.domain;
import  pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;


import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityMatchAgeMethodTest {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
		new Activity(provider, "Bush Walking", 13, 60, -1);
	}
	@Test (expected = ActivityException.class)
	public void ageMaxTest() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		new Activity(provider, "Bush Walking", 19, 101, -1);
	}
	
	
	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
