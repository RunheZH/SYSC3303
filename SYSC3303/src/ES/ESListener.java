package ES;

import java.io.IOException;
import java.net.*;

public class ESListener {
	
	private DatagramPacket receivedPacket;
	private DatagramSocket receiveSendSocket;
	
	public ESListener() {
		
		try {
			System.out.println("created a socket (port 23)");
			receiveSendSocket = new DatagramSocket(23);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void handleNormal() {
		
		byte[] data = new byte[1024];
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread normalThread = new ESThread(0, 0, (byte)0, receivedPacket, receiveSendSocket);
		normalThread.start();
		
	}
	
	public void handleNetworkError(int transError, byte choice) {
		
		byte[] data = new byte[1024];
		
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread networkErrThread = new ESThread(1, transError, choice, receivedPacket, receiveSendSocket);
		networkErrThread.start();
		
	}
	
	public void handleErrorCode(int errorCode, byte choice) {
		
		byte[] data = new byte[1024];
		
		receivedPacket = new DatagramPacket(data, data.length);
		
		try {
			
			System.out.println("Error Simulator: waiting for a packet...");
			receiveSendSocket.receive(receivedPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ESThread errCodeThread = new ESThread(2, errorCode, choice, receivedPacket, receiveSendSocket);
		errCodeThread.start();
		
	}
	
	public void quit() {
		System.out.println("Quiting Error Simulator...");
		receiveSendSocket.close();
	}
	
}
