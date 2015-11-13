/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


import nl.tue.ieis.is.collaboration.enums.TransitionType;
import nl.tue.ieis.is.collaboration.gui.ChorGUI;
import nl.tue.tm.is.ptnet.*;

/**
 * The Class Strategies.
 */
public class Strategies {
	
	/** The inform party. */
	private InformParty informParty;
	
	/**
	 * Instantiates a new strategies.
	 *
	 * @param informParty the inform party
	 */
	public Strategies(InformParty informParty) {
		this.informParty = informParty;
	}
	
	/**
	 * Strategy.
	 *
	 * @param chor the chor
	 * @param newParty the new party
	 */
	public void Strategy(Choreography chor, Party newParty){
		Transition lastSendTransition = findLastSendTransition(chor, newParty);
		if(lastSendTransition != null) {
			MarkingHistory mh = MigrateToTransition(newParty, lastSendTransition);
			informParty.addNewPartyMarkingHistory(mh);

			Marking lastMigratbaleState = chor.getHistory().getMarkingHistory().get(lastSendTransition);
			Set<Transition> chorEnable = chor.getModel().enabledTransitions(lastMigratbaleState);
			informParty.addTransitionToList(chorEnable);
		}
	}

	/**
	 * Find last send transition.
	 *
	 * @param chor the chor
	 * @param newParty the new party
	 * @return the transition
	 */
	private Transition findLastSendTransition(Choreography chor, Party newParty) {
		Transition lastSendTransition = null;
		for(Transition t : newParty.getOrchestration().transitions()) {
			if(t.getType() == TransitionType.BORDER_SEND) {
				if(chor.getHistory().getMarkingHistory().containsKey(t)) {
					CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
					//TODO: Implement approve method
					if(approveMsg(newParty, cr.getMsg())) {
						ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + newParty.getId() + " has approved message [" + cr.getMsg().getHeader().getSender().getPartyId() + " to " + cr.getMsg().getHeader().getReceiver().getPartyId() + "] : " + cr.getMsg().getPayload() + "\n");
						lastSendTransition = t;
					} else {
						ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + newParty.getId() + " has rejected message [" + cr.getMsg().getHeader().getSender().getPartyId() + " to " + cr.getMsg().getHeader().getReceiver().getPartyId() + "] : " + cr.getMsg().getPayload() + "\n");
					}
				}
			}
		}
		return lastSendTransition;
	}
	
	/**
	 * Migrate to transition.
	 *
	 * @param prt the prt
	 * @param lastSendTransition the last send transition
	 * @return the marking history
	 */
	private MarkingHistory MigrateToTransition(Party prt, Transition lastSendTransition) {
		Marking m = prt.getOrchestration().getInitialMarking();
		List<Message> sentMessages = new ArrayList<Message>();
		List<Message> receivedMessages = new ArrayList<Message>();
		
		ChorFunctions.chorRollback(lastSendTransition);
		
		while(!prt.getHistory().getMarkingHistory().containsKey(lastSendTransition)) {
			for(Transition t : prt.getOrchestration().enabledTransitions(m)) {
				if(prt.getOrchestration().enabledTransitions(m).contains(t)){
					if(t.getType() == TransitionType.BORDER_SEND) {
						CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
						sentMessages.add(cr.getMsg());
						m = prt.getOrchestration().fireTransition(m, t);

					} else if (t.getType() == TransitionType.BORDER_RECEIVE) {
						CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(t);
						receivedMessages.add(cr.getMsg());
						m = prt.getOrchestration().fireTransition(m, t);

					} else {
						m = prt.getOrchestration().fireTransition(m, t);
					}
					prt.getHistory().addTrace(t, m);
				}
			}
		}
		prt.setSentMessages(sentMessages);
		prt.setReceivedMessages(receivedMessages);
		return prt.getHistory();
	}
	
	/**
	 * Approve Message.
	 *
	 * @param p the Party
	 * @param msg the Message
	 * @return true, if successful
	 */
	public boolean approveMsg(Party p, Message msg){
		Random random = new Random();
	    //return random.nextBoolean();
		return true;
	}
}
