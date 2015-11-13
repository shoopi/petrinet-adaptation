package nl.tue.tm.is.ptnet;

import nl.tue.ieis.is.collaboration.enums.*;

public class Transition extends Node{
	
	public static String SILENT_LABEL = "tau";
	public TransitionType type;
	public String partyId;

	public Transition(){
	}
	public Transition(String id){
		this.id = id;
	}
	public Transition(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String toString(){
		return id;
	}
	public String getSILENT_LABEL() {
		return SILENT_LABEL;
	}
	public TransitionType getType() {
		return type;
	}
	public void setSILENT_LABEL(String sILENT_LABEL) {
		SILENT_LABEL = sILENT_LABEL;
	}
	public void setType(TransitionType type) {
		this.type = type;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyId(){
		return partyId;
	}
	
}
