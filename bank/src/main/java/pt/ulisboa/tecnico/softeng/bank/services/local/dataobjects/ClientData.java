package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.bank.domain.*;

public class ClientData {
	public static enum CopyDepth {
		SHALLOW, ACCOUNT
	};
	
	private String name;
	private String ID;
	private List<AccountData> accounts = new ArrayList<AccountData>();
	
	public ClientData() {}
	
	public ClientData(Client client, CopyDepth depth) {
		this.name = client.getName();
		this.ID = client.getID();
		
		switch(depth) {
		case ACCOUNT:
			for (Account account : client.getAccountSet()) {
				AccountData accountData = new AccountData(account, pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.AccountData.CopyDepth.SHALLOW);
				accounts.add(accountData);
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
	
	public String getID() {
		return this.ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public List<AccountData> getAccounts() {
		return this.accounts;
	}
	
	public void setAcoounts(List<AccountData> accounts) {
		this.accounts = accounts;
	}
}
