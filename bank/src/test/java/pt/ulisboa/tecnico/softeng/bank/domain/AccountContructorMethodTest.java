package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class AccountContructorMethodTest {
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

		Assert.assertEquals(this.bank, account.getBank());
		Assert.assertTrue(account.getIBAN().startsWith(this.bank.getCode()));
		Assert.assertEquals(this.client, account.getClient());
		Assert.assertEquals(0, account.getBalance());
		Assert.assertEquals(1, this.bank.getNumberOfAccounts());
		Assert.assertTrue(this.bank.hasClient(this.client));
	}
	
	@Test(expected = BankException.class)
	public void clientNull(){
		new Account(this.bank, null);
	}
	
	@Test(expected = BankException.class)
	public void bankNull(){
		new Account(null, this.client);
	}
	
	@Test(expected = BankException.class)
	public void clientDoesNotBelong(){
		//Create a client on 2nd bank and tried to create an account in the 1st bank
		Bank bank2 = new Bank("Bank2", "B2");
		Client client2 = new Client(bank2, "Cliente2");
		new Account(this.bank, client2);
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
