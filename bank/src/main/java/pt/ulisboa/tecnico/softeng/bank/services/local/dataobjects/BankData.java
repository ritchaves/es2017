package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.bank.domain.*;

public class BankData {
	public static enum CopyDepth {
		SHALLOW, CLIENT, ACCOUNT, OPERATION
	};
	
	private String name;
	private String code;
	private List<ClientData> clients = new ArrayList<ClientData>();
	private List<AccountData> accounts = new ArrayList<AccountData>();
	private List<BankOperationData> operations = new ArrayList<BankOperationData>();
	
	public BankData() {}
	
	public BankData(Bank bank, CopyDepth depth) {
		this.name = bank.getName();
		this.code = bank.getCode();
		
		switch (depth) {
		case CLIENT:
			for (Client client : bank.getClientSet()) {
				ClientData clientData = new ClientData(client, pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.ClientData.CopyDepth.SHALLOW);
				clients.add(clientData);
			}
			break;
		case ACCOUNT:
			for (Account account : bank.getAccountSet()) {
				AccountData accountData = new AccountData(account, pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.ClientData.CopyDepth.SHALLOW.SHALLOW);
				accounts.add(accountData);
			}
			break;
		case OPERATION:
			for (Operation operation : bank.getOperationSet()) {
				BankOperationData bankOperationData = new BankOperationData(operation);
				operations.add(bankOperationData);
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<ClientData> getClients() {
		return this.clients;
	}
	
	public void setClients(List<ClientData> clients) {
		this.clients = clients;
	}
	
	public List<AccountData> getAccounts() {
		return this.accounts;
	}
	
	public void setAccounts(List<AccountData> accounts) {
		this.accounts = accounts;
	}
	
	public void removeAccount(AccountData accountData) {
		this.accounts.remove(accountData);
	}
	
	public int getAccountsSize() {
		return this.accounts.size();
	}
	
	public List<BankOperationData> getOperations() {
		return this.operations;
	}
	
	public void setOperations(List<BankOperationData> operations) {
		this.operations = operations;
	}
}
