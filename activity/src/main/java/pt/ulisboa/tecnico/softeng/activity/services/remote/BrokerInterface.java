package pt.ulisboa.tecnico.softeng.activity.services.remote;

import java.util.List;

import pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects.BulkData;
import pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects.BrokerData.CopyDepth;

public class BrokerInterface {

	public static List<BrokerData> getBrokers(){
		//TODO
		return null;
	}
	
	public static void createBroker(BrokerData brokerData){
		//TODO
	}
	
	public static BrokerData getBrokerDataByCode(String brokerCode, CopyDepth depth){
		//TODO
		return null;
	}
	
	public static void createAdventure(String brokerCode, AdventureData adventureData){
		//TODO
	}
	
	public static void createBulkRoomBooking(String brokerCode, BulkData bulkData){
		//TODO
	}
}
