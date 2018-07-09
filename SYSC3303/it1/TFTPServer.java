// TFTPServer.java
// This class is the server side of a simple TFTP server based on
// UDP/IP. The server receives a read or write packet from a client and
// sends back the appropriate response without any actual file transfer.
// One socket (69) is used to receive (it stays open) and another for each response. 

import java.io.*;
import java.net.*;
import java.util.*;

public class TFTPServer extends Thread{

   // types of requests we can receive
   public static enum Request { READ, WRITE, ERROR};
   // responses for valid requests
   public static final byte[] readResp = {0,3,0,1};
   public static final byte[] writeResp = {0,4,0,0};
   
   // UDP datagram packets and sockets used to send / receive
   private DatagramPacket sendPacket, receivePacket;
   private DatagramSocket receiveSocket, sendSocket;
   
   boolean readReq;
   private int numberOfThreads;
   
   public TFTPServer()
   {
      try {
         // Construct a datagram socket and bind it to port 69
         // on the local host machine. This socket will be used to
         // receive UDP Datagram packets.
    	  numberOfThreads = 0;
         receiveSocket = new DatagramSocket(69);
      } catch (SocketException se) {
         se.printStackTrace();
         System.exit(1);
      }
   }
  
   public TFTPServer(DatagramPacket received) {
	   receivePacket = received;
	   int readOrWrite = received.getData()[1];
	   byte[] response = new byte[4];
	   
	   if(readOrWrite == 1){
		   readReq = true;
		   response = readResp;
		   sendPacket = new DatagramPacket(response, response.length, received.getAddress(), received.getPort());
	   } else if (readOrWrite == 2){
		   readReq = false;
		   response = writeResp;
		   sendPacket = new DatagramPacket(response, response.length, received.getAddress(), received.getPort());
	   }
	   
       System.out.println("Server: Sending packet:");
       int len = response.length;
       System.out.println("Length: " + len);
       System.out.println("Containing: ");
       for (int j=0;j<len;j++) {
          System.out.println("byte " + j + " " + response[j]);
       }
	   
	   try {
		   sendSocket = new DatagramSocket();
	   } catch (SocketException e){
		   e.printStackTrace();
		   System.exit(1);
	   }
   }
   
   @Override
   public void run(){
	   if(readReq){
		   read();
	   } else {
		   write();
	   }
	   sendSocket.close();
   }
   
   public void quit(){
	   receiveSocket.close();
	   System.out.println("Finishing threads...");
	   while(numberOfThreads > 0){
		   try{
			   wait();
		   } catch (InterruptedException e) {
			e.printStackTrace();
		}
	   }
	   System.out.println("Closing");
	   System.exit(0);
   }
   
   public void read(){
	   
	   BufferedInputStream in;
	   int len = receivePacket.getLength();
	   String filename = "";
	   int j;
	   byte[] data = receivePacket.getData();
	   for(j=2;j<len;j++) {
           if (data[j] == 0) break;
       }
	   filename = new String(data,2,j-2);
	   
	   sendPacket = new DatagramPacket(readResp, readResp.length,
               receivePacket.getAddress(), receivePacket.getPort());
	   try {
           sendSocket.send(sendPacket);
        } catch (IOException e) {
           e.printStackTrace();
           System.exit(1);
        }
       
       System.out.println("Server: packet sent using port " + sendSocket.getLocalPort());
       System.out.println();
       numberOfThreads--;
   }
   
   public void write(){
	   
	   int len = receivePacket.getLength();
	   String filename = null;
	   byte[] data = receivePacket.getData();
	   int j;
	   for(j=2;j<len;j++) {
           if (data[j] == 0) break;
       }
	   filename = new String(data,2,j-2);
	   
	   sendPacket = new DatagramPacket(writeResp, writeResp.length,
               receivePacket.getAddress(), receivePacket.getPort());
	   try{
		   sendSocket.send(sendPacket);
	   } catch (IOException e) {
           e.printStackTrace();
           System.exit(1);
        }
       
       System.out.println("Server: packet sent using port " + sendSocket.getLocalPort());
       System.out.println();
       numberOfThreads--;
   }

   public void receiveAndSendTFTP() throws Exception
   {

      byte[] data,
             response = new byte[4];
      
      Request req; // READ, WRITE or ERROR
      
      String filename, mode;
      int len, j=0, k=0;

      for(;;) { // loop forever
         // Construct a DatagramPacket for receiving packets up
         // to 100 bytes long (the length of the byte array).
         
         data = new byte[512];
         receivePacket = new DatagramPacket(data, data.length);

         System.out.println("Server: Waiting for packet.");
         // Block until a datagram packet is received from receiveSocket.
         try {
            receiveSocket.receive(receivePacket);
         } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
         }

         // Process the received datagram.
         numberOfThreads++;
         System.out.println("Server: Packet received:");
         System.out.println("From host: " + receivePacket.getAddress());
         System.out.println("Host port: " + receivePacket.getPort());
         len = receivePacket.getLength();
         System.out.println("Length: " + len);
         System.out.println("Containing: " );
         
         // print the bytes
         for (j=0;j<len;j++) {
            System.out.println("byte " + j + " " + data[j]);
         }

         // Form a String from the byte array.
         String received = new String(data,0,len);
         System.out.println(received);

         // If it's a read, send back DATA (03) block 1
         // If it's a write, send back ACK (04) block 0
         // Otherwise, ignore it
         if (data[1]==1) req = Request.READ; // could be read
         else req = Request.WRITE; // could be write

             // search for next all 0 byte
             for(j=2;j<len;j++) {
                 if (data[j] == 0) break;
            }

            filename = new String(data,2,j-2);
          
             // search for next all 0 byte
             for(k=j+1;k<len;k++) { 
                 if (data[k] == 0) break;
            }
            mode = new String(data,j,k-j-1);
         
         

         
         // Create a response.
         if (req==Request.READ) { // for Read it's 0301
            response = readResp;
         } else if (req==Request.WRITE) { // for Write it's 0400
            response = writeResp;
         } 
         
         new TFTPServer(receivePacket).start();

      } // end of loop
   }

   public static void main(String args[]) throws Exception
   {
      TFTPServer c = new TFTPServer();
       listener l = new listener(c);
      c.receiveAndSendTFTP();
   }
}
