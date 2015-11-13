package nl.tue.ieis.is.collaboration.gui;

import java.awt.EventQueue;

import nl.tue.ieis.is.collaboration.ChorFunctions;
import nl.tue.ieis.is.collaboration.InformParty;
import nl.tue.ieis.is.collaboration.Party;
import nl.tue.ieis.is.collaboration.PartyFunctions;

public class RunProgram {

	private static PartyFunctions prtFunc = new PartyFunctions();
	public static InformParty changeInfo = new InformParty();
	
	public static void main(String[] args) {

		ChorFunctions.initChorModel("chor");
		final Party prt1 = prtFunc.initParty("prt1", "PRT1", ChorFunctions.choreography);
		final Party prt2 = prtFunc.initParty("prt2", "PRT2", ChorFunctions.choreography);
		final Party prt3 = prtFunc.initParty("prt3", "PRT3", ChorFunctions.choreography);
		
		//Parameterize choreography
		ChorFunctions.findCommunicationRelations();
		ChorFunctions.labelSilentTransition();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChorGUI window = new ChorGUI(changeInfo);
					window.frmChoreography.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyGUI window = new PartyGUI(prt1, changeInfo);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyGUI window = new PartyGUI(prt2, changeInfo);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyGUI window = new PartyGUI(prt3, changeInfo);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
