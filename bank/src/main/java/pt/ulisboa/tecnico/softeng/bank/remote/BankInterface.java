package pt.ulisboa.tecnico.softeng.bank.remote;

import pt.ulisboa.tecnico.softeng.bank.services.remote.dataobjects.BankOperationData;

public class BankInterface {
	public static String processPayment(String IBAN, int amount) {
		// TODO: implement in the final version as a rest invocation
		return null;
	}

	public static String cancelPayment(String reference) {
		// TODO: implement in the final version as a rest invocation
		return null;
	}

	public static BankOperationData getOperationData(String reference) {
		// TODO: implement in the final version as a rest invocation
		return null;
	}
}
