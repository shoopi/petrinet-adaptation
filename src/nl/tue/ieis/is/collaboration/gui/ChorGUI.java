package nl.tue.ieis.is.collaboration.gui;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import nl.tue.ieis.is.collaboration.ChorFunctions;
import nl.tue.ieis.is.collaboration.InformParty;
import nl.tue.tm.is.ptnet.Transition;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ChorGUI {

	public JFrame frmChoreography;
	public static JTable table;
	public JFrame frame;
	public static JComboBox comboBox;
	public static JTextArea textArea;
	public static JButton btnNewButton;
	public static JTextArea textArea_2;
	public static boolean isAutoSilentExec = true;
	private JCheckBox chckbxAutomaticallyExecuteSilent;

	public ChorGUI(InformParty informParty) {
		
		Set<Transition> enabledTransition = ChorFunctions.choreography.getModel().enabledTransitions(
				ChorFunctions.choreography.getModel().getInitialMarking());
		comboBox = new JComboBox(enabledTransition.toArray());
		comboBox.setEnabled(false);
		table = initHistoryTable(table, enabledTransition.toString());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChoreography = new JFrame();
		frmChoreography.setTitle("Choreography");
		frmChoreography.setBounds(100, 100, 1009, 450);
		frmChoreography.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmChoreography.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(table, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(table, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
		);
		panel_4.setLayout(gl_panel_4);
		
		textArea_2 = new JTextArea();
		textArea_2.setEnabled(false);
		textArea_2.setLineWrap(true);
		textArea_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		textArea_2.setForeground(Color.RED);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea_2, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea_2, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		JTextArea textArea_1 = new JTextArea();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		btnNewButton = new JButton("Exec Silent");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChorFunctions.fireChorSilentTransition(comboBox, textArea, table);
				ChorFunctions.checkSilentTransition(comboBox, btnNewButton);
			}
		});
		btnNewButton.setEnabled(false);
		
		chckbxAutomaticallyExecuteSilent = new JCheckBox("Automatic");
		chckbxAutomaticallyExecuteSilent.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				isAutoSilentExec = chckbxAutomaticallyExecuteSilent.isSelected();
			}
		});
		chckbxAutomaticallyExecuteSilent.setSelected(true);
		
		JLabel lblSilentExecution = new JLabel("SILENT execution");

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox, 0, 85, Short.MAX_VALUE)
								.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(19))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(chckbxAutomaticallyExecuteSilent, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(16, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblSilentExecution)
							.addContainerGap())))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(chckbxAutomaticallyExecuteSilent, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSilentExecution)
					.addContainerGap(88, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		);
		textArea = new JTextArea(ChorFunctions.choreography.getModel().getInitialMarking().toString());
		scrollPane.setViewportView(textArea);
		textArea.setEnabled(false);
		panel.setLayout(gl_panel);
		frmChoreography.getContentPane().setLayout(groupLayout);
	}
	
	private JTable initHistoryTable(JTable table, String enabledTransition) {
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("PAST");
		model.addColumn("READY");
		model.addRow(new Object[]{"init", enabledTransition});
		return table;
	}
}
