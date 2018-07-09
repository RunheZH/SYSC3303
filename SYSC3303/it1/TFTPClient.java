
//TFTPClient.java
//This class is the client side for a very simple assignment based on TFTP on
//UDP/IP. The client uses one port and sends a read or write request and gets 
//the appropriate response from the server.  No actual file transfer takes place.   

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TFTPClient {

private DatagramPacket sendPacket, receivePacket;
private DatagramSocket sendReceiveSocket;
public int request;
public String filename;
public int mode;
//we can run in normal (send directly to server) or test
//(send to simulator) mode
public static enum Mode { NORMAL, TEST};

public TFTPClient()
{
 try {
    // Construct a datagram socket and bind it to any available
    // port on the local host machine. This socket will be used to
    // send and receive UDP Datagram packets.
    sendReceiveSocket = new DatagramSocket();
 } catch (SocketException se) {   // Can't create the socket.
    se.printStackTrace();
    System.exit(1);
 }
}

public void send(String filename, int mode)
{
	 byte[] fn = new byte[512]; // message we send
	       
	 int j, len, sendPort = 0;
	 
	
	 if (mode == 1){ 
	    sendPort = 69;
	 }
	 else if(mode == 2){
	    sendPort = 23;
	 }
	 else if(mode == 3){
		 sendPort = 23;
	 }
	 else{
		 System.out.println("Quite mode");
	 }
	fn = filename.getBytes();
   
   try {
      sendPacket = new DatagramPacket(fn, fn.length,
                          InetAddress.getLocalHost(), sendPort);
   } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(1);
   }

   System.out.println("Client: sending packet .");
   System.out.println("To host: " + sendPacket.getAddress());
   System.out.println("Destination host port: " + sendPacket.getPort());
   len = sendPacket.getLength();
   System.out.println("Length: " + len);
   System.out.println("Containing: ");
   for (j=0;j<len;j++) {
       System.out.println("byte " + j + " " + fn[j]);
   }
   
   // Form a String from the byte array, and print the string.
   String sending = new String(fn,0,len);
   System.out.println(sending);

   // Send the datagram packet to the server via the send/receive socket.

   try {
      sendReceiveSocket.send(sendPacket);
   } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
   }

   System.out.println("Client: Packet sent to server.");

   // Construct a DatagramPacket for receiving packets up
   // to 100 bytes long (the length of the byte array).
}

public void receive(){
	byte[] msg = new byte[4];
	msg[0] = 1;
	try {
	   sendPacket = new DatagramPacket(msg, msg.length,
	                          InetAddress.getLocalHost(), 69);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	try {
	      sendReceiveSocket.send(sendPacket);
	   } catch (IOException e) {
	      e.printStackTrace();
	      System.exit(1);
	 }
	
	byte[] data = new byte[512];
   receivePacket = new DatagramPacket(data, data.length);

   System.out.println("Client: Waiting for packet from server.");
   try {
      // Block until a datagram is received via sendReceiveSocket.
      sendReceiveSocket.receive(receivePacket);
   } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
   }

   // Process the received datagram.
   System.out.println("Client: Packet received:");
   System.out.println("From host: " + receivePacket.getAddress());
   System.out.println("Host port: " + receivePacket.getPort());
   int len = receivePacket.getLength();
   System.out.println("Length: " + len);
   System.out.println("Containing: ");
   for (int j=0;j<len;j++) {
       System.out.println("byte " + j + " " + data[j]);
   }
   
   System.out.println();
}

public void ui(){
	while(true){
		System.out.println("What is your request? (1. read or 2.write)");
		System.out.println("Enter "+"-1"+" to quit.");
		Scanner sc = new Scanner(System.in);
		request = sc.nextInt();
		if(request == -1){
			break;
		}
		else if(request != 1 && request != 2){
			System.out.println("Sorry, invalid input.");
		}
		System.out.println("What is the filename?");
		filename = sc.next();
		System.out.println("What is the mode? (1.normal 2.test 3.verbose 4.quite)");
		mode = sc.nextInt();
		if(mode < 1 && mode > 4){
			System.out.println("Sorry, invalid input.");
		}else{
			if(request == 1){
				receive();
			}
			else{
				send(filename, mode);
				receive();
			}
		}
	}
}

public static void main(String args[])
{
	TFTPClient c = new TFTPClient();
	c.ui();
}
}
