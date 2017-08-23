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

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class InspectApplyDialog extends JDialog {

	JPanel inspectItemPanelWarpper;

	Map<JCheckBox, InspectItem> inspectItemCBs;

	JTextArea goalTA;

	JButton confirmBtn;

	JButton closeBtn;

	InspectApply inspectApply;

	private static final int DEFAULT_WIDTH = 300;

	private static final int DEFAULT_HEIGHT = 400;

	public InspectApplyDialog() {

		this.setModal(true);

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.InspectApply);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel goalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel goalJL = new JLabel(ConstMessagesCN.Labels.GOAL);
		goalPanel.add(goalJL);

		goalTA = new JTextArea(3, 30);
		goalPanel.add(goalTA);

		add(goalPanel, BorderLayout.NORTH);

		inspectItemPanelWarpper = new JPanel(new BorderLayout());

		add(inspectItemPanelWarpper, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void refreshItems(List<InspectItem> items) {

		JPanel inspectItemPanel = new JPanel(new GridLayout(items.size(), 1));

		inspectItemCBs = new LinkedHashMap<JCheckBox, InspectItem>();
		for (InspectItem item : items) {
			JCheckBox inspectItemCB = new JCheckBox(item.getName());

			boolean isSelected = false;
			if (inspectApply != null) {
				for (InspectApplyItem applyItem : inspectApply.getInspectApplyItems()) {
					if (applyItem.getInspectItem().equals(item)) {
						isSelected = true;
						break;
					}
				}
			}
			if (isSelected) {
				inspectItemCB.setSelected(true);
			}

			inspectItemPanel.add(inspectItemCB);

			inspectItemCBs.put(inspectItemCB, item);
		}

		inspectItemPanelWarpper.removeAll();
		inspectItemPanelWarpper.add(inspectItemPanel);
	}

	public void load() {
		if (inspectApply != null) {
			goalTA.setText(inspectApply.getGoal());
		}
	}

	public List<InspectItem> getSelectedInspectItems() {
		List<InspectItem> selectedItems = new ArrayList<InspectItem>();

		for (JCheckBox inspectItemCB : this.inspectItemCBs.keySet()) {
			if (inspectItemCB.isSelected()) {
				selectedItems.add(this.inspectItemCBs.get(inspectItemCB));
			}
		}

		return selectedItems;

	}

	public void setInspectApply(InspectApply inspectApply) {
		this.inspectApply = inspectApply;
	}

	public JTextArea getGoalTA() {
		return goalTA;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
