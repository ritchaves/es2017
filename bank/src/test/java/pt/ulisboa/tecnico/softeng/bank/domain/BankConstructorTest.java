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
	public void nullname() {
		Bank bank2 = new Bank(null, "BK02");
	}

	@Test(expected = BankException.class)	
	public void nullcode() {
		Bank bank2 = new Bank("R", null);
	}
	
	@Test(expected = BankException.class)	
	public void emptystring() {
		Bank bank2 = new Bank("", "BK03");
	}
	
	@Test(expected = BankException.class)	
	public void emptycode() {
		Bank bank2 = new Bank("M", "");
	}
	
	@Test(expected = BankException.class)
	public void codeverification(){
		Bank bank4 = new Bank("M", "BK011");
	}
	
	@Test(expected = BankException.class)
	public void codeexists(){
		Bank bank5 = new Bank("M", "BK03");
		Bank bank6 = new Bank("O", "BK03");
	}
	
	@After
	public void tearDown() {
		Bank.banks.clear();
	}
}
