package com.URPlus.dashboardCom.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RobotTester implements AutoCloseable{

	private Socket clientSocket;
	private Scanner inputReader;
	private PrintWriter outWriter;
	private int incounter;
	private int outcounter;
	public RobotTester(String host, int port) throws UnknownHostException, IOException {
        clientSocket = new Socket(host, port);
        inputReader = new Scanner(clientSocket.getInputStream());
        outWriter = new PrintWriter(clientSocket.getOutputStream());
    }
	public String nextInput() {
        String mess = inputReader.nextLine();
        System.out.println("< " + (++incounter) + ": " + mess);
        return mess;
    }
	
	public void writeCommand(String command) {
        System.out.println("> " + (++outcounter) + ": " + command);
        outWriter.print(command);
        outWriter.print('\n');
        outWriter.flush();
    }
	
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		if (inputReader != null) {
            inputReader.close();
            inputReader = null;
        }
        if (outWriter != null) {
            outWriter.close();
            outWriter = null;
        }
        if (clientSocket != null) {
            clientSocket.close();
            clientSocket = null;
        }
	}

}
