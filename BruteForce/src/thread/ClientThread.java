package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public ClientThread(String hostname, int port) {
		Socket s = null;
		try {
			s = new Socket(hostname, port);
			System.out.println("Connected");
//			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			pw = new PrintWriter(s.getOutputStream());
			
			//change the order of stream instantiation
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (oos != null) {
					oos.close();
				}
				
				if (s != null) {
					s.close();
				}
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
	}
	public void run() {

	}
	public static void main (String [] args) {
		
	}
}
