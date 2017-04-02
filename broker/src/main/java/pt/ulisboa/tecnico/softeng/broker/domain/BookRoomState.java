 package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookRoomState extends AdventureState {
	private static Logger logger = LoggerFactory.getLogger(ReserveActivityState.class);
	private String roomConfirmation;
	
	@Override
	public State getState() {
		return State.BOOK_ROOM;
	}

	@Override
	public void process(Adventure adventure) {
		logger.debug("process ID:{}, state:{} ", adventure.getID(), adventure.getState().name());
		
		this.getNumOfRemoteErrors();

		try {
			adventure.setRoomConfirmation(HotelInterface.reserveRoom(Room.Type.SINGLE, adventure.getBegin(), adventure.getEnd()));
		} catch (HotelException rae) {
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();
			if (this.numOfRemoteErrors == 10) {
				adventure.setState(State.UNDO);
				return;
			}
			return;
		}
		
		adventure.setState(State.CONFIRMED);

	}

//	private void setState(State undo) {
//		// TODO Auto-generated method stub
//	}

	public String getRoomConfirmation() {
		return roomConfirmation;
	}

	public void setRoomConfirmation(String roomConfirmation) {
		this.roomConfirmation = roomConfirmation;
	}

}