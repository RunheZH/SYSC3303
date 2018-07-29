package ES;

import java.net.*;

public class ESThread implements Runnable{
	
	private DatagramPacket receivedPacket, sendPacket;
	private DatagramSocket receiveAndSendSocket;
	
	private int errorType;
	private int errorChoice;
	private byte errorPacket;
	
	
	public ESThread(int errorType, int errorChoice, byte errorPacket, DatagramPacket received) {
		
		byte[] receivedData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		receivedPacket = new DatagramPacket(receivedData, receivedData.length);
		sendPacket = new DatagramPacket(sendData, sendData.length);
		
		this.errorType = errorType;
		this.errorChoice = errorChoice;
		this.errorPacket = errorPacket;
		this.receivedPacket = received;
		
		Thread t = new Thread();
		t.start();
	}
	
	public void run() {
		System.out.println("Thread is running...");
		
		try {
			receiveAndSendSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		if(errorType == 0) normal();
		else if(errorType == 1) networkError();
		else if (errorType == 2) errorCode();
		
	}
	
	public void normal() {
		
		System.out.println("Normal operation...");
		
	}
	
	public void networkError() {
		
		switch(errorChoice) {
			case 1: System.out.println("Lose a packet"); break;
			case 2: System.out.println("Delay a packet"); break;
			case 3: System.out.println("Duplicate a packet"); break;
			default: System.out.println("Oops, something is wrong"); break;
		}
		
		switch(errorPacket) {
			case 1: System.out.println("Modify RRQ"); break;
			case 2: System.out.println("Modify WRQ"); break;
			case 3: System.out.println("Modify DATA"); break;
			case 4: System.out.println("Modify ACK"); break;
			case 5: System.out.println("Modify ERROR"); break;
			default: System.out.println("Oops, something is wrong"); break;
		}
	}
	
	public void errorCode() {
		
		switch(errorChoice) {
			case 1: System.out.println("File not found"); break;
			case 2: System.out.println("Access violation"); break;
			case 3: System.out.println("Disk full or allocation exceeded"); break;
			case 4: System.out.println("Illegal TFTP operation"); break;
			case 5: System.out.println("Unknown transfer ID"); break;
			case 6: System.out.println("File already exists"); break;
			default: System.out.println("Oops, something is wrong"); break;
		}
		
		switch(errorPacket) {
			case 1: System.out.println("Modify RRQ"); break;
			case 2: System.out.println("Modify WRQ"); break;
			case 3: System.out.println("Modify DATA"); break;
			case 4: System.out.println("Modify ACK"); break;
			case 5: System.out.println("Modify ERROR"); break;
			default: System.out.println("Oops, something is wrong"); break;
		}
	}

}
