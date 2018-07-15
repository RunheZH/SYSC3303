package ErrorSimulator;

import java.io.*;
import java.net.*;

public class ErrorSController{
	
	private DatagramSocket receiveSocket;
	
	private int errorSPort = 23;
	
	public ErrorSController(){
		try {
			
			receiveSocket = new DatagramSocket(errorSPort);
			
		}catch(SocketException se) {
			
			se.printStackTrace();
		    System.exit(1);
		    
		}
	}
	
	/*public void distribute(int ) {
		
	}*/


}
