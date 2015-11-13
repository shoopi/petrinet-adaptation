package nl.tue.ieis.is.collaboration.gui;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import nl.tue.tm.is.ptnet.*;
import nl.tue.ieis.is.collaboration.*;
import nl.tue.ieis.is.collaboration.enums.*;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;

public class PartyGUI implements Observer{

	public JFrame frame;
	private JComboBox comboBox;
	private JTextArea textArea;
	
	private PartyFunctions prtFunc = new PartyFunctions();
	private Party prt;
	private JTable table_1;
	private JLabel lblMessage;
	private JTextPane textPane;
	private JTextArea textArea_1;
	private JLabel errorLbl;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private InformParty informParty;
	
	public PartyGUI(final Party prt, InformParty informParty) {
		
		this.informParty = informParty;
		informParty.addObserver(this);
		
		this.prt = prt;
		Set<Transition> enabledTransition = prt.getOrchestration().enabledTransitions(prt.getOrchestration().getInitialMarking());
		comboBox = new JComboBox(enabledTransition.toArray());
		textArea = new JTextArea(prt.getOrchestration().getInitialMarking().toString());
		table_1 = initHistoryTable(table_1, enabledTransition.toString());
		textPane = new JTextPane();
		lblMessage = new JLabel();
		textArea_1 = new JTextArea();
		errorLbl = new JLabel("");
		lblNewLabel_2 = new JLabel();
		btnNewButton = new JButton("Execute");
		ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + prt.getId() + " has been added." + "\n");
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(prt.getId());
		
		prtFunc.checkGUIStatus(prt, comboBox, lblMessage, textPane, textArea_1, lblNewLabel_2, btnNewButton);
		
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		JPanel panel_3 = new JPanel();
		JPanel panel_4 = new JPanel();
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				prtFunc.checkGUIStatus(prt, comboBox, lblMessage, textPane, textArea_1, lblNewLabel_2, btnNewButton);
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prtFunc.firePartyTransition(prt, ChorFunctions.choreography, comboBox, textPane, textArea, errorLbl, table_1, textArea_1, lblNewLabel_2);
				ChorFunctions.checkSilentTransition(ChorGUI.comboBox, ChorGUI.btnNewButton);
				prtFunc.checkGUIStatus(prt, comboBox, lblMessage, textPane, textArea_1,lblNewLabel_2, btnNewButton);
			}
		});
		
		btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prtFunc.removeParty(prt, informParty);
				frame.dispose();
				//btnNewButton.setEnabled(false);
				//btnNewButton_1.setEnabled(false);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Message History");
		JLabel lblNewLabel_1 = new JLabel("Current Messsage");
		JLabel lblLastMessageStatus = new JLabel("Last Message Status:");
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollPane = new JScrollPane();
		JScrollPane scrollPane_1 = new JScrollPane();
		
		scrollPane.setViewportView(textArea_1);
		scrollPane_1.setViewportView(textPane);

		textArea.setEnabled(false);
		textArea_1.setEnabled(false);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		GroupLayout gl_panel = new GroupLayout(panel);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 639, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_3, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE))
		);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(table_1, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(12)
					.addComponent(table_1, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(errorLbl, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
					.addComponent(lblLastMessageStatus, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
						.addComponent(lblLastMessageStatus, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
					.addGap(20))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addComponent(errorLbl, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(lblMessage)
					.addContainerGap())
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addContainerGap())
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(lblNewLabel_1)
					.addContainerGap())
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(4)
					.addComponent(lblMessage)
					.addGap(20)
					.addComponent(lblNewLabel_1)
					.addGap(8)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
						.addComponent(comboBox, 0, 94, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(36)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap(139, Short.MAX_VALUE))
		);
		
		panel.setLayout(gl_panel);
		panel_1.setLayout(gl_panel_1);
		panel_2.setLayout(gl_panel_2);
		panel_3.setLayout(gl_panel_3);
		panel_4.setLayout(gl_panel_4);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private JTable initHistoryTable(JTable table, String enabledTransition) {
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("PAST");
		//model.addColumn("SKIPPED");
		model.addColumn("READY");
		model.addRow(new Object[]{"init", enabledTransition});
		return table;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof List) {
			if(ControlPanel.selectedStrategy == 1)
				changeToRelatedPartyStrat1(arg);
			if(ControlPanel.selectedStrategy == 2)
				changeToRelatedPartyStrat2(arg);
		}
		if (arg instanceof MarkingHistory) {
			changeForNewParty(arg);
		}
	}

	private void changeForNewParty(Object arg) {
		MarkingHistory history = (MarkingHistory)arg;
		try {
			
			Marking initMarking = (Marking) history.getMarkingHistory().values().toArray()[0];
			if(prt.getOrchestration().getInitialMarking().equals(initMarking)) {
				
				LinkedHashMap<Transition, Marking> temp = history.getMarkingHistory(); 
				for(int i = 1 ; i < temp.size() ; i++) {
					Marking m = (Marking) temp.values().toArray()[i];
					Transition t = (Transition) temp.keySet().toArray()[i];
					Set<Transition> enabeledT = prt.getOrchestration().enabledTransitions(m);

					comboBox.setModel(new DefaultComboBoxModel(enabeledT.toArray()));
					textArea.setText(textArea.getText() + "\n" + m.toString());
					DefaultTableModel tbl = (DefaultTableModel) table_1.getModel();

					tbl.addRow(new String[]{t.getId(), enabeledT.toString()});	
					//TODO: RETREIVE MESSAGES ALSO
					
				}
			}
		} catch (Exception e) {}
	}

	private void changeToRelatedPartyStrat1(Object arg) {
		List<Transition> rollbackTo = (List<Transition>)arg;
		if(rollbackTo != null) {
			for(Transition t : rollbackTo) {
				try{
					if(prt.getOrchestration().findTransition(t.getId()) != null) {
						if(prt.getHistory().getMarkingHistory().containsKey(t)) {
							ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "New Party force " + prt.getId() + " to restart from transition " + t.getId() + "\n");
							Marking rollbackState = prtFunc.rollback(prt, t);
							
							t.setId(t.getId()+t.getId());
							prt.getHistory().addTrace(t, rollbackState);
							t.setId(t.getId().substring(t.getId().length()/2));
							
							Set<Transition> updateList = prt.getOrchestration().enabledTransitions(rollbackState);
							comboBox.setModel(new DefaultComboBoxModel(updateList.toArray()));
							textArea.setText(textArea.getText() + "\n*ROLLBACK " + rollbackState.toString());
							DefaultTableModel tbl = (DefaultTableModel) table_1.getModel();
							tbl.addRow(new String[]{"===", updateList.toString()});
							prtFunc.checkGUIStatus(prt, comboBox, lblMessage, textPane, textArea_1, lblNewLabel_2, btnNewButton);
							
						} else {
							ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + prt.getId() + " has not reached to transition " + t.getId() + " yet \n ");
						}
					}
				} catch (Exception e) {}
			}
		}
	}
	
	private void changeToRelatedPartyStrat2(Object arg) {
		List<Transition> rollbackTo = (List<Transition>)arg;
		if(rollbackTo != null) {
			for(Transition t : rollbackTo) {
				try{
					if(prt.getOrchestration().findTransition(t.getId()) != null) {
						if(prt.getHistory().getMarkingHistory().containsKey(t)) {
							ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "New Party suggests " + t.getId() + " to " + prt.getId() + "to fire\n");
							
							Marking m = prt.getHistory().getMarkingHistory().get(t);
							boolean start = false;
							Transition tempTransition = t;
							
							for(Entry<Transition, Marking> history : prt.getHistory().getMarkingHistory().entrySet()) {
								if(history.getValue() == m ) start = true;
								if(start) {
									if(history.getKey().getType() == TransitionType.BORDER_SEND) {
										CommunicationRelation cr = ChorFunctions.findCommunicationRelationByTransition(history.getKey());
										//TODO CHECK MESSAGE CONTENT AND HEADER
										break;
									} else {
										m = history.getValue();
										tempTransition = history.getKey();
										if(ChorFunctions.choreography.getHistory().getMarkingHistory().containsKey(tempTransition)) {
											Marking tempMarking = ChorFunctions.choreography.getHistory().getMarkingHistory().get(tempTransition);
											tempTransition.setId(tempTransition.getId()+tempTransition.getId());
											ChorFunctions.choreography.getHistory().addTrace(tempTransition, tempMarking);
											tempTransition.setId(tempTransition.getId().substring(tempTransition.getId().length()/2));
											
										}
									}
								}
							}
							Marking rollbackState = prtFunc.rollback(prt, tempTransition);
							tempTransition.setId(tempTransition.getId()+tempTransition.getId());
							prt.getHistory().addTrace(tempTransition, rollbackState);
							tempTransition.setId(tempTransition.getId().substring(tempTransition.getId().length()/2));
							
							Set<Transition> updateList = prt.getOrchestration().enabledTransitions(rollbackState);
							comboBox.setModel(new DefaultComboBoxModel(updateList.toArray()));
							textArea.setText(textArea.getText() + "\n*ROLLBACK " + rollbackState.toString());
							DefaultTableModel tbl = (DefaultTableModel) table_1.getModel();
							tbl.addRow(new String[]{"===", updateList.toString()});
							prtFunc.checkGUIStatus(prt, comboBox, lblMessage, textPane, textArea_1, lblNewLabel_2, btnNewButton);
						} else {
							ChorGUI.textArea_2.setText(ChorGUI.textArea_2.getText() + "Party " + prt.getId() + " has not reached to transition " + t.getId() + " yet \n ");
						}
					}
				} catch (Exception e) {}
			}
		}
	}
}
