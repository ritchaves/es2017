package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankGetOperationDataTest {
	private Bank bank;
	private Account account;
	private String ref1; 
	private String ref2;
	
/*BankOperationData bod = new BankOperationData();
		for (Bank bank : Bank.banks){
			if(bank.getOperation(reference)!=null){
				bod.setIban(bank.getOperation(reference).getAccount().getIBAN());
				bod.setReference(reference);
				bod.setValue(bank.getOperation(reference).getValue());
				bod.setTime(bank.getOperation(reference).getTime());
				bod.setType(bank.getOperation(reference).getType().toString());
				return bod;*/
	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "M");
		this.account = new Account(this.bank, client);
		this.ref1=this.account.deposit(200);
		this.ref2=this.account.withdraw(100);
	}
	
	@After
	public void tearDown(){
		Bank.banks.clear();
	}
	
	@Test
	public void successDepositData(){
		BankOperationData bod = Bank.getOperationData(ref1);
		Assert.assertEquals(ref1, bod.getReference());
		Assert.assertEquals(this.account.getIBAN(), bod.getIban());
		Assert.assertEquals(this.bank.getOperation(ref1).getTime(), bod.getTime());
		Assert.assertEquals(200, bod.getValue());
	}
	
	@Test
	public void successWithdrawData(){
		BankOperationData bod2 = Bank.getOperationData(ref2);
		Assert.assertEquals(ref2, bod2.getReference());
		Assert.assertEquals(this.account.getIBAN(), bod2.getIban());
		Assert.assertEquals(this.bank.getOperation(ref2).getTime(), bod2.getTime());
		Assert.assertEquals(100, bod2.getValue());
	}
	

	@Test(expected = BankException.class)
	public void referenceNull() {
		Bank.getOperationData(null);
	}
	
	
	@Test(expected = BankException.class)
	public void referenceEmpty() {
		Bank.cancelPayment("  ");
	}
	
	
	@Test(expected = BankException.class)
	public void referenceDoesNotExist() {
		Bank.cancelPayment("XPTO");
	}
	
}
