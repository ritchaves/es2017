package pt.ulisboa.tecnico.softeng.activity.domain;
import  pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;


import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ActivityOfferHasVacancyMethodTest {
	private ActivityOffer offer;

	@Before
	public void setUp() {
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 3);

		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		this.offer = new ActivityOffer(activity, begin, end);
	}

	@Test
	public void successZeroBookinks() {
		Assert.assertTrue(this.offer.hasVacancy());
	}
	

	@Test(expected= ActivityException.class)
	public void notEnoughtCapacityForReservations() {

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 3);
		ActivityOffer activityoffer = new ActivityOffer(activity, new LocalDate(2016, 12, 21), new LocalDate(2016, 12, 19));


		new Booking ( provider, activityoffer);

	}
	
	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}