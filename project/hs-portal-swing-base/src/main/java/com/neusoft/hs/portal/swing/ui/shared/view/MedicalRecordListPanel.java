package com.neusoft.hs.portal.swing.ui.shared.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

public class MedicalRecordListPanel extends JPanel {

	private MedicalRecordTableModel medicalRecordTableModel;

	protected JTable table;

	@Autowired
	public MedicalRecordListPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		this.medicalRecordTableModel = new MedicalRecordTableModel();
		table = new JTable(this.medicalRecordTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public MedicalRecordTableModel getMedicalRecordTableModel() {
		return medicalRecordTableModel;
	}

	public JTable getTable() {
		return table;
	}
	
	
}
