package ES;

import java.util.Scanner;

public class ErrorSimulator {
	
	private Scanner scan;
	private String ec,tc,pc, bc, dc;  
	private ESListener listener;
	private int errorType,errorChoice;
	private int packetChoice, blockChoice, delayChoice;

	
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
			errorType = Integer.valueOf(ec);
			switch(errorType) {
				case 0: 
					listener.setErrorType(errorType); 
					listener.confirmChange();
					break;
				case 1: 			 
					transmissionError();
					break;
				case 2: 
					//////////////////
					//////////////////
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
			errorChoice = Integer.valueOf(tc);
			if(errorChoice < 0 || errorChoice > 4) {
				System.out.println("Invalid input, please try again."); 
				transmissionError();
			}else {		
				if (errorChoice == 4) return;
				listener.setErrorChoice(errorChoice);
				packetSelection(); 
			}
		}catch(NumberFormatException e) {
				System.out.println("Invalid input, please try again.");
				transmissionError();
		}
		
		
	}
	public void delaySelection() {
		System.out.println("---------- Delay Selection ----------");
		System.out.println("    Please enter delay time (ms)...");;
		System.out.println("    Enter -1 to go back to Error Menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		dc = scan.next();
		
		if(dc.equals("quit")) {
			stop();
			return;
		}
		try {
			delayChoice = Integer.valueOf(dc);
			if (delayChoice < -1) {
				System.out.println("Invalid input, please try again."); 
				delaySelection();
			}else if (delayChoice == -1){
				return;
			}else {
				listener.setDelayChoice(delayChoice);
				listener.confirmChange();
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input, please try again.");
			delaySelection();
		}
		
	}
	
	public void packetSelection() {
		System.out.println("---------- Packet Selection ----------");
		System.out.println("    1. RRQ");
		System.out.println("    2. WRQ");
		System.out.println("    3. DATA");
		System.out.println("    4. ACK");
		System.out.println("    5. ERROR" );
		System.out.println("    6. Back to Error Menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		
		pc = scan.next();
		
		if(pc.equals("quit")) {
			stop();
			return;
		}
		try {
			packetChoice = Integer.valueOf(pc);
			if (packetChoice < 1 || packetChoice > 6) {
				System.out.println("Invalid input, please try again."); 
				packetSelection();
			}else {
				if(packetChoice == 3 || packetChoice == 4) {
					blockSelection();
				}else if(packetChoice == 6){
					return;
				}else {
					if(errorChoice == 2) {
						delaySelection();
					}else {
						listener.setPacketChoice(packetChoice);
						listener.confirmChange();
					}
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input, please try again.");
			packetSelection();
		}
	
	}
	
	public void blockSelection() {
		System.out.println("---------- Block Selection ----------");
		System.out.println("    Please enter block number...");;
		System.out.println("    Enter -1 to go back to Error Menu");
		System.out.println(">>>>>>>> input quit to exit this program");
		bc = scan.next();
		
		if(bc.equals("quit")) {
			stop();
			return;
		}
		try {
			blockChoice = Integer.valueOf(bc);
			if (blockChoice < -1) {
				System.out.println("Invalid input, please try again."); 
				blockSelection();
			}else if (blockChoice == -1){
				return;
			}else {
				if(errorChoice == 2) {
					delaySelection();
				}else {
					listener.setPacketChoice(packetChoice);
					listener.confirmChange();
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input, please try again.");
			blockSelection();
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
