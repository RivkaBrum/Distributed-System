import java.io.*;

public class ClientReadFromServerThread extends Thread {
	private BufferedReader readFromServer;
	// as server completes the job writes back job was completed 
	public ClientReadFromServerThread(BufferedReader readFromServer) {
		this.readFromServer = readFromServer;

	}

	@Override
	public void run() {
		try {
			String response;
			while ((response = readFromServer.readLine()) != null) {
				//printing from server what came in 
				System.out.println("\n"+response);
				
				//let the user know that they can still make another selection
				System.out.println("Enter q to quit ");
				System.out.println("Enter a/b");

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
