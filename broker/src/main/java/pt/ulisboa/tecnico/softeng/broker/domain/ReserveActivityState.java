 package pt.ulisboa.tecnico.softeng.broker.domain;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;

public class ReserveActivityState extends AdventureState {
	private static Logger logger = LoggerFactory.getLogger(ReserveActivityState.class);
	private LocalDate begin;
	private LocalDate end;
	private String activityConfirmation;
	
	@Override
	public State getState() {
		return State.RESERVE_ACTIVITY;
	}

	@Override
	public void process(Adventure adventure) {
		logger.debug("process");		
		
		this.getNumOfRemoteErrors();
		try {
			adventure.setActivityConfirmation(ActivityInterface.reserveActivity(adventure.getBegin(), adventure.getEnd(), adventure.getAge()));
		} catch (ActivityException ae) {
			setState(State.UNDO);
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();
			if(this.numOfRemoteErrors >=5){
				adventure.setState(State.CANCELLED);
				return;
			}
		}
			
		//actividade de 1 dia nao precisa de quarto
		if (this.begin.equals(this.end)) {
			setState(State.CONFIRMED);
		} else {
			setState(State.BOOK_ROOM);
		}

		adventure.setState(State.RESERVE_ACTIVITY);

	}

	private void setState(State undo) {
		// TODO Auto-generated method stub
		
	}

	public String getActivityConfirmation() {
		return activityConfirmation;
	}

	public void setActivityConfirmation(String activityConfirmation) {
		this.activityConfirmation = activityConfirmation;
	}

}