package pt.ulisboa.tecnico.softeng.bank.services.remote.exception;

public class RemoteAccessException extends RuntimeException {
	public RemoteAccessException() {
		super();
	}

	public RemoteAccessException(String message) {
		super(message);
	}
}
