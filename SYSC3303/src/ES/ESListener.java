package ES;

import java.io.IOException;
import java.net.*;

public class ESListener {
	
	private DatagramPacket receivedPacket;
	private DatagramSocket receiveSendSocket;
	
	public ESListener() {
		try {
			receiveSendSocket = new DatagramSocket(23);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void handleNormal() {
		
		byte data[] = new byte[1024];
		
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread aThread = new ESThread(0, 0, (byte)0);
		
	}
	
	public void handleNetworkError(int transError, byte choice) {
		
		byte data[] = new byte[1024];
		
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread aThread = new ESThread(1, transError, choice);
		
	}
	
	public void handleErrorCode(int errorCode, byte choice) {
		
		byte data[] = new byte[1024];
		
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread aThread = new ESThread(2, errorCode, choice);
		
	}
	
}
