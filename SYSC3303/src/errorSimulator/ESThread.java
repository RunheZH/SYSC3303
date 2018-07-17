package errorSimulator;

import java.io.*;
import java.net.*;

public class ESThread extends Thread{
	
	private DatagramSocket sendReceiveSocket;	
	private DatagramPacket receiveServerPacket, receiveClientPacket, sendPacket;
	
	private InetAddress clientAddr; //serverAddr; for multi-pc
	private int clientPort, serverPort;
	
	public ESThread(DatagramPacket received) {
		
		byte[] data1 = new byte[1024];
		byte[] data2 = new byte[1024];
		
		receiveServerPacket = new DatagramPacket(data1, data1.length);
		receiveClientPacket = new DatagramPacket(data2, data2.length);
		
		clientAddr = received.getAddress();
		clientPort = received.getPort();
		//serverAddr = null;
		serverPort = -1;
		
		this.receiveClientPacket = received;
		
		try{
			sendReceiveSocket = new DatagramSocket();
		}catch (SocketException se){
			se.printStackTrace();
			System.exit(1);
		}
	
	}
	
	public void run() {
		
		receiveFromClient();
		
	}
	
	public byte[] Modify(DatagramPacket packet, RequestParser RP, UI ui) {
		int choice = ui.mainMenu();
		byte[] sendData = null;
		if(choice == 0) {
			sendData = new byte[packet.getLength()];
			for(int i = 0; i < packet.getLength();i++) {
				sendData[i] = packet.getData()[i];
			}
			//send(sendData, port);
		}else if(choice == 1) {
			int newOp = ui.askOpCode();
			sendData = new byte[packet.getLength()];
			for(int i = 0; i < packet.getLength();i++) {
				sendData[i] = packet.getData()[i];
			}
			sendData[1] = (byte) newOp;
			//send(sendData, port);		
		}
		return sendData;
	}
	
	public void send(byte[] data, InetAddress addr, int port) {
				
		sendPacket = new DatagramPacket(data, data.length,
					addr, port);
		
		try {
			sendReceiveSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		displaySend(sendPacket);	
	}
	
	public void receiveFromClient() {
		if(receiveClientPacket == null) {
			byte data[] = new byte[1024];
			receiveClientPacket = new DatagramPacket(data, data.length);
			try {
				System.out.println("ES: Waiting for Client...");
				sendReceiveSocket.receive(receiveClientPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}	
		RequestParser RP = new RequestParser();
		RP.parseRequest(receiveClientPacket.getData(), receiveClientPacket.getLength());
		UI myUI = new UI(RP);
		////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////
		if(serverPort == -1) {
			send(Modify(receiveClientPacket, RP, myUI),clientAddr, 69);
		}else {
			send(Modify(receiveClientPacket, RP, myUI),clientAddr, serverPort);
		}
		////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////
		receiveFromServer();
	}
	
	public void receiveFromServer() {
		try {
			System.out.println("ES: Waiting for server response...");
			sendReceiveSocket.receive(receiveServerPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if(serverPort == -1) {
			serverPort = receiveServerPacket.getPort();
			//serverAddr = receiveServerPacket.getAddress();
		}
		RequestParser RP = new RequestParser();
		RP.parseRequest(receiveServerPacket.getData(), receiveServerPacket.getLength());
		UI myUI = new UI(RP);
		send(Modify(receiveServerPacket, RP, myUI), clientAddr, clientPort);
		receiveClientPacket = null;
		receiveFromClient();
	}
	
	public void displaySend(DatagramPacket sendPacket) {
		System.out.println("ES : Sending packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
	}
	
}