package server;

import com.google.gson.Gson;

public class BruteForceHandler {
	public BruteForceHandler() { }
	public void handleRequest(String callType) {
		switch (callType) {
		case "add_user":
			System.out.println("add_user");
			break;
		case "check_class_list":
			System.out.print("check_class_list");
			break;
		default:
			break;
		}
	}
}
