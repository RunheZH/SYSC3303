package ErrorSimulator;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ErrorSController{
	
	//private DatagramSocket receiveSocket;
	//private DatagramPacket receivePacket;
	
	//private int errorSPort = 2000; //should be 23
	
	//private int threadCounter = 0;
	
	public ErrorSController(){}
	
	public void distribute(int errorCode) {
		
		switch(errorCode) {
		
			case 0:
				System.out.println("error code: 0");
				break;
			case 1:
				System.out.println("error code: 1");
				break;
			case 2:
				System.out.println("error code: 2");
				break;
			case 3:
				System.out.println("error code: 3");
				break;
			case 4:
				System.out.println("error code: 4");
				break;
			case 5:
				System.out.println("error code: 5");
				break;
			case 6:
				System.out.println("error code: 6");
				break;
			case 7:
				System.out.println("error code: 7");
				break;
			case 8:
				System.out.println("error code: 8 [Normal]");
				ESThreadNormal normal = new ESThreadNormal();
				break;
			case 9:
				System.out.println("error code: 9");
				System.out.println("Quiting...");
				break;
			default:
				System.out.println("Something is wrong...");
			
		}
		//threadCounter++;
		
	}


}
