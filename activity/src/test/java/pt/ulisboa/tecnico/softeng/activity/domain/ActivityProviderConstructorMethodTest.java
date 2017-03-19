package pt.ulisboa.tecnico.softeng.activity.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.exception.ActivityException;

public class ActivityProviderConstructorMethodTest {

	@Before
	public void setUp() {

	}
	
	@Test
	public void success() {
		ActivityProvider provider = new ActivityProvider("XtremX", "Adventure++");

		Assert.assertEquals("Adventure++", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(1, ActivityProvider.providers.size());
		Assert.assertEquals(0, provider.getNumberOfActivities());
	}
	
	@Test (expected = ActivityException.class)
	public void moreCaractersCode() {
		ActivityProvider provider = new ActivityProvider("XtremXXX", "Adventure++");

		Assert.assertEquals("Adventure++", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@Test (expected = ActivityException.class)
	public void lessCaractersCode() {
		ActivityProvider provider = new ActivityProvider("X", "Adventure++");

		Assert.assertEquals("Adventure++", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}

	@Test (expected = ActivityException.class)
	public void ifNameNull() {
		ActivityProvider provider = new ActivityProvider("XtremX", null);

		Assert.assertEquals(null, provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@Test (expected = ActivityException.class)
	public void ifNameEmpty() {
		ActivityProvider provider = new ActivityProvider("XtremX", "");

		Assert.assertEquals("", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@Test (expected = ActivityException.class)
	public void ifNameSpace() {
		ActivityProvider provider = new ActivityProvider("XtremX", "       ");

		Assert.assertEquals("       ", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@Test (expected = ActivityException.class)
	public void ifCodenull() {
		ActivityProvider provider = new ActivityProvider(null , "Adventure++");

		Assert.assertEquals("Adventure++", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@Test (expected = ActivityException.class)
	public void ifCodeSpace() {
		ActivityProvider provider = new ActivityProvider("       ", "Adventure++");

		Assert.assertEquals("Adventure++", provider.getName());
		Assert.assertTrue(provider.getCode().length() == ActivityProvider.CODE_SIZE);
		Assert.assertEquals(0, provider.getNumberOfActivities());
		Assert.assertEquals(1, ActivityProvider.providers.size());
	}
	
	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
