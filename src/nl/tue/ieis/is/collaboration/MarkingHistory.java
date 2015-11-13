/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.util.LinkedHashMap;

import nl.tue.tm.is.ptnet.Marking;
import nl.tue.tm.is.ptnet.Transition;

/**
 * The Class MarkingHistory.
 */
public class MarkingHistory {
	
	/** The marking history. */
	private LinkedHashMap<Transition, Marking> markingHistory;
	
	/**
	 * Instantiates a new marking history.
	 */
	public MarkingHistory(){
		markingHistory = new LinkedHashMap<Transition, Marking>();
	}

	/**
	 * Gets the marking history.
	 *
	 * @return the marking history
	 */
	public LinkedHashMap<Transition, Marking> getMarkingHistory() {
		return markingHistory;
	}

	/**
	 * Sets the marking history.
	 *
	 * @param markingHistory the marking history
	 */
	public void setMarkingHistory(LinkedHashMap<Transition, Marking> markingHistory) {
		this.markingHistory = markingHistory;
	}
	
	/**
	 * Adds the trace.
	 *
	 * @param t the t
	 * @param m the m
	 */
	public void addTrace (Transition t, Marking m) {
		markingHistory.put(t, m);
	}

}
