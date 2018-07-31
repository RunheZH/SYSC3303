package ES;

import java.io.IOException;
import java.net.*;

public class ESThread extends Thread{

	private DatagramPacket receivedPacket, sendPacket;
	private DatagramSocket receiveSendSocket;

	private int errorType;
	private int errorChoice;
	private int errorPacket;
	private int blockChoice;
	private int delayChoice;
	private InetAddress clientAddress;
	private int clientPort;
	private InetAddress serverAddress = null;
	private int serverPort = 69;
	private RequestParser rp;
	private int ID;

	public ESThread(int errorType, int errorChoice, int errorPacket, int blockChoice, int delayChoice, DatagramPacket received) {
		//		byte[] sendData = new byte[1024];

		//		sendPacket = new DatagramPacket(sendData, sendData.length);

		this.errorType = errorType;
		this.errorChoice = errorChoice;
		this.errorPacket = errorPacket;
		this.blockChoice = blockChoice;
		this.delayChoice = delayChoice;
		this.receivedPacket = received;

		System.out.println("Error Simulator: Packet received:");
		printPacketInfo(received);

		this.clientAddress = receivedPacket.getAddress();
		this.clientPort = receivedPacket.getPort();
		//temp
		try {
			receiveSendSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		rp = new RequestParser();
		ID = receiveSendSocket.getLocalPort();

		System.out.println("Created a thread");

	}

	public void run() {
		System.out.println("Thread is running...");
		tryError(receivedPacket);

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

	public void sendPacket(DatagramPacket sendPacket) {

		try {
			receiveSendSocket.send(sendPacket);
			System.out.println("Error Simulator: Packet sent:");
			printPacketInfo(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	} 

	public void receive() {
		try {
			receiveSendSocket.setSoTimeout(10000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		try {
			System.out.println("Receving a packet...");
			receiveSendSocket.receive(receivedPacket);

			if(!ifClient(receivedPacket)) {
				if(serverPort == -1) 
					this.serverPort = receivedPacket.getPort();
			}

			System.out.println("Error Simulator: Packet received:");
			printPacketInfo(receivedPacket);
		}catch(SocketTimeoutException e1) {
			System.out.println(ID + ": Timeout, closing thread.");
			return;
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		tryError(receivedPacket);
	}

	private void tryError(DatagramPacket receivedPacket){
		rp.parseRequest(receivedPacket.getData(), receivedPacket.getLength());

		if(ifError(receivedPacket)) {
			if(errorType == 1) {
				makeTransmissionError(receivedPacket);
			}else if(errorType == 2) {
				////////////////////
				///////////////////
			}
		}else {
			transferPacket(receivedPacket);
			receive();
		}
	}

	private boolean ifError(DatagramPacket receivedPacket) {
		if(errorType == 0) return false;
		if(errorPacket == rp.getType()) {
			if(errorPacket != 3 && errorPacket != 4) {
				return true;
			}else {
				if(blockChoice == rp.getBlockNum()) {
					return true;
				}
			}
		}
		return false;
	}

	private void makeTransmissionError(DatagramPacket receivedPacket) {
		if(errorChoice == 1) {}
		else if(errorChoice == 2) {
			try {
				Thread.sleep(delayChoice);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			transferPacket(receivedPacket);
		}else if(errorChoice == 3) {
			transferPacket(receivedPacket);
			transferPacket(receivedPacket);
		}else {
			System.out.println("invalid error choice");
		}
	}

	public void transferPacket(DatagramPacket receivedPacket) {
		if(ifClient(receivedPacket)) {
			sendPacket = new DatagramPacket(receivedPacket.getData(), receivedPacket.getLength(), this.serverAddress, this.serverPort);
			sendPacket(sendPacket);
		}else {
			sendPacket = new DatagramPacket(receivedPacket.getData(), receivedPacket.getLength(), this.clientAddress, this.clientPort);
			sendPacket(sendPacket);
		}
	}

	public boolean ifClient(DatagramPacket receivedPacket) {
		if(receivedPacket.getPort() == clientPort && receivedPacket.getAddress().equals(clientAddress)) return true;
		return false;
	}

	public void printPacketInfo(DatagramPacket received){
		System.out.println("Address: " + received.getAddress());
		System.out.println("Port: " + received.getPort());
		int len = received.getLength();
		System.out.println("Length: " + len);
	}
}
