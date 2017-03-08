package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Client {
	private static int counter = 0;

	private final String name;
	private final String ID;

	public Client(Bank bank, String name) {
		this.ID = Integer.toString(++Client.counter);
		
		if(name=="" || name==null){
			throw new BankException();
		}
		else{
		this.name = name;
		}

		bank.addClient(this);
	}

	public String getName() {
		return this.name;
	}

	public String getID() {
		return this.ID;
	}

}
