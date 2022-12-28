package com.URPlus.dashboardCom.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputCallback;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputFactory;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

public class DBCInstallationNodeContribution implements InstallationNodeContribution{

	private final DataModel model;
	private final DBCInstallationNodeView view;
	private final InstallationAPIProvider apiProvider;
	private final KeyboardInputFactory keyboardInputFactory;

	private Timer uiTimer;
	private final String TCP_IP = "127.0.0.1";
	private final int TCP_PORT = 29999;
	
	private static final String COMMAND_KEY = "command";
	private static final String COMMAND_DEFAULT = "";
	private int incounter;
	private int outcounter;
	
	
	public DBCInstallationNodeContribution(InstallationAPIProvider apiProvider, DataModel model,
			DBCInstallationNodeView view){
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
		this.apiProvider = apiProvider;
		this.keyboardInputFactory = apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInputFactory();
		this.incounter = 0;
		this.outcounter = 0;
	}
	
	public KeyboardTextInput getKeyboardForString(){
		KeyboardTextInput keyboard = keyboardInputFactory.createStringKeyboardInput();
		keyboard.setInitialValue(model.get(COMMAND_KEY, COMMAND_DEFAULT));
		return keyboard;
	}
	public KeyboardInputCallback<String> getCallbackForString(){
		return new KeyboardInputCallback<String>() {

			@Override
			public void onOk(String value) {
				// TODO Auto-generated method stub
				System.out.println("Type in new command line: "+ value);
				view.setCommandField(value);
				model.set(COMMAND_KEY, value);
			}
		};
	}
	public void onSendButtonClick() {
		System.out.println("Command length: "+model.get(COMMAND_KEY, COMMAND_DEFAULT).length());
		sendCommand(model.get(COMMAND_KEY, COMMAND_DEFAULT));
	}

	public void sendCommand(final String command) {
		Thread appThread = new Thread() {
			public void run() {
				try {
					RobotTester robot = new RobotTester(TCP_IP, TCP_PORT);
					System.out.println("Connected to robot.");
					robot.nextInput();				//read and print robot's welcome message
					robot.writeCommand(command);	//send command
					String resp = robot.nextInput(); //read result
					view.setReturnLabel(resp);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		appThread.start();
	}

	@Override
	public void openView() {
		// TODO Auto-generated method stub
		view.setCommandField(model.get(COMMAND_KEY, COMMAND_DEFAULT));
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		if(uiTimer!= null) {
			uiTimer.cancel();
		}
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
