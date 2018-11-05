package thread;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	//Solely responsible for one client
	//should listen
//	private BufferedReader br;
//	private PrintWriter pw;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ThreadHandler th;
	public ServerThread(Socket s, ThreadHandler th) {
		this.th = th;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	public void run() {
////		try {
////			th.broadcast();
////		} catch (IOException ioe) {
////			System.out.println("ioe: " + ioe.getMessage());
////		} catch (ClassNotFoundException cnfe) {
////			System.out.println("cnfe: " + cnfe.getMessage());
////		}
//	}
////	public void sendMessage(String line) {
//	public void sendMessage(ChatMessage cm) {
////		pw.println(line);
////		pw.flush();
//		try {
//			oos.writeObject(cm);
//			oos.flush();
//		} catch (IOException ioe) {
//			System.out.println("ioe in sendMessage: " + ioe.getMessage());
//		}
		
	}
}