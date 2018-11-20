/**
 * Group 14
 * 
 * CP: Aya Shimizu (ashimizu@usc.edu)
 * Yiyang Hou (yiyangh@usc.edu)
 * Sean Syed (seansyed@usc.edu)
 * Eric Duguay (eduguay@usc.edu)
 * Xing Gao (gaoxing@usc.edu)
 * Sangjun Lee (sangjun@usc.edu)
 * 
 */

package server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint (value="/ss")
public class ServerSocket {
	private static Vector<Session> sessionVector = new Vector<>();
	//add connection
	@OnOpen
	public void open(Session session) {
		System.out.println("connecting...");
		sessionVector.add(session);
	}
	
	@OnMessage
	public void message(String message, Session session) {
		System.out.println(message);
		//having error in one client doesn't mean it will do in other clients
		try {
			//NEED TO GET ARRAYLIST OF SECTIONS
			ArrayList<String> sections = new ArrayList<>();
			sections.add("30303R");
			sections.add("29909R");
			sections.add("30245R");
			//TODO: JUST ADD ALL THE COURSE CODES FROM THE DATABASE
			
			//Filtering the data with the keyword			
			String json = new Gson().toJson(sections);
			session.getBasicRemote().sendText(json);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
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