package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankCancelPayment {
	private Bank bank;
	private Account account;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "M");
		this.account = new Account(this.bank, client);
		this.account.deposit(100);
	}

//	public static String cancelPayment(String reference) {
//		for (Bank bank : Bank.banks) {
//			if(bank.getOperation(reference) != null) {
//				Operation canceledOperation = bank.getOperation(reference);
//				Account creditAcc = canceledOperation.getAccount();
//				return creditAcc.deposit(canceledOperation.getValue());
//			}
//		}
//		throw new BankException();
//	}
	
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
