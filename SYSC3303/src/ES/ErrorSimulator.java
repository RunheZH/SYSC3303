package ES;

import java.util.Scanner;

public class ErrorSimulator {
	
	private Scanner scan;
	private String ec,tc,pc;  
	private byte packetChoice;
	private int ErrorCodeChoice;
	private int transError;
	private boolean status = false;
	
	public ErrorSimulator() {
		scan = new Scanner(System.in);
	}

	
	
	public void errorMainMenu() {
		
		System.out.println("----------Error Selection----------");
		System.out.println("    0. Normal Operation");
		System.out.println("    1. Transmission Error");
		System.out.println("    2. Error Codes (1-7)");
		System.out.println(">>>>>>>> pc quit to exit this program");
		
		ec = scan.next();
		
		if(ec == "0") {
			ESListener listener = new ESListener();
			listener.handleNormal();
			
		}else if (ec == "1") {
			transmissionError();
			
		}else if (ec == "2") {
			
		}else if (ec == "quit") {
			System.exit(1);
			
		}
		
	}
	
	public void transmissionError(){
		System.out.println("---------- Transmission Error ----------");
		System.out.println("    1. Lose a packet");
		System.out.println("    2. Delay a packet");
		System.out.println("    3. Duplicate a packet");
		System.out.println("    4. Back to Error main menu");
		System.out.println(">>>>>>>> pc quit to exit this program");
		
		tc = scan.next();
		
		if(tc == "1") {
			transError = 1;
			packetSelection();
			
		}else if(tc == "2") {
			transError = 2;
			packetSelection();
			
		}else if(tc == "3") {
			transError = 3;
			packetSelection();
			
		}else if(tc == "4") {
			errorMainMenu();
			
		}else if(tc == "quit") {
			System.exit(1);
			
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
		System.out.println(">>>>>>>> pc quit to exit this program");
		
		pc = scan.next();
		
		if(pc == "1") {
			packetChoice = 1;
			ESListener listener = new ESListener();
			listener.handleNetworkError(transError, packetChoice);
			
		}else if(pc == "2") {
			packetChoice = 2;
			ESListener listener = new ESListener();
			listener.handleNetworkError(transError, packetChoice);
					;
		}else if(pc == "3") {
			packetChoice = 3;
			ESListener listener = new ESListener();
			listener.handleNetworkError(transError, packetChoice);
			
		}else if(pc == "4") {
			packetChoice = 4;
			ESListener listener = new ESListener();
			listener.handleNetworkError(transError, packetChoice);
			
		}else if(pc == "5") {
			packetChoice = 5;
			ESListener listener = new ESListener();
			listener.handleNetworkError(transError, packetChoice);
			
		}else if(pc == "6") {
			transmissionError();
			
		}else if(pc == "quit") {
			System.exit(1);
		
		}
  
	}
	
	public byte getPacketChoice() {
		return packetChoice;
	}
	
	public int getTransError() {
		return transError;
	}
	
	public static void main(String[] args) {
		ErrorSimulator es = new ErrorSimulator();
		es.errorMainMenu();
		
		
		
	}

}
