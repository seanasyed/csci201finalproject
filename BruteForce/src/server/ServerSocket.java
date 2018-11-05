package server;
import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint (value="/ss")
public class ServerSocket {
	private static Vector<Session> sessionVector = new Vector<>();
	//add connection
	@OnOpen
	public void open(Session session) {
		sessionVector.add(session);
	}
	
	@OnMessage
	public void message(String message, Session session) {
		System.out.println(message);
		//having error in one client doesn't mean it will do in other clients
		for (Session s: sessionVector) {
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
			
		}
	}
	
	//remove from vector
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnected");
		sessionVector.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		
	}
 }