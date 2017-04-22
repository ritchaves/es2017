package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Client extends Client_Base{
	private static int counter = 0;

	//private final String name;
	//private final String ID;

	public Client(Bank bank, String name) {
		checkArguments(bank, name);

		//this.ID = Integer.toString(++Client.counter);
		//this.name = name;
		setBank(bank);
		setName(name);
		setID(Integer.toString(++Client.counter));
		bank.addClient(this);
	}

	private void checkArguments(Bank bank, String name) {
		if (bank == null || name == null || name.trim().equals("")) {
			throw new BankException();
		}
	}
	
	//TIVE DE FAZER ISTO PARA CORRER TESTES
	
	public void delete() {
		
		for(Account account : getAccountSet()){
			account.delete();
		}
		setBank(null);
		deleteDomainObject();
	}

	/*
	public String getName() {
		return this.name;
	}

	public String getID() {
		return this.ID;
	}
*/
}
