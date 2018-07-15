package ErrorSimulator;

import java.util.*;
import java.io.*;

public class ErrorSUI {
	
	private int userOption;
	private int operationChoice;
	private ErrorType error;
	
	public ErrorSUI() {
		error = new ErrorType();
	}
	
	public void ErrorMenu() throws IOException{
		
		Scanner sc = new Scanner(System.in);
		error.printErrorMenu();
	
		boolean valid = false;
		
		while(!valid) {
			userOption = sc.nextInt();
			
			switch(userOption) {
			
			case 0:
				System.out.println("Not defined, see error message (if any).");
				valid = true;
				break;
			case 1:
				System.out.println("File not found.");
				valid = true;
				break;
			case 2:
				System.out.println("Access Violation.");
				valid = true;
				break;
			case 3:
				System.out.println("Disk full or allocation exceeded.");
				valid = true;
				break;
			case 4:
				System.out.println("Illegal TFTP Operation.");
				IllegalOperation();
				valid = true;
				break;
			case 5:
				System.out.println("Unknown transfer ID.");
				valid = true;
				break;
			case 6:
				System.out.println("File already exists.");
				valid = true;
				break;
			case 7:
				System.out.println("No such user.");
				valid = true;
				break;
			default:
				System.out.println("User input a invalid number");
				break;
			}
			
		}
	
	}
	public void IllegalOperation() throws IOException{
		Scanner sc = new Scanner(System.in);
		error.printIllegalOperation();
		operationChoice = sc.nextInt(); 
	}
}