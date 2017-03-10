package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BankConstructorTest {

	@Before
	public void setUp() {

	}

	@Test
	public void success() {
		Bank bank = new Bank("Money", "BK01");
		
		Assert.assertEquals("Money", bank.getName());
		Assert.assertEquals("BK01", bank.getCode());
		Assert.assertEquals(1, Bank.banks.size());
		Assert.assertEquals(0, bank.getNumberOfAccounts());
		Assert.assertEquals(0, bank.getNumberOfClients());
	}	
	
	@Test(expected = BankException.class)	
	public void nullName() {
		new Bank(null, "BK02");
	}

	@Test(expected = BankException.class)	
	public void nullCode() {
		new Bank("R", null);
	}
	
	@Test(expected = BankException.class)	
	public void emptyName() {
		new Bank("", "BK03");
	}
	
	@Test(expected = BankException.class)	
	public void emptyCode() {
		new Bank("M", "");
	}
	
	@Test(expected = BankException.class)
	public void codeVerification(){
		new Bank("M", "BK011");
	}
	
	@Test(expected = BankException.class)
	public void codeExists(){
		new Bank("M", "BK03");
		new Bank("O", "BK03");
	}
	
	@After
	public void tearDown() {
		Bank.banks.clear();
	}
}
