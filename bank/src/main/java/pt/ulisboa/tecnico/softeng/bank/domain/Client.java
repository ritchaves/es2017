package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Client {
	private static int counter = 0;

	private final String name;
	private final String ID;

	public Client(Bank bank, String name){
		this.ID = Integer.toString(++Client.counter);
		System.out.println(name);
		if(name==null || name.trim().isEmpty()){
			throw new BankException("Erro BankException - Client");
		}
		else{
			System.out.println("got here");
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
