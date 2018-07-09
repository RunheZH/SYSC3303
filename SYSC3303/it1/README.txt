Sysc 3303 Iteration#1     team 11
        Lau,James							
	Liu,Yan						
	Mohammed,Myles							
	Rachid,Osama		
	Yang,Hansong  

The goal of this iteration is to extend the client, error simulator, and server programs to support steady-state file transfer. 
For this part, assume that no errors occur.


Firstly, run the TFTPServer class, then the "waiting" can show up, secondly,run the TFTPSim class, finally run the TFTPClient 
class, then follow the instuction,1 for read and 2 for write, then input the filename,then select mode to run.

Description for each class:
TFTPClient: determine the request whether read or write, the selected file by filename. and whether mode to choose.
            Then print out the address and port of sendPacket,print out the message and its length. After the send,
            server can receive the message, then client send request again.
TFTPServer: receive the message in port 69, the receive packet can be put in new DatagramPacket,then send back.The run, quit,read,
            and write method are used to run, quit, print out length, data, and name, and print out length, data, filename, address
            and port. Thew receiveAndSendTFTP method is used to send back the received message after the instruston by cilent selected.

TFTPSim:This class just receive the packet from client and send to server, and receive packet from server and send back to client.

listener: It is used to scan the request of client.
   
