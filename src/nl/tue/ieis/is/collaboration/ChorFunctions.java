/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import nl.tue.ieis.is.collaboration.enums.MessageType;
import nl.tue.ieis.is.collaboration.enums.TransitionType;
import nl.tue.ieis.is.collaboration.gui.ChorGUI;
import nl.tue.tm.is.ptnet.Marking;
import nl.tue.tm.is.ptnet.PTNet;
import nl.tue.tm.is.ptnet.Place;
import nl.tue.tm.is.ptnet.Transition;


/**
 * The Class ChorFunctions.
 */
public class ChorFunctions {
	
	/** The static choreography. */
	public static Choreography choreography;
	
	/**
	 * Inits the chor model.
	 *
	 * @param modelName the model name
	 */
	public static void initChorModel(String modelName) {
		choreography = new Choreography(PTNet.loadPNML("models/" + modelName + ".pnml"));
		List<CommunicationRelation> comRels = new ArrayList<CommunicationRelation>();
		choreography.setComRelations(comRels);
		choreography.setHistory(new MarkingHistory());
		choreography.setInitMarking(initMarking(choreography.getModel()));
		choreography.getHistory().addTrace(null,initMarking(choreography.getModel()));
	}
	
	/**
	 * Find intermediary place.
	 */
	public static void findIntermediaryPlace() {
		Set<Place> intermediaryPlace = new HashSet<Place>();
		for(Place p : choreography.getModel().places()) {
			if(choreography.getModel().getPre(p).size() == 1 && 
					choreography.getModel().getPost(p).size() == 1) {
				
				Transition t1 = (Transition) choreography.getModel().getPre(p).toArray()[0];
				Transition t2 = (Transition) choreography.getModel().getPost(p).toArray()[0];
				
				
				if(t1.getPartyId() != t2.getPartyId())
					intermediaryPlace.add(p);
			}
		}
		choreography.setIntermediaryPlaces(intermediaryPlace);
	}
	
	/**
	 * Find communication relations.
	 */
	public static void findCommunicationRelations() {
		findIntermediaryPlace();
		for(Place p : choreography.getIntermediaryPlaces()) {
			Transition sender = (Transition) choreography.getModel().getPre(p).toArray()[0];
			Transition receiver = (Transition) choreography.getModel().getPost(p).toArray()[0];
			choreography.getComRelations().add(new CommunicationRelation(sender, receiver, p, MessageType.SYNCHRONOUS));
		}
	}
	
	/**
	 * Inits the marking.
	 *
	 * @param ptnet the ptnet
	 * @return the marking
	 */
	private static Marking initMarking(PTNet ptnet) {
		Marking m = new Marking();
		for(Place p : ptnet.places()) {
			if(ptnet.getPre(p).size() == 0) {
				ptnet.addMarking(p, 1);
				m.addMark(p, 1);
			} else {
				ptnet.addMarking(p, 0);
				m.addMark(p, 0);
			}
		}
		return m;
	}
	
	/**
	 * Find communication relation by transition.
	 *
	 * @param t 
	 * @return the communication relation
	 */
	public static CommunicationRelation findCommunicationRelationByTransition(Transition t) {
		for(CommunicationRelation cr : choreography.getComRelations()) {
			if(cr.getReceiver().equals(t) ) { 
				return cr;
			} else if(cr.getSender().equals(t)) {
				return cr;
			}
		}
		return null;
	}
	
	/**
	 * Label silent transition.
	 */
	public static void labelSilentTransition(){
		for(Transition t : choreography.getModel().transitions()) {
			if(t.getType() == null) t.setType(TransitionType.SILENT);
		}
	}
	
	/**
	 * Check silent transition.
	 *
	 * @param combobox the combobox
	 * @param button the button
	 */
	public static void checkSilentTransition(JComboBox combobox, JButton button) {
		Set<Transition> enabledTransition = choreography.getModel().enabledTransitions(getLastMarking(choreography.getHistory()));
		Set<Transition> silentEnabledTransition = new HashSet<Transition>();
		boolean isSilent = false;
		for(Transition t: enabledTransition) {
			if(t.getType() == TransitionType.SILENT) {
				silentEnabledTransition.add(t);
				isSilent = true;
			}
		}
		combobox.setModel(new DefaultComboBoxModel(silentEnabledTransition.toArray()));
		combobox.setEnabled(isSilent);
		button.setEnabled(isSilent);
		//Auto Firing
		if(isSilent) {
			if(ChorGUI.isAutoSilentExec)
				fireChorSilentTransition(combobox, ChorGUI.textArea, ChorGUI.table);
		}
	}
	
	/**
	 * Fire chor silent transition.
	 *
	 * @param comboBox the combo box
	 * @param chorMarking the chor marking
	 * @param table the table
	 */
	public static void fireChorSilentTransition(JComboBox comboBox, JTextArea chorMarking, JTable table) {
		Marking temp = new Marking();
		Transition t = choreography.getModel().findTransition(comboBox.getSelectedItem().toString());
		temp = choreography.getModel().fireTransition(getLastMarking(choreography.getHistory()), t);
		choreography.getHistory().addTrace(t, temp);
		chorMarking.setText(chorMarking.getText() + "\n*SILENT: " + temp.toString() + "*");
		addTableRow(table, t.getId(), choreography.getModel().enabledTransitions(temp).toString());
	}
	
	/**
	 * Gets the last marking.
	 *
	 * @param history the history
	 * @return the last marking
	 */
	private static Marking getLastMarking(MarkingHistory history) {
		int lastIndex = history.getMarkingHistory().size()-1;
		return (Marking) history.getMarkingHistory().values().toArray()[lastIndex];
	}
	
	/**
	 * Adds the table row.
	 *
	 * @param table the table
	 * @param past the past
	 * @param ready the ready
	 */
	private static void addTableRow(JTable table, String past, String ready) {
		DefaultTableModel tbl = (DefaultTableModel) table.getModel();
		tbl.addRow(new String[]{past, ready});
	}
	
	/**
	 * Chor rollback.
	 *
	 * @param t the t
	 * @return the marking
	 */
	public static Marking chorRollback(Transition t) {
		if(choreography.getHistory().getMarkingHistory().containsKey(t)) {
			Marking m = choreography.getHistory().getMarkingHistory().get(t);
			
			choreography.getHistory().addTrace(changeTransitionIdForRedo(t), m);
			redoIdChange(t);
			
			ChorGUI.textArea.setText(ChorGUI.textArea.getText() + "\n*ROLLBACK: " + m.toString() + "*");
			addTableRow(ChorGUI.table, "===", choreography.getModel().enabledTransitions(m).toString());
			
		return m;
		} else return getLastMarking(choreography.getHistory());
	}
	
	/**
	 * Change transition id for redo.
	 *
	 * @param t the t
	 * @return the transition
	 */
	private static Transition changeTransitionIdForRedo(Transition t) {
		t.setId(t.getId()+t.getId());
		return t;
	}
	
/**
 * Redo id change.
 *
 * @param t the t
 * @return the transition
 */
private static Transition redoIdChange(Transition t) {
		t.setId(t.getId().substring(t.getId().length()/2));
		return t;
	}
}
