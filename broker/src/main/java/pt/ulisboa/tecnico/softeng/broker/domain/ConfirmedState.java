package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.activity.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.dataobjects.BankOperationData;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class ConfirmedState extends AdventureState {
	private static Logger logger = LoggerFactory.getLogger(CancelledState.class);
	
	@Override
	public State getState() {
		return State.CONFIRMED;
	}

	@Override
	public void process(Adventure adventure) {
		logger.debug("process");
		BankOperationData operation;
		try {
			operation = BankInterface.getOperationData(adventure.getPaymentConfirmation());
		} catch (BankException be) {
			this.incNumOfRemoteErrors();
			if (this.numOfRemoteErrors == 5) {
				adventure.setState(State.UNDO);
			
			}
			return;
			
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();
			if (this.numOfRemoteErrors == 20) {
				adventure.setState(State.UNDO);
			
			}
		
			return;
		}
		this.resetNumOfRemoteErrors();
		
		ActivityReservationData reservation;
		try {
			reservation = ActivityInterface.getActivityReservationData(adventure.getActivityConfirmation());
		} catch (ActivityException ae) {
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();
			if(this.numOfRemoteErrors == 20){
				adventure.setState(State.UNDO);
			}
			return;
			
		}
		this.resetNumOfRemoteErrors();
		
		if (adventure.getRoomConfirmation() != null) {
			RoomBookingData booking;
			try {
				booking = HotelInterface.getRoomBookingData(adventure.getRoomConfirmation());
			} catch (HotelException he) {
				adventure.setState(State.UNDO);
				return;
			} catch (RemoteAccessException rae) {
				this.incNumOfRemoteErrors();
				if (this.numOfRemoteErrors == 20) {
					adventure.setState(State.UNDO);
					
				}
				return;
			}
			this.resetNumOfRemoteErrors();
		}



	}

}
