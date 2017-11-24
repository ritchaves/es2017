package pt.ulisboa.tecnico.softeng.activity.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;

import pt.ulisboa.tecnico.softeng.activity.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;

import pt.ist.fenixframework.FenixFramework;

public class ActivityPersistenceTest {
	private static final String PROVIDER_CODE = "XtremX";
	private static final String PROVIDER_NAME = "Adventure++";
	
	private static final int MIN_AGE = 25;
	private static final int MAX_AGE = 50;
	private static final int CAPACITY = 30;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	
	private ActivityProvider provider;
	private Activity activity;
	private Booking booking;
	
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		ActivityProvider provider = new ActivityProvider(PROVIDER_CODE, PROVIDER_NAME);
		this.activity = new Activity(provider, PROVIDER_NAME, MIN_AGE, MAX_AGE, CAPACITY);
		ActivityOffer offer = new ActivityOffer(activity, this.begin, this.end);
		ActivityOffer offer2 = new ActivityOffer(activity, this.begin.minusDays(4), this.begin.plusDays(2));
		this.booking = new Booking(provider, offer);
		new Booking(provider, offer2);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		for (ActivityProvider prov : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			this.provider = prov;
		}
		
		//test ActivityProvider
		assertEquals(1, FenixFramework.getDomainRoot().getActivityProviderSet().size());
		assertEquals(PROVIDER_NAME, provider.getName());
		assertEquals(PROVIDER_CODE, provider.getCode());
		
		//test Activity
		assertEquals(2, activity.getActivityOfferSet().size());
		
		//test ActivityOffer
		for (ActivityOffer actoff : activity.getActivityOfferSet()) {
			assertNotNull(actoff);
			assertEquals(1, actoff.getNumberOfBookings());
		}
		
		// test ActivityReservationData
		ActivityReservationData activityReservationData = ActivityProvider.getActivityReservationData(booking.getReference());
		assertEquals(activityReservationData.getReference(), booking.getReference());
		assertEquals(activityReservationData.getName(), provider.getName());
		assertEquals(activityReservationData.getCode(), provider.getCode());
		assertEquals(activityReservationData.getBegin(), this.begin);
		assertEquals(activityReservationData.getEnd(), this.end);
		activityReservationData.setCancellationDate(this.end.minusDays(2));
		assertNotNull(activityReservationData.getCancellationDate());

	}
	
	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			provider.delete();
		}
	}

}
