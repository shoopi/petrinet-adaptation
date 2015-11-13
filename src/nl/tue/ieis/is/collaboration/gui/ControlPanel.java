package nl.tue.ieis.is.collaboration.gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JSeparator;

import nl.tue.ieis.is.collaboration.ChorFunctions;
import nl.tue.ieis.is.collaboration.InformParty;
import nl.tue.ieis.is.collaboration.Party;
import nl.tue.ieis.is.collaboration.PartyFunctions;
import nl.tue.ieis.is.collaboration.Strategies;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.Color;

public class ControlPanel {

	public JFrame frmPartyControlPanel;
	private List<Party> addedParty = new ArrayList<Party>();
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton;
	private InformParty informParty;
	private PartyFunctions prtFunc = new PartyFunctions();
	private JComboBox comboBox;
	private JTextPane textPane;
	public static int selectedStrategy;

	public ControlPanel(InformParty informParty) {
		this.informParty = informParty;
		Set<String> availableModels = new HashSet<String>();
		availableModels.add("prt4");
		comboBox = new JComboBox(availableModels.toArray());
		textPane = new JTextPane();
		textPane.setText(comboBox.getSelectedItem().toString().toUpperCase());
		initialize();
	}

	private void initialize() {
		frmPartyControlPanel = new JFrame();
		frmPartyControlPanel.setTitle("Party Control");
		frmPartyControlPanel.setBounds(100, 100, 254, 323);
		frmPartyControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmPartyControlPanel.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 192, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblPartyChangeControl = new JLabel("Party Change Control");
		lblPartyChangeControl.setForeground(new Color(0, 0, 0));
		lblPartyChangeControl.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnNewButton_1 = new JButton("Global Adaptation Strategy");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Strategies strat = new Strategies(informParty);
				selectedStrategy = 1;
				strat.Strategy(ChorFunctions.choreography, addedParty.get(addedParty.size()-1));
				frmPartyControlPanel.dispose();
			}
		});
		btnNewButton_1.setEnabled(false);
		
		btnNewButton_2 = new JButton("Self-Adaptation Strategy");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Strategies strat = new Strategies(informParty);
				selectedStrategy = 2;
				strat.Strategy(ChorFunctions.choreography, addedParty.get(addedParty.size()-1));
				frmPartyControlPanel.dispose();
			}
		});
		btnNewButton_2.setEnabled(false);

		btnNewButton_3 = new JButton("Negotiation");
		btnNewButton_3.setEnabled(false);

		btnNewButton = new JButton("Add Party");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Party prt = prtFunc.generatePartyGUI(comboBox.getSelectedItem().toString(), textPane.getText());
					addedParty.add(prt);
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(true);
					btnNewButton_2.setEnabled(true);
				} catch (Exception es) {
					//TODO
				}
			}
		});
		
		JLabel lblPartyId = new JLabel("Party ID");
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblPartyId)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPartyChangeControl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(79, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addGap(20))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addComponent(btnNewButton_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(45))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblPartyChangeControl)
					.addGap(18)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPartyId)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_3)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frmPartyControlPanel.getContentPane().setLayout(groupLayout);
	}
}
