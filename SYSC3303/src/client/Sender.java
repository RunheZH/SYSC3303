package client;

import java.io.*;
import java.net.*;

import server.FileHandler;
import server.RequestParser;

public class Sender {
	private Client c;
	private DatagramSocket sendReceiveSocket;
	private DatagramPacket receivePacket, sendPacket;
	private FileHandler fileHandler;
	private RequestParser RP;
	private int blockNumber = 0, port;

	public Sender(Client c){
		this.c = c;
		RP = new RequestParser();
		try {
			sendReceiveSocket = new DatagramSocket();
		} catch (SocketException se) {   
			se.printStackTrace();
			System.exit(1);
		}
	}

	public DatagramPacket getReceivePacket() {
		return this.receivePacket;
	}

	public DatagramPacket getSendPacket() {
		return this.sendPacket;
	}

	public void Receiver () throws IOException{
		System.out.println("Client: waiting a packet...");
		byte data[] = new byte[1024];
		receivePacket = new DatagramPacket(data, data.length);

		try {
			sendReceiveSocket.receive(receivePacket);

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		PrintReceiver(receivePacket);
		ReceiveHandler(receivePacket);

	}

	public void ReceiveHandler (DatagramPacket receivePacket) throws IOException{
		RP.parseRequest(receivePacket.getData(), receivePacket.getLength());

		switch (RP.getType()) {
		case 3:	DATA(receivePacket);
		break;
		case 4:	ACK(receivePacket);
		break;
		case 5:	ERR();
		break;
		}
	}

	public void DATA (DatagramPacket receivePacket) throws IOException{
		System.out.println("Received Data packet.");
		byte [] send = new byte [4];
		int blockNum = RP.getBlockNum();
		int length = receivePacket.getLength();

		if (blockNum == blockNumber){
			fileHandler.writeFile(RP.getFileData());
			if (length == 516){
				blockNumber++;			
				send[0] = 0;
				send[1] = 4;
				send[2] = (byte)(blockNumber/256);
				send[3] = (byte)(blockNumber%256);
				SendPacket (send);
				Receiver();
			}
			else {
				fileHandler.close();
			}
		}	
		else {
			System.out.println("Error, block number don't match");
		}
	}


	public void ACK (DatagramPacket receivePacket) throws IOException{
		System.out.println("Received ACK packet.");
		byte [] send; 
		int blockNum = RP.getBlockNum();

		if (blockNum == blockNumber){
			byte[] fileData = fileHandler.readFile();
			int length = fileData.length;
			send = new byte [4+ length];
			send[0] = 0;
			send[1] = 3;
			send[2] = (byte)(blockNumber/256);
			send[3] = (byte)(blockNumber%256);

			for (int i = 0; i < fileData.length; i++){
				send[4+i] = fileData[i];
			}
			SendPacket(send);

			if (fileData.length == 512){
				blockNumber++;
				Receiver();
			}	
			else {
				fileHandler.close();
			}
		}	
		else{
			System.out.println("Error");
		}	
	}

	public void ERR (){
		System.out.println("Received error packet.");
	}

	public void SendPacket(byte [] packet){
		//		System.out.println("Client: sending a packet...");
		try {
			sendPacket = new DatagramPacket(packet, packet.length,
					InetAddress.getLocalHost(), port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}	

		//		Send packet
		try {
			// displaySend(sendPacket);
			sendReceiveSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		PrintSender(sendPacket);
	}

	public void  RequestHandler(String request, String fileName) throws IOException{
		byte [] send = null;
		byte [] length = fileName.getBytes();

		send = new byte [3 +length.length];
		send[0] = 0;
		if(request.equals("1")) {
			send[1] = 1;
			fileHandler = new FileHandler();
			fileHandler.prepareWrite(fileName);
			blockNumber = 1;
			System.out.println("Preparing Read request");
		}else {
			send[1] = 2;
			fileHandler = new FileHandler();
			fileHandler.readFile(fileName);
			blockNumber = 0;
			System.out.println("Preparing Write request");
		}

		for (int i = 0; i < length.length; i++){
			send[2+i] = length[i];
		}

		send[2+length.length] = 0;

		System.out.println("printing out byte array");
		for (int i = 0; i < send.length; i++) {
			System.out.println(send[i]);
		}

		SendPacket (send);

	}


	public void PrintReceiver (DatagramPacket receivePacket){
		Verbose v = new Verbose();
		Quiet q = new Quiet();

		if (c.getFig().equals("1")) {
			v.PrintReceiverV(receivePacket);
		}
		else {
			q.PrintReceiverQ(receivePacket);
		}	
	}


	public void PrintSender (DatagramPacket sendPacket) {
		Verbose v = new Verbose();
		Quiet q = new Quiet();

		if (c.getFig().equals("1")) {
			v.PrintSender(sendPacket);
		}
		else {
			q.PrintSenderQ(sendPacket);
		}
	}

	public void Close (){
		sendReceiveSocket.close();
	}

	public void start (Client c, Sender s, int portNum) throws IOException{
		System.out.println("Normal mode selected");
		this.port = portNum;
		s.RequestHandler(c.getRequest(), c.getFileName());
	}

}
