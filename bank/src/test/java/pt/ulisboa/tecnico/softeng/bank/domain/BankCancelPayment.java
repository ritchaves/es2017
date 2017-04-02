package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.domain.Operation.Type;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankCancelPayment {
	private Bank bank;
	private Account account;
	private Operation operation;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "M");
		this.account = new Account(this.bank, client);
		this.account.deposit(100);
		this.operation = new Operation(Type.WITHDRAW, this.account, 20);
	}

	@Test
	public void success() {
		Bank.cancelPayment(this.operation.getReference());
		Assert.assertEquals(120, this.account.getBalance());	
	}
	
	@Test
	public void notSucess() {
		Bank.cancelPayment(this.operation.getReference());
		Assert.assertNotEquals(10, this.account.getBalance());
	}
	
	@Test
	public void notSucess2() {
		Bank.cancelPayment(this.operation.getReference());
		Assert.assertNotEquals(140, this.account.getBalance());
	}
	
	@Test(expected = BankException.class)
	public void referenceNull() {
		Bank.cancelPayment(null);
	}
	
	@Test(expected = BankException.class)
	public void referenceEmpty() {
		Bank.cancelPayment(" ");
	}
	
	@Test(expected = BankException.class)
	public void referenceDoesNotExist() {
		Bank.cancelPayment("XPTO");
	}
	
	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
