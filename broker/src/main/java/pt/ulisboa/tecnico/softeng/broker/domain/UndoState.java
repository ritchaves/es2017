 package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;

public class UndoState extends AdventureState {
	private static Logger logger = LoggerFactory.getLogger(UndoState.class);
	
	@Override
	public State getState() {
		return State.UNDO;
	}

	@Override
	public void process(Adventure adventure) {
		logger.debug("process ID:{}, state:{} ", adventure.getID(), adventure.getOldState().name());
		
		this.getNumOfRemoteErrors();
		try {
			adventure.setPaymentCancellation(BankInterface.cancelPayment(adventure.getPaymentCancellation()));
		} catch(BankException | RemoteAccessException e) {
			return;
		}
	}
}