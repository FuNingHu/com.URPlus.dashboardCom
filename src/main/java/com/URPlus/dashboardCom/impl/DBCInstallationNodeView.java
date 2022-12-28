package com.URPlus.dashboardCom.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

public class DBCInstallationNodeView implements SwingInstallationNodeView<DBCInstallationNodeContribution>{

	private final Style style;
	private JTextField commandField = new JTextField();
	private JLabel returnLabel = new JLabel("aucune message actuallement!");
	private JButton sendButton = new JButton("Send");

	
	public DBCInstallationNodeView(Style style) {
		// TODO Auto-generated constructor stub
		this.style = style;
	}
	@Override
	public void buildUI(JPanel panel, DBCInstallationNodeContribution contribution) {
		// TODO Auto-generated method stub
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(createSpaceing(0, 100));
		panel.add(createTextField(commandField, sendButton, contribution));
		panel.add(createVerticalSpacing());
		panel.add(createReplyBox(returnLabel));
	}
	
	public void setReturnLabel(String str) {
		returnLabel.setText(str);
	}
	public void setCommandField(String str) {
		commandField.setText(str);
	}
	private Box createDescription(String description) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel label = new JLabel(description);
		box.add(label);
		return box;
	}
	private Box createReplyBox(JLabel jLable) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		box.add(new JLabel("Reply: "));
		box.add(jLable);
		return box;
	}
	private Box createTextField(final JTextField textField, JButton button, final DBCInstallationNodeContribution contribution) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel desc = new JLabel("Input command: ");
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		textField.setPreferredSize(new Dimension(300,30));
		textField.setMaximumSize(textField.getPreferredSize());
		textField.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				KeyboardTextInput keyboardInput = contribution.getKeyboardForString();
				keyboardInput.show(textField, contribution.getCallbackForString());
			}
		});
		
		button.setPreferredSize(new Dimension(180,30));
		button.setMaximumSize(button.getPreferredSize());
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("send button clicked!");
				contribution.onSendButtonClick();
			}
		});
		
		box.add(desc);
		box.add(textField);
		box.add(createHorizontalSpacing());
		box.add(button);
		return box;
		
	}
	
	private Component createHorizontalSpacing() {
		return Box.createRigidArea(new Dimension(style.getHorizontalSpacing(),0));
	}
	private Component createVerticalSpacing() {
		return Box.createRigidArea(new Dimension(0,style.getHorizontalSpacing()));
	}
	private Component createSpaceing(int horizontal, int vertical) {
		return Box.createRigidArea(new Dimension(horizontal, vertical));
	}

}
