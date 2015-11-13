/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import nl.tue.ieis.is.collaboration.enums.MessageStatus;
import nl.tue.ieis.is.collaboration.enums.TransitionType;
import nl.tue.ieis.is.collaboration.gui.ChorGUI;
import nl.tue.ieis.is.collaboration.gui.ControlPanel;
import nl.tue.ieis.is.collaboration.gui.PartyGUI;
import nl.tue.ieis.is.collaboration.gui.RunProgram;
import nl.tue.tm.is.ptnet.Marking;
import nl.tue.tm.is.ptnet.PTNet;
import nl.tue.tm.is.ptnet.Place;
import nl.tue.tm.is.ptnet.Transition;

/**
 * The Class PartyFunctions.
 */
public class PartyFunctions {

	/**
	 * Inits the party.
	 *
	 * @param modelName the model name
	 * @param partyId the party id
	 * @param chor the choroeography
	 * @return the party
	 */
	public Party initParty(String modelName, String partyId, Choreography chor) {
		PTNet orchest = PTNet.loadPNML("models/" + modelName + ".pnml");
		List<Message> sent = new ArrayList<Message>();
		List<Message> received = new ArrayList<Message>();
		Marking m = initMarking(orchest);
		MarkingHistory history = new MarkingHistory();
		history.addTrace(null, m);
		Party prt = new Party(partyId, orchest, history, sent, received);
		labelFunction(chor, prt);
		return prt;
	}
	
	/**
	 * Inits the marking.
	 *
	 * @param ptnet the ptnet
	 * @return the marking
	 */
	private Marking initMarking(PTNet ptnet) {
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
	 * Label function.
	 *
	 * @param chor the chor
	 * @param prt the prt
	 */
	private void labelFunction(Choreography chor, Party prt) {
		for(Transition t : prt.getOrchestration().transitions()) {
			t.setPartyId(prt.getId());
			if(chor.getModel().transitions().contains(t)) {
				chor.getModel().findTransition(t.getId()).setPartyId(prt.getId());
				if(prt.getOrchestration().getPre(t).size() != chor.getModel().getPre(t).size()) {
					t.setType(TransitionType.BORDER_RECEIVE);
					chor.getModel().findTransition(t.getId()).setType(TransitionType.BORDER_RECEIVE);
				} else if (prt.getOrchestration().getPost(t).size() != chor.getModel().getPost(t).size()) {
					t.setType(TransitionType.BORDER_SEND);
					chor.getModel().findTransition(t.getId()).setType(TransitionType.BORDER_SEND);
				}
			} else {
				t.setType(TransitionType.INTERNAL);
				
			}
			//System.out.println(t + " in " + prt.getId() + " is " + t.getType());
		}
	}
	
	
	/**
	 * Fire party transition.
	 *
	 * @param p the p
	 * @param chor the chor
	 * @param comboBox the combo box
	 * @param msgTextPane the msg text pane
	 * @param textArea the text area
	 * @param errorLbl the error lbl
	 * @param table the table
	 * @param messageHistoryText the message history text
	 * @param msgStatusLbl the msg status lbl
	 */
	public void firePartyTransition(Party p, Choreography chor, JComboBox comboBox, 
			JTextPane msgTextPane, JTextArea textArea, JLabel errorLbl, JTable table, JTextArea messageHistoryText, JLabel msgStatusLbl) {
		
		JTextArea chorMarking = ChorGUI.textArea;
		JTable chorTable = ChorGUI.table;
		
		Marking m = new Marking();
		m =getLastMarking(p.getHistory());

		Transition t = p.getOrchestration().findTransition(comboBox.getSelectedItem().toString());
		if(t.getType() == TransitionType.BORDER_SEND) {
			Set<Transition> chorEnabled = chor.getModel().enabledTransitions(getLastMarking(chor.getHistory()));
			if(chorEnabled.contains(t)) {
				CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);

				if(msgTextPane.getText().length() > 3) {
					
					Message msg = new Message(msgTextPane.getText(), MessageStatus.SENT, cr);
					p.getSentMessages().add(msg);
					cr.setMsg(msg);
					messageHistoryText.setText(messageHistoryText.getText() + "Send to " + cr.getReceiver().getPartyId() + ":\n Payload: [" + msgTextPane.getText() + "]\n---\n");
					msgStatusLbl.setText(msg.getStatus().toString());
					
					Marking temp = new Marking();
					temp = chor.getModel().fireTransition(getLastMarking(chor.getHistory()), t);
					addToChorHistory(chor, t, temp);
					//chor.getHistory().addTrace(t, temp);
					chorMarking.setText(chorMarking.getText() + "\n" + temp.toString());
					addTableRow(chorTable, t.getId(), chor.getModel().enabledTransitions(temp).toString());
					
					m = p.getOrchestration().fireTransition(m, t);
					//p.getHistory().addTrace(t, m);
					addToPartyHistory(p, t, m);
					textArea.setText(textArea.getText() + "\n" + m.toString());
					Set<Transition> enabledTransition = p.getOrchestration().enabledTransitions(m);
					comboBox.setModel(new DefaultComboBoxModel(enabledTransition.toArray()));
					errorLbl.setText("");
				} else errorLbl.setText("message content should be more than 3 characters.");	
			} else errorLbl.setText("Transition " + t + " is not enabled in the choreography");
			
		} else if (t.getType() == TransitionType.BORDER_RECEIVE) {
			CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
			Place choreInterBeforePlace = cr.getIntermediary();

			//if choreography is enabled
			if(getLastMarking(chor.getHistory()).getMark(choreInterBeforePlace) > 0) {
				Marking temp = new Marking();
				temp = chor.getModel().fireTransition(getLastMarking(chor.getHistory()), t);
				//chor.getHistory().addTrace(t, temp);
				addToChorHistory(chor, t, temp);
				chorMarking.setText(chorMarking.getText() + "\n" + temp.toString());
				addTableRow(chorTable, t.getId(), chor.getModel().enabledTransitions(temp).toString());
				
				Message msg = cr.getMsg();
				msg.setStatus(MessageStatus.SEEN);
				messageHistoryText.setText(messageHistoryText.getText() + "Receive from " + cr.getSender().getPartyId() + ":\n Payload: [" + cr.getMsg().getPayload() + "]\n---\n");
				p.getReceivedMessages().add(msg);
				msgStatusLbl.setText(msg.getStatus().toString());
				
				m = p.getOrchestration().fireTransition(m, t);
				//p.getHistory().addTrace(t, m);
				addToPartyHistory(p, t, m);
				textArea.setText(textArea.getText() + "\n" + m.toString());
				Set<Transition> enabledTransition = p.getOrchestration().enabledTransitions(m);
				comboBox.setModel(new DefaultComboBoxModel(enabledTransition.toArray()));
				addTableRow(table, t.getId(), enabledTransition.toString());
				errorLbl.setText("");
			} else {
				chorMarking.setText(chorMarking.getText() + "\n" + "there is no token in " + choreInterBeforePlace + " in order to fire " + t);
				errorLbl.setText("there is no token in " + choreInterBeforePlace + " in order to fire " + t);
			}
		
			//Internal Activity
		} else {
			m = p.getOrchestration().fireTransition(m, t);
			//p.getHistory().addTrace(t, m);
			addToPartyHistory(p, t, m);
			textArea.setText(textArea.getText() + "\n" + m.toString());
			Set<Transition> enabledTransition = p.getOrchestration().enabledTransitions(m);
			comboBox.setModel(new DefaultComboBoxModel(enabledTransition.toArray()));
			addTableRow(table, t.getId(), enabledTransition.toString());
		}
	}
	
	/**
	 * Gets the last marking.
	 *
	 * @param history the history
	 * @return the last marking
	 */
	private Marking getLastMarking(MarkingHistory history) {
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
	private void addTableRow(JTable table, String past, String ready) {
		DefaultTableModel tbl = (DefaultTableModel) table.getModel();
		tbl.addRow(new String[]{past, ready});
	}
	
	/**
	 * Check gui status.
	 *
	 * @param prt the prt
	 * @param comboBox the combo box
	 * @param label the label
	 * @param textPane the text pane
	 * @param textArea_1 the text area_1
	 * @param msgStatusLbl the msg status lbl
	 * @param button the button
	 */
	public void checkGUIStatus(Party prt, JComboBox comboBox, JLabel label, JTextPane textPane, JTextArea textArea_1, JLabel msgStatusLbl, JButton button) {
		if(comboBox.getSelectedItem() != null ) {
			Transition t = prt.getOrchestration().findTransition(comboBox.getSelectedItem().toString());
			if(t.getType() == TransitionType.BORDER_SEND) {
				CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
				label.setText("SEND TO " + cr.getReceiver().getPartyId());
				textPane.setEnabled(true);
				msgStatusLbl.setText("Ready to send a message...");
			} else if (t.getType() == TransitionType.BORDER_RECEIVE) {
				CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
				label.setText("RECEIVE FROM " + cr.getSender().getPartyId());
				if(cr.getMsg() != null) {
					textPane.setText(cr.getMsg().getPayload());
					cr.getMsg().setStatus(MessageStatus.DELIVERED);
					msgStatusLbl.setText(cr.getMsg().getStatus().toString());
				} else {
					msgStatusLbl.setText("Waiting for receiving a message...");
				}
				textPane.setEnabled(false);
	
			} else {
				label.setText("Internal");
				textPane.setText("");
				textPane.setEnabled(false);
			}
		} else {
			button.setEnabled(false);
		}
	}
	
	/**
	 * Rollback.
	 *
	 * @param prt the prt
	 * @param t the t
	 * @return the marking
	 */
	public Marking rollback(Party prt, Transition t) {
		Marking m = prt.getHistory().getMarkingHistory().get(t);
		Marking prev = null;
		Transition beforeT = null;
		for (Entry<Transition, Marking> history : prt.getHistory().getMarkingHistory().entrySet()) {
			if(history.getValue() != m) {
				prev = history.getValue();
				beforeT = history.getKey();
			} else break;
        }
		
		prt.getHistory().addTrace(beforeT, prev);
		return prev;
	}
	
	/**
	 * Removes the party.
	 *
	 * @param prt the prt
	 * @param informParty the inform party
	 */
	public void removeParty(Party prt, final InformParty informParty) {
		prt.setOrchestration(null);
		prt.setHistory(null);
		prt.setReceivedMessages(null);
		prt.setSentMessages(null);
		ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + prt.getId() + " has been removed."  + "\n");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlPanel window = new ControlPanel(informParty);
					window.frmPartyControlPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Generate party gui.
	 *
	 * @param model the model
	 * @param partyId the party id
	 * @return the party
	 */
	public Party generatePartyGUI (String model, String partyId) {
		final Party newPrt = initParty(model, partyId, ChorFunctions.choreography);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyGUI window = new PartyGUI(newPrt, RunProgram.changeInfo);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return newPrt;
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
	
	/**
	 * Adds the to party history.
	 *
	 * @param p the p
	 * @param t the t
	 * @param m the m
	 */
	private static void addToPartyHistory(Party p, Transition t, Marking m) {
		if(p.getHistory().getMarkingHistory().containsKey(t)) {
			t = changeTransitionIdForRedo(t);
			p.getHistory().addTrace(t, m);
			t = redoIdChange(t);
		} else 
			p.getHistory().addTrace(t, m);
	}
	
	/**
	 * Adds the to chor history.
	 *
	 * @param chor the chor
	 * @param t the t
	 * @param m the m
	 */
	private static void addToChorHistory(Choreography chor, Transition t, Marking m) {
		if(chor.getHistory().getMarkingHistory().containsKey(t)) {
			t = changeTransitionIdForRedo(t);
			chor.getHistory().addTrace(t, m);
			t = redoIdChange(t);
		} else 
			chor.getHistory().addTrace(t, m);
	}
}
