package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation.Type;
import pt.ist.fenixframework.FenixFramework;

public class BankPersistenceTest {
	private static final String BANK_CODE = "BK01";
	private static final String BANK_NAME = "Money";
	private static final String CLIENT_NAME = "António";
	
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		Bank bank = new Bank(BANK_NAME, BANK_CODE);
		Client client = new Client(bank, CLIENT_NAME);
		Account account = new Account(bank, client);
		new Operation(Type.DEPOSIT, account, 1000);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		Bank bank = Bank.getBankByCode(BANK_CODE);
		
		assertEquals(BANK_NAME, bank.getName());
		
		Operation operation = null;
		for(Operation op : bank.getOperationSet()){
			operation = op;
		}
		
		// Test percistency of Bank
		
		Assert.assertEquals(operation, bank.getOperation(operation.getReference()));
		//Assert.assertEquals(account, bank.getAccount(account.getIBAN()));		SAME HERE DAVID
		//Assert.assertTrue(bank.hasClient(client));		SAME HERE DAVID
		
		// Test percistency of Operation
		
		Assert.assertNotNull(operation);
		Assert.assertTrue(operation.getReference().startsWith(bank.getCode()));
		Assert.assertTrue(operation.getReference().length() > Bank.CODE_SIZE);
		Assert.assertEquals(Type.DEPOSIT, operation.getType());
//		Assert.assertEquals(account, operation.getAccount());  DAVID QUANDO FIZERES O ACCOUNT COM NOME "ACCOUNT" TIRA ISTO DE COMENTARIO"
		Assert.assertEquals(1000, operation.getValue());
		Assert.assertTrue(operation.getTime() != null);
		Assert.assertEquals(bank, operation.getBank());
		
	}
	
	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			bank.delete();
		}
	}

}
