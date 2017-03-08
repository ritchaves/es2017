package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

/*Teste o construtor da classe Client 
 * tal que os argumentos 
	 * não possam ser null, done
	 * nem a string vazia, done
	 * nem brancos */

public class ClientContructorMethodTest {
	Bank bank;
	Client client;
	
	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");	}

	@Test
	public void success() {
		Client client = new Client(this.bank, "António");

		Assert.assertNotEquals("", client.getName()); //not empty
		Assert.assertEquals("António", client.getName());//is equal to what we expect
		Assert.assertTrue(client.getID().length() >= 1);//sizeID positive
		Assert.assertTrue(this.bank.hasClient(client));//is in bank
	}
	
	@Test(expected = BankException.class)
	public void empty(){
		Client client2 = new Client(this.bank, "");
	}
	
	@Test(expected = BankException.class)
	public void nulled(){
		Client client3 = new Client(this.bank, null);
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
