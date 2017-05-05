package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.bank.domain.*;

public class AccountData {
	public static enum CopyDepth {
		SHALLOW, OPERATION
	};
	
	private String IBAN;
	private int balance;
	private List<BankOperationData> operations = new ArrayList<BankOperationData>();
	
	public AccountData() {}
	
	public AccountData(Account account, CopyDepth depth) {
		this.IBAN = account.getIBAN();
		this.balance = account.getBalance();
		
		switch(depth) {
		case OPERATION:
			for (Operation operation : account.getOperationSet()) {
				BankOperationData bankOperationData = new BankOperationData(operation);
				this.operations.add(bankOperationData);
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}
	}
	
	public String getIBAN() {
		return this.IBAN;
	}
	
	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public List<BankOperationData> getOperations() {
		return this.operations;
	}
	
	public void setOperation(List<BankOperationData> operations) {
		this.operations = operations;
	}
}
