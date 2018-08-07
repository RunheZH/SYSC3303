/*
 *SYSC3303 Project G11 Client
 */
package client;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private	String mode, request, fig;
	private	String fileName;
	private File check, space;

	private	boolean running = true;
	private String ip, destination, loc, location = "src\\client\\files";
	private InetAddress ipAddress;

	public Client (){}

	public String getMode (){
		return this.mode;
	}

	public String getRequest (){
		return this.request;
	}

	public String getFileName (){
		return this.fileName;
	}

	public String getFig (){
		return this.fig;
	}

	public String getLocation () {
		return this.location;
	}

	public InetAddress getIpAddress () {
		return this.ipAddress;
	}


	/*
	 * Method for the UI
	 * First ask user input for mode, then read/write request and finally output mode 
	 */
	public void menu () throws UnknownHostException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to client V2 <Enter quit to quit anytime :(>");
		System.out.println("Please select your destination");
		System.out.println("1. Local host");
		System.out.println("2. I have my own destination");
		ip = sc.next();
		if (!ip.equals("quit")) {
			ipAddress = InetAddress.getLocalHost();
			if (ip.equals("2")) {
				System.out.println("Please enter the destination IP address");
				destination = sc.next();
				ipAddress = InetAddress.getByName(destination);

			}
			System.out.println("Please select your mode:");
			System.out.println("1. Normal  <Client, Server>");
			System.out.println("2. Test  <Client, Error Simulator, Server>");
			mode = sc.next();
			if (!mode.equals("quit")) {
				System.out.println("Please select your request");
				System.out.println("1. RRQ <Read Request>");
				System.out.println("2. WRQ <Write Request>");
				request = sc.next();
				if (!request.equals("quit")) {
					System.out.println("Please select your location");
					System.out.println("1. src\\client\\files\\");
					System.out.println("2. I have my own space :p");
					loc = sc.next();
					if (!loc.equals("quit")) {		
						if (loc.equals("2")) {
							System.out.println("Please enter your location. Don't forget to enter \\");
							location = sc.next();
						}
						System.out.println("Please enter your file Name ");
						fileName = sc.next();

						//check read request
						if (request.equals("1")) {
							if (!this.checkDisk(fileName)	) {
								System.out.println("Disk full can't read.");
								System.out.println	("Please delete file and come :)");
							}


							while (this.checkFile(fileName)) {
								System.out.println("File already exist error.");
								System.out.println	("Please enter a new file Name");
								fileName = sc.next();
							}
						}
						//check write request
						else if (request.equals("2")) {
							while(true) {
								if (fileName.equals("quit")) {
									sc.close();
									return;
								}
								if(!this.checkFile(fileName)) {
									System.out.println("File not found error.");
									System.out.println("Please re-enter your file Name ");
									fileName = sc.next();
									continue;
								}
								if(!this.permission(fileName)) {
									System.out.println("Please enter a new file Name ");
									fileName = sc.next();
									continue;
								}
								break;
							}
						}else{
							System.out.println("Error");
							sc.close();
						}

						if(!fileName.equals("quit")) {
							System.out.println("Please enter your mode for data");
							System.out.println("1. Verbose");
							System.out.println("2. Quiet");
							fig = sc.next();		

						}else {
							sc.close();
						}
					}else {
						sc.close();
					}
				}else {
					sc.close();
				}
			}else {
				sc.close();
			}
		}else {
			sc.close();
		}
	}


	//file not found
	public boolean checkFile (String fileName) {
		check = new File (location + "\\" + fileName);
		if (check.exists()) {
			System.out.println("File exist.");
			return true;
		}
		return false;
	}

	//access violation
	public boolean permission (String fileName) {
		try {
			FileInputStream permit = new FileInputStream(new File (location + "\\" + fileName));
			byte [] fileBuffer = new byte[512];	
			permit.read(fileBuffer);
			permit.close();
		}catch(IOException e) {
			// Access denied
			if(e.getMessage().contains("Access is denied")) {
				System.out.println("ERROR: Access Violation.");

			}else {
				System.out.print("IO Exception: likely:");
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	//check disk full
	public boolean checkDisk (String fileName) {
		space = new File (location + "\\" + fileName);
		if (space.getUsableSpace() > 0) {
			System.out.println("Disk available space: " +space.getUsableSpace());
			return true;
		}
		return false;
	}

	/*
	 * Open Normal/Test mode depends on the user input in UI, if user enter quit end the program
	 */
	public void start (Sender s) throws IOException{

		if (mode.equals("quit") || request.equals("quit") || fileName.equals("quit") || fig.equals("quit")) {
			running = false;
			System.out.println("Thank your for using our program. Goodbye!");
			s.Close();
			//t.close();	
		}else {
			if (mode.equals("1")){
				s.start(this, 69);
			}else if(mode.equals("2")){
				s.start(this, 23);
			}else {
				System.out.println("Invalid mode input, please try again.");
			}
		}	
	}

	/*
	 * Client Starts
	 */
	public static void main(String[] args) throws IOException {
		Client c = new Client();
		Sender n = new Sender (c);
		while(c.running) {
			c.menu();
			c.start(n);	
		}			
	}
}
