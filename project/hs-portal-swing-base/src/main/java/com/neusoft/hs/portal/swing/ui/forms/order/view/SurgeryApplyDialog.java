package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.surgery.SurgeryType;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class SurgeryApplyDialog extends JDialog {

	JPanel surgeryTypePanelWarpper;

	Map<JCheckBox, SurgeryType> surgeryTypeCBs;
	
	JTextArea describeTA;

	JButton confirmBtn;

	JButton closeBtn;

	SurgeryApply surgeryApply;

	private static final int DEFAULT_WIDTH = 300;

	private static final int DEFAULT_HEIGHT = 400;

	public SurgeryApplyDialog() {

		this.setModal(true);

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.SurgeryApply);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel goalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel goalJL = new JLabel(ConstMessagesCN.Labels.Describe);
		goalPanel.add(goalJL);

		describeTA = new JTextArea(3, 30);
		goalPanel.add(describeTA);

		add(goalPanel, BorderLayout.NORTH);

		surgeryTypePanelWarpper = new JPanel(new BorderLayout());

		add(surgeryTypePanelWarpper, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void refreshItems(List<SurgeryType> items) {

		JPanel inspectItemPanel = new JPanel(new GridLayout(items.size(), 1));

		surgeryTypeCBs = new LinkedHashMap<JCheckBox, SurgeryType>();
		for (SurgeryType item : items) {
			JCheckBox surgeryItemCB = new JCheckBox(item.getName());

			boolean isSelected = false;
			if (surgeryApply != null) {
				for (SurgeryApplyItem applyItem : surgeryApply.getSurgeryApplyItems()) {
					if (applyItem.getSurgeryType().equals(item)) {
						isSelected = true;
						break;
					}
				}
			}
			if (isSelected) {
				surgeryItemCB.setSelected(true);
			}

			inspectItemPanel.add(surgeryItemCB);

			surgeryTypeCBs.put(surgeryItemCB, item);
		}

		surgeryTypePanelWarpper.removeAll();
		surgeryTypePanelWarpper.add(inspectItemPanel);
	}

	public void load() {
		if (surgeryApply != null) {
			describeTA.setText(surgeryApply.getGoal());
		}
	}

	public List<SurgeryType> getSelectedSurgeryTypes() {
		List<SurgeryType> selectedItems = new ArrayList<SurgeryType>();

		for (JCheckBox inspectItemCB : this.surgeryTypeCBs.keySet()) {
			if (inspectItemCB.isSelected()) {
				selectedItems.add(this.surgeryTypeCBs.get(inspectItemCB));
			}
		}

		return selectedItems;

	}

	public void setSurgeryApply(SurgeryApply surgeryApply) {
		this.surgeryApply = surgeryApply;
	}

	public JTextArea getDescribeTA() {
		return describeTA;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
