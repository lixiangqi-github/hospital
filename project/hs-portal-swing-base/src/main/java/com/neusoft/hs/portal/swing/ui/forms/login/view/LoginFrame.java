package com.neusoft.hs.portal.swing.ui.forms.login.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.forms.login.model.AbstractUserComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

import javax.swing.*;

import java.awt.*;

@Component
public class LoginFrame extends JDialog {

	private LoginFormPanel formPanel;
	private LoginFormBtnPanel formBtnPanel;
	
	private final static int Width = 440;
	private final static int Height = 150;

	@Autowired
	public LoginFrame(LoginFormPanel formPanel, LoginFormBtnPanel formBtnPanel) {
		this.formPanel = formPanel;
		this.formBtnPanel = formBtnPanel;
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.DialogTitles.Login_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
	}

	private void initComponents() {
		add(formPanel, BorderLayout.CENTER);
		add(formBtnPanel, BorderLayout.SOUTH);
	}

	public LoginFormPanel getFormPanel() {
		return formPanel;
	}

	public LoginFormBtnPanel getFormBtnPanel() {
		return formBtnPanel;
	}
	
	public AbstractUserComboBoxModel getAbstractUserComboBoxModel() {
		return formPanel.getAbstractUserComboBoxModel();
	}
}
