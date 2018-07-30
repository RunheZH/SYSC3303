package ES;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ErrorSimulator {
	
	private Scanner scan;
	private String ec,tc,pc;  
	private boolean lisRunning = false;
	private ESListener listener;

	
	public ErrorSimulator() {
		
		scan = new Scanner(System.in);
		listener = new ESListener();
	}

	
	public void errorMainMenu() {
		
		System.out.println("----------Error Selection----------");
		System.out.println("    0. Normal Operation");
		System.out.println("    1. Transmission Error");
		System.out.println("    2. Error Codes (1-6)");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		ec = scan.next();
		if(ec.equals("quit")) {
			stop();
			return;
		}
		try {
			int errorType = Integer.valueOf(ec);
			switch(errorType) {
				case 0: 
					if(lisRunning == false) {
						lisRunning = true;
						listener.start();
					}
					listener.setErrorType(errorType); 
					break;
				case 1: 
					listener.setErrorType(errorType); 
					transmissionError();
					break;
				case 2: 
					listener.setErrorType(errorType); 
					System.out.println("Error Code selected");
					break;
				default: 
					System.out.println("Invalid input, please try again.");
					break;
			}
		}catch(NumberFormatException e) {
			System.out.println("Invalid input, please try again.");
		}
	}
	
	public void transmissionError(){
		System.out.println("---------- Transmission Error ----------");
		System.out.println("    1. Lose a packet");
		System.out.println("    2. Delay a packet");
		System.out.println("    3. Duplicate a packet");
		System.out.println("    4. Back to Error main menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		tc = scan.next();
		if(tc.equals("quit")) {
			stop();
			return;
		}
		try {
			int errorChoice = Integer.valueOf(tc);
			switch (tc) {
				case "1": 
					listener.setErrorChoice(errorChoice); 
					packetSelection(); 
					break;
				case "2": 
					listener.setErrorChoice(errorChoice); 
					packetSelection(); 
					break;
				case "3": 
					listener.setErrorChoice(errorChoice); 
					packetSelection(); 
					break;
				case "4": 
					errorMainMenu(); 
					break;
				default: 
					System.out.println("Invalid input, please try again."); 
					break;
			}
		}catch(NumberFormatException e) {
				System.out.println("Invalid input, please try again.");
		}
		
		
	}
	
	public void packetSelection() {
		System.out.println("---------- Transmission Error ----------");
		System.out.println("    1. RRQ");
		System.out.println("    2. WRQ");
		System.out.println("    3. DATA");
		System.out.println("    4. ACK");
		System.out.println("    5. ERROR" );
		System.out.println("    6. Back to Transmission Menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		pc = scan.next();
		
		if(pc.equals("quit")) {
			stop();
			return;
		}
		try {
			byte packetChoice = Byte.valueOf(pc);
			switch (pc) {
				case "1": 
					listener.setPacketChoice(packetChoice); 
					//listener.handleNetworkError(transError, packetChoice);
					break;
				case "2": 
					listener.setPacketChoice(packetChoice); 
				//	listener.handleNetworkError(transError, packetChoice);
					break;
				case "3": 
					listener.setPacketChoice(packetChoice); 
				//	listener.handleNetworkError(transError, packetChoice);
					break;
				case "4": 
					listener.setPacketChoice(packetChoice); 
				//	listener.handleNetworkError(transError, packetChoice);
					break;
				case "5": 
					listener.setPacketChoice(packetChoice); 
				//	listener.handleNetworkError(transError, packetChoice);
					break;
				case "6": 
					transmissionError();
					break;
				default: 
					System.out.println("Invalid input, please try again."); 
					break;
			}
		}catch(NumberFormatException e) {
			System.out.println("Invalid input, please try again.");
		}
	
	}
	
	
	public void stop() {
		listener.quit();
	}
	
	public static void main(String[] args) {
		
		ErrorSimulator es = new ErrorSimulator();
		while(true) {
			System.out.println("in main while loop");
			es.errorMainMenu();
		}
	}

}