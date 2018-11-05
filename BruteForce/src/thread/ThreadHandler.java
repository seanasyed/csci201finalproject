package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ThreadHandler {
	private Vector<ServerThread> serverThreads;
	private int port;
	public ThreadHandler (int port) {
		this.port = port;
		//bind to a port (reasons for ioexception)
		//1. no permission
		//2. another app is running on the port
		//3. port out of the range
	}
	public void run() {
		ServerSocket ss = null;
		try {
			System.out.println("Trying to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<ServerThread>();
			while (true) {
				Socket s = ss.accept(); //waits for a client to connect
				System.out.println("Connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this); //only knows about server & 1 client / chatroom knows about all the clients
				serverThreads.add(st);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if (ss != null) {
					ss.close();
				}
			} catch (IOException ioe) {
				System.out.println("ioe closing stuff: " + ioe.getMessage());
			}
		}
	}
}
