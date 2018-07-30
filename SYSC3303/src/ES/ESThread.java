package ES;

import java.io.IOException;
import java.net.*;

public class ESThread extends Thread{
	
	private DatagramPacket receivedPacket, sendPacket;
	private DatagramSocket receiveSendSocket;
	
	private int errorType;
	private int errorChoice;
	private byte errorPacket;
	private InetAddress clientAddress;
	private int clientPort;
	private InetAddress serverAddress;
	private int serverPort = 69;
	
	private int counter = 0;
	
	public ESThread(int errorType, int errorChoice, byte errorPacket, DatagramPacket received) {
		
		byte[] receivedData = new byte[1024];
//		byte[] sendData = new byte[1024];
		
		receivedPacket = new DatagramPacket(receivedData, receivedData.length);
//		sendPacket = new DatagramPacket(sendData, sendData.length);
		
		this.errorType = errorType;
		this.errorChoice = errorChoice;
		this.errorPacket = errorPacket;
		this.receivedPacket = received;

		System.out.println("Error Simulator: Packet received:");
		printPacketInfo(received);
		
		this.clientAddress = receivedPacket.getAddress();
		this.clientPort = receivedPacket.getPort();
		//temp
		this.serverAddress = receivedPacket.getAddress();
		
		System.out.println("Created a thread");

	}
	
	public void run() {
		System.out.println("Thread is running...");
		
		try {
			receiveSendSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		while(true) { // assume the file transfer has ended
			if(errorType == 0) normal();
			else if(errorType == 1) networkError();
			else if (errorType == 2) errorCode();
		}
		
	}
	
	public void normal() {
		
		System.out.println("Normal operation...");
		//if(receivedPacket.getPort() == serverPort) { // server side
			sendPacket = new DatagramPacket(receivedPacket.getData(), receivedPacket.getLength(), this.serverAddress, this.serverPort);
			
			try {
				
				receiveSendSocket.send(sendPacket);
				System.out.println("Error Simulator: Packet sent:");
				printPacketInfo(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
			receivePacketFromServer();
		//}
		//else { // client side
			sendPacket = new DatagramPacket(receivedPacket.getData(), receivedPacket.getLength(), this.clientAddress, this.clientPort);
			try {
				receiveSendSocket.send(sendPacket);
				System.out.println("Error Simulator: Packet sent:");
				printPacketInfo(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
			counter ++;
			
			receivePacketFromClient();
		//}
		
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
	
	public void receivePacketFromClient() {
		try {
			System.out.println("Receving a packet...");
			receiveSendSocket.receive(receivedPacket);
			System.out.println("Error Simulator: Packet received:");
			printPacketInfo(receivedPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void receivePacketFromServer() {
		try {
			System.out.println("Receving a packet...");
			receiveSendSocket.receive(receivedPacket);
			this.serverPort = receivedPacket.getPort();
			System.out.println("Error Simulator: Packet received:");
			printPacketInfo(receivedPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void printPacketInfo(DatagramPacket received){
		System.out.println("Address: " + received.getAddress());
		System.out.println("Port: " + received.getPort());
		int len = received.getLength();
		System.out.println("Length: " + len);
	}
}
