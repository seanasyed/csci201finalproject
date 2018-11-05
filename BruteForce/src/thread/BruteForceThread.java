package thread;

public class BruteForceThread extends Thread {
	private ThreadHandler th;
	public BruteForceThread(int port) {
		th = new ThreadHandler(port);
	}
	public void run() {
		th.run();
	}
}
