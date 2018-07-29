package ES;

public class ESThread implements Runnable{
	
	private int errorType;
	private int errorChoice;
	private byte errorPacket;
	
	public ESThread(int errorType, int errorChoice, byte errorPacket) {
		this.errorType = errorType;
		this.errorChoice = errorChoice;
		this.errorPacket = errorPacket;
		Thread t = new Thread();
		t.start();
	}
	
	public void run() {
		System.out.println("Thread is running...");
		if(errorType == 0) normal();
		else if(errorType == 1) networkError();
		else if (errorType == 2) errorCode();
		
	}
	
	public void normal() {
		
		System.out.println("Normal operation...");
		
	}
	
	public void networkError() {
		
		switch(errorChoice) {
			case 1: System.out.println("Modify RRQ"); break;
			case 2: System.out.println("Modify WRQ"); break;
			case 3: System.out.println("Modify DATA"); break;
			case 4: System.out.println("Modify ACK"); break;
			case 5: System.out.println("Modify ERROR"); break;
			default: System.out.println("Oops, something is wrong"); break;
		}
	}
	
	public void errorCode() {
		
		switch(errorChoice) {
		case 1: System.out.println("Modify RRQ"); break;
		case 2: System.out.println("Modify WRQ"); break;
		case 3: System.out.println("Modify DATA"); break;
		case 4: System.out.println("Modify ACK"); break;
		case 5: System.out.println("Modify ERROR"); break;
		default: System.out.println("Oops, something is wrong"); break;
	}
	}

}
