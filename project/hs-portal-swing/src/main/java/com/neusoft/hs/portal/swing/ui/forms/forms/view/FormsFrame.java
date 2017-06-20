package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

@Component
public class FormsFrame extends JFrame {

	private JButton loginBtn;

	private JButton registerBtn;

	private JButton cashierBtn;

	private JButton receiveBtn;

	private JButton createOrderBtn;

	private JButton verifyOrderBtn;

	private JButton sendOrderExecuteBtn;
	
	private JLabel loginLbl;

	public FormsFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesEN.Labels.FORMS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		
		setLayout(new BorderLayout());
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(10, 2, 20, 20));
		
		loginBtn = new JButton(ConstMessagesEN.Labels.Login);
		registerBtn = new JButton(ConstMessagesEN.Labels.Register);
		cashierBtn = new JButton(ConstMessagesEN.Labels.InitAccount);
		receiveBtn = new JButton(ConstMessagesEN.Labels.ReceiveVisit);
		createOrderBtn = new JButton(ConstMessagesEN.Labels.CreateOrder);
		verifyOrderBtn = new JButton(ConstMessagesEN.Labels.VerifyOrder);
		sendOrderExecuteBtn = new JButton(
				ConstMessagesEN.Labels.SendOrderExecute);

		menuPanel.add(loginBtn);
		menuPanel.add(registerBtn);
		menuPanel.add(cashierBtn);
		menuPanel.add(receiveBtn);
		menuPanel.add(createOrderBtn);
		menuPanel.add(verifyOrderBtn);
		menuPanel.add(sendOrderExecuteBtn);
		
		add(menuPanel, BorderLayout.CENTER);
		
		JPanel statePanel = new JPanel();
		loginLbl = new JLabel();
		statePanel.add(loginLbl);
		
		add(statePanel, BorderLayout.SOUTH);
		
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public JButton getRegisterBtn() {
		return registerBtn;
	}

	public JButton getCashierBtn() {
		return cashierBtn;
	}

	public JButton getReceiveBtn() {
		return receiveBtn;
	}

	public JButton getCreateOrderBtn() {
		return createOrderBtn;
	}

	public JButton getVerifyOrderBtn() {
		return verifyOrderBtn;
	}

	public JButton getSendOrderExecuteBtn() {
		return sendOrderExecuteBtn;
	}
}
