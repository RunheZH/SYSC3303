/*
 * UI class for error simulator
 * Allow User to modify the packet to create different error codes 
*/
package errorSimulator;

import java.util.Scanner;

import ES.ESListener;

public class UI {

	private Scanner scan;
	private String ec,tc; 
	//private byte packetChoice;
	private int errorChoice;
	private int transError;
	//private boolean status = false;
	private ESListener listener;
	
	public  UI() {
		scan = new Scanner(System.in);
	}

	 
	
	public void errorMainMenu() {
		
		System.out.println("----------Error Selection----------");
		System.out.println("    0. Normal Operation");
		System.out.println("    1. Transmission Error");
		System.out.println("    2. Error Codes (1-6)");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		ec = scan.next();
		
		if(ec.equals("0")) {
			errorChoice = 0;
		}else if (ec.equals("1")) {
			errorChoice = 1;
			transmissionError();
		}else if (ec.equals("2")) {
			errorChoice = 2;
			System.out.println("Error Code");
		}else if (ec.equals("quit")) {
			System.exit(0);
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
		
		switch (tc) {
			case "1": 
				transError = 1; 
				break;
			case "2": 
				transError = 2;
				break;
			case "3": 
				transError = 3;
				break;
			case "4": 
				transError = 4;
				errorMainMenu(); 
				break;
			case "quit": 
				System.exit(0);
				break;
			default: 
				System.out.println("Oops, something is wrong"); 
				break;
		}
	}
	
	/*public void packetSelection() {
		System.out.println("---------- Transmission Error ----------");
		System.out.println("    1. RRQ");
		System.out.println("    2. WRQ");
		System.out.println("    3. DATA");
		System.out.println("    4. ACK");
		System.out.println("    5. ERROR" );
		System.out.println("    6. Back to Transmission Menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		pc = scan.next();
		
		switch (pc) {
			case "1": 
				packetChoice = 1;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "2": 
				packetChoice = 2;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "3": 
				packetChoice = 3;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "4": 
				packetChoice = 4;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "5": 
				packetChoice = 5;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "6": 
				packetChoice = 6;
				listener.handleNetworkError(transError, packetChoice);
				break;
			case "quit": 
				listener.quit(); 
				break;
			default: 
				System.out.println("Oops, something is wrong"); 
				break;
		}
	}*/
	
	/*public byte getPacketChoice() {
		return packetChoice;
	}*/
	
	public int getErrorType() {
		return errorChoice;
	}
	
	public int getTransError() {
		return transError;
	}
}
