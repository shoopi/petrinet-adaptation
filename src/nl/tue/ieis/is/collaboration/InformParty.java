/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer; 
import java.util.Set;

import nl.tue.tm.is.ptnet.Transition;

/**
 * The Class InformParty.
 */
public class InformParty extends Observable {
	
	
	private List<Transition> transitions;
	private List<Observer> observerParties = new ArrayList<Observer>();
	private MarkingHistory newPartyMarkingHistory;

	public InformParty() {
		if(transitions == null)
			transitions = new ArrayList<Transition>();
		if(newPartyMarkingHistory == null)
			newPartyMarkingHistory = new MarkingHistory();
	}
	
	public List<Transition> getTransitionList() {
		return transitions;
	}
	
	public MarkingHistory getMarkingHistory() {
		return newPartyMarkingHistory;
	}
	
	public void addTransitionToList(Set<Transition> trans) {
		transitions.addAll(trans);
		this.setChanged();
		notifyObservers(transitions);
	}
	
	public void addNewPartyMarkingHistory(MarkingHistory mh) {
		this.newPartyMarkingHistory.getMarkingHistory().putAll(mh.getMarkingHistory());
		this.setChanged();
		notifyObservers(newPartyMarkingHistory);
	}  
  
    public void registerObserver(Observer observer) {
    	observerParties.add(observer);
	}  
  
    public void removeObserver(Observer observer) {
    	observerParties.remove(observer);
     }  
}
