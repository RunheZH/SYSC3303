
import java.util.Scanner;

public class listener extends Thread{
	Scanner sc;
	TFTPServer s;
	public static final String[] op = {"RRQ", "WRQ", "DATA", "ACK"};
	
	public listener(TFTPServer s){
		this.s = s;
		sc = new Scanner(System.in);
	}
	
	@Override
	public void run(){
		while(true){
			if(sc.hasNext()){
				String t = sc.next();
				if (t.contains("quit")){
					s.quit();
					return;
				}
				sc.reset();
			}
		}
	}
	
}
