package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Account extends Account_Base{
	private static int counter = 0;

	//private final Bank bank;
	//private final String IBAN;
	//private final Client client;
	//private int balance;

	public Account(Bank bank, Client client) {
		checkArguments(bank, client);

		setBank(bank);
		//this.IBAN = bank.getCode() + Integer.toString(++Account.counter);
		setClient(client);
		//this.balance = 0;
		setIBAN(getBank().getCode() + Integer.toString(++Account.counter));
		setBalance(0);
		getBank().addAccount(this);
	}

	private void checkArguments(Bank bank, Client client) {
		if (bank == null || client == null) {
			throw new BankException();
		}

		if (!getBank().hasClient(client)) {
			throw new BankException();
		}

	}
/*
	Bank getBank() {
		return this.bank;
	}

	/*public String getIBAN() {
		return this.IBAN;
	}

	public Client getClient() {
		return this.client;
	}

	/*public int getBalance() {
		return this.balance;
	}
*/
	
	 public String deposit(int amount) {
	 
		if (amount <= 0) {
			throw new BankException();
		}

		setBalance(getBalance() + amount);

		Operation operation = new Operation(Operation.Type.DEPOSIT, this, amount);
		return operation.getReference();
	}

	public String withdraw(int amount) {
		if (amount <= 0 || amount > getBalance()) {
			throw new BankException();
		}

		setBalance(getBalance() - amount);

		return new Operation(Operation.Type.WITHDRAW, this, amount).getReference();
	}

}
