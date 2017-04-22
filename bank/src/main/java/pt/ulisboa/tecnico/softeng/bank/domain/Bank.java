package pt.ulisboa.tecnico.softeng.bank.domain;
/*
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;*/

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Bank extends Bank_Base {
	public static final int CODE_SIZE = 4;

	//private final String name;
	//private final String code;
	//private final Set<Account> accounts = new HashSet<>();
	//private final Set<Client> clients = new HashSet<>();
	//private final List<Operation> log = new ArrayList<>();

	public Bank(String name, String code) {
		checkArguments(name, code);

		setName(name);
		setCode(code);
		
		FenixFramework.getDomainRoot().addBank(this);
	}

	public void delete() {
		setRoot(null);

		deleteDomainObject();
	}

	private void checkArguments(String name, String code) {
		if (name == null || code == null || name.trim().equals("") || code.trim().equals("")) {
			throw new BankException();
		}

		if (code.length() != Bank.CODE_SIZE) {
			throw new BankException();
		}

		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getCode().equals(code)) {
				throw new BankException();
			}
		}
	}
/*
	String getName() {
		return this.name;
	}

	String getCode() {
		return this.code;
	}
*/
	public int getNumberOfAccounts() {
		return getAccountSet().size();
	}

	public int getNumberOfClients() {
		return getClientSet().size();
	}

	public void addAccount(Account account) {
		getAccountSet().add(account);
	}

	boolean hasClient(Client client) {
		return getClientSet().contains(client);
	}

	public void addClient(Client client) {
		getClientSet().add(client);
	}

	public void addOperation(Operation operation) {
		getOperationSet().add(operation);
	}

	public Account getAccount(String IBAN) {
		if (IBAN == null || IBAN.trim().equals("")) {
			throw new BankException();
		}
		
		for (Bank bank:FenixFramework.getDomainRoot().getBankSet()){
			if(!bank.getAccount(IBAN).equals(null))
				return bank.getAccount(IBAN);
		}

		return null;
	}

	public Operation getOperation(String reference) {
		for (Operation operation : getOperationSet()) {
			if (operation.getReference().equals(reference)) {
				return operation;
			}
		}
		return null;
	}

	public static Bank getBankByCode(String code) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getCode().equals(code)) {
				return bank;
			}
		}
		return null;
	}

	public static Operation getOperationByReference(String reference) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			Operation operation = bank.getOperation(reference);
			if (operation != null) {
				return operation;
			}
		}
		return null;
	}
	
	public static String processPayment(String IBAN, int amount) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getAccount(IBAN) != null) {
				return bank.getAccount(IBAN).withdraw(amount);
			}
		}
		throw new BankException();
	}

	public static String cancelPayment(String paymentConfirmation) {
		Operation operation = getOperationByReference(paymentConfirmation);
		if (operation != null) {
			return operation.revert();
		}
		throw new BankException();
	}

	public static BankOperationData getOperationData(String reference) {
		Operation operation = getOperationByReference(reference);
		if (operation != null) {
			return new BankOperationData(operation);
		}
		throw new BankException();
	}

}
