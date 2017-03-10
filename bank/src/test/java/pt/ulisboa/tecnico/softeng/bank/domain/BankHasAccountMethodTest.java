package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankHasAccountMethodTest {
	Bank bank;
	Client client;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		this.client = new Client(this.bank, "António");
	}

	@Test
	public void success() {
		Account account = new Account(this.bank, this.client);

		Account result = this.bank.getAccount(account.getIBAN());
		Assert.assertEquals(account, result);
	}
	
	@Test(expected = BankException.class)
	public void nulled(){
		this.bank.getAccount(null);
	}
	
	@Test(expected = BankException.class)
	public void empty(){
		this.bank.getAccount("  ");
	}
	
	@Test(expected = BankException.class)
	public void invalidIBAN(){
		this.bank.getAccount("123172401927401279401209972401927401927401EHIHIJ HFAKFH");
	}
	
	@Test(expected = BankException.class)
	public void validIBANfromAnotherBank(){
		Bank bank2 = new Bank("Money", "BK02");
		Client client2 = new Client(bank2, "Client2");
		Account account2 = new Account(bank2, client2);
		this.bank.getAccount(account2.getIBAN());
	}
	
	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
