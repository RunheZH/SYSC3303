package ES;

import java.io.*;
import java.net.*;

public class ESListener extends Thread{
	
	private DatagramPacket receivedPacket;
	private DatagramSocket receiveSendSocket;
	private int errorType,errorChoice;
	private int packetChoice, blockChoice;
	private boolean lisRunning = false;
	
	public ESListener() {
		try {
			System.out.println("created a socket (port 23)");
			receiveSendSocket = new DatagramSocket(23);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		errorType = -1;

	}

	public void run() {
		System.out.println("Error Simulator: Listener now online.");
		while(true) {
			listen();
		}
	}
	
	public void listen() {
		byte[] data = new byte[1024];
		receivedPacket = new DatagramPacket(data, data.length);
		try {
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			System.out.println("received a packet");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Thread normalThread = new ESThread(errorType, errorChoice, packetChoice, receivedPacket);
		normalThread.start();
	}

	public void setError(int error) {
		setErrorType(error);
	}
	
	public void setError(int errorType, int errorChoice, int packetChoice) {
		setErrorType(errorType);
		setErrorChoice(errorChoice);
		setPacketChoice(packetChoice);
		this.blockChoice = -1;
	}
	
	public void setError(int errorType, int errorChoice, int packetChoice, int blockChoice) {
		setError(errorType, errorChoice, packetChoice);
		setBlockChoice(blockChoice);
	}
	
	public void setErrorType(int errorType) {
		this.errorType = errorType;
		System.out.println("Mode set to " + errorType);
	}
	
	public void setErrorChoice(int errorChoice) {
		this.errorChoice = errorChoice;
		System.out.println("Error set to " + errorType);
	}
	
	public void setPacketChoice(int packetChoice) {
		this.packetChoice = packetChoice;
		System.out.println("Packet set to " + packetChoice);
	}
	
	public void setBlockChoice(int blockChoice) {
		this.blockChoice = blockChoice;
		System.out.println("Block number set to " + packetChoice);
	}
	
	public void setOnline() {
		if(lisRunning == false) {
			lisRunning = true;
			start();
		}	
	}
	
	public void quit() {
		System.out.println("Quiting Error Simulator...");
	}
	
}
