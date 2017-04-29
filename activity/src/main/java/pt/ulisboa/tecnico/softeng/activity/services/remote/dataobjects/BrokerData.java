package pt.ulisboa.tecnico.softeng.activity.services.remote.dataobjects;

import java.util.ArrayList;
import java.util.List;


public class BrokerData {
	public static enum CopyDepth {
		SHALLOW, BULKS, ADVENTURES
	};

	private String name;
	private String code;
	private List<AdventureData> adventures = new ArrayList<>();
	private List<BulkData> bulks = new ArrayList<>();

	public BrokerData() {
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

	public List<AdventureData> getAdventures() {
		return this.adventures;
	}

	public void setAdventures(List<AdventureData> adventures) {
		this.adventures = adventures;
	}

	public List<BulkData> getBulks() {
		return this.bulks;
	}

	public void setBulks(List<BulkData> bulks) {
		this.bulks = bulks;
	}

}
