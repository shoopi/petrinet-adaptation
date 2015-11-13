/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import nl.tue.tm.is.ptnet.Marking;
import nl.tue.tm.is.ptnet.PTNet;
import nl.tue.tm.is.ptnet.Place;

import java.util.*;


/**
 * The Class Choreography.
 */
public class Choreography {
	
	/** The Petri net model. */
	private PTNet model;
	
	/** The initial marking. */
	private Marking initMarking;
	
	/** The communication relations list. */
	private List<CommunicationRelation> comRelations;
	
	/** The marking history. */
	private MarkingHistory history;
	
	/** The intermediary places. */
	private Set<Place> intermediaryPlaces;
	
	/** The party ids. */
	private Set<String> partyIds;
	
	
	/**
	 * Gets the party ids.
	 *
	 * @return the party ids
	 */
	public Set<String> getPartyIds() {
		return partyIds;
	}
	
	/**
	 * Sets the party ids.
	 *
	 * @param partyIds the new party ids
	 */
	public void setPartyIds(Set<String> partyIds) {
		this.partyIds = partyIds;
	}
	
	/**
	 * Gets the intermediary places.
	 *
	 * @return the intermediary places
	 */
	public Set<Place> getIntermediaryPlaces() {
		return intermediaryPlaces;
	}
	
	/**
	 * Sets the intermediary places.
	 *
	 * @param intermediaryPlaces the new intermediary places
	 */
	public void setIntermediaryPlaces(Set<Place> intermediaryPlaces) {
		this.intermediaryPlaces = intermediaryPlaces;
	}
	
	/**
	 * Gets the history.
	 *
	 * @return the history
	 */
	public MarkingHistory getHistory() {
		return history;
	}
	
	/**
	 * Sets the history.
	 *
	 * @param history the new history
	 */
	public void setHistory(MarkingHistory history) {
		this.history = history;
	}
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public PTNet getModel() {
		return model;
	}
	
	/**
	 * Gets the inits the marking.
	 *
	 * @return the inits the marking
	 */
	public Marking getInitMarking() {
		return initMarking;
	}
	
	/**
	 * Gets the com relations.
	 *
	 * @return the com relations
	 */
	public List<CommunicationRelation> getComRelations() {
		return comRelations;
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(PTNet model) {
		this.model = model;
	}
	
	/**
	 * Sets the inits the marking.
	 *
	 * @param initMarking the new inits the marking
	 */
	public void setInitMarking(Marking initMarking) {
		this.initMarking = initMarking;
	}
	
	/**
	 * Sets the com relations.
	 *
	 * @param comRelations the new com relations
	 */
	public void setComRelations(List<CommunicationRelation> comRelations) {
		this.comRelations = comRelations;
	}
	
	/**
	 * Instantiates a new choreography.
	 *
	 * @param model the model
	 * @param initMarking the init marking
	 * @param comRelations the com relations
	 * @param history the history
	 * @param intermediaryPlaces the intermediary places
	 * @param partyIds the party ids
	 */
	public Choreography(PTNet model, Marking initMarking, List<CommunicationRelation> comRelations, MarkingHistory history, Set<Place> intermediaryPlaces, Set<String> partyIds) {
		super();
		this.model = model;
		this.initMarking = initMarking;
		this.comRelations = comRelations;
		this.history = history;
		this.intermediaryPlaces = intermediaryPlaces;
		this.partyIds = partyIds;
	}
	
	/**
	 * Instantiates a new Choreography.
	 *
	 * @param model the model
	 */
	public Choreography(PTNet model) {
		super();
		this.model = model;
	}
}
