package com.neusoft.hs.portal.swing.ui.shared.controller;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

public abstract class AbstractController {

	protected void registerAction(JButton button, ActionListener listener) {
		button.addActionListener(listener);
	}

	protected void unRegisterAction(JButton button) {
		for (ActionListener listener : button.getActionListeners()) {
			button.addActionListener(listener);
		}
	}

	protected void registerAction(JComboBox comboBox, ItemListener listener) {
		comboBox.addItemListener(listener);
	}

	protected void registerAction(JLabel label, MouseListener listener) {
		label.addMouseListener(listener);
	}

	protected void registerAction(JTable table, MouseListener listener) {
		table.addMouseListener(listener);
	}

	protected void registerAction(JCheckBox checkBox, ItemListener listener) {
		checkBox.addItemListener(listener);
	}

}
