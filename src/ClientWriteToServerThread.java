import java.io.*;

public class ClientWriteToServerThread extends Thread {
	private BufferedReader keyboard;
	private PrintWriter writeToServer;
	private int CLIENTNUM;
//client enters job type 
	public ClientWriteToServerThread(BufferedReader keyboard, PrintWriter writeToServer, int CLIENTNUM) {
		this.keyboard = keyboard;
		this.writeToServer = writeToServer;
		this.CLIENTNUM = CLIENTNUM; 
	}

	@Override
	public void run() {
		try {
			
			//job number will be incremented for each job
			int jobNum = 0;
			char jobType;

			while (true) { // infinite loop
				System.out.println("Enter q to quit ");
				System.out.println("Enter a/b");
				//have the user input the job type
				
				jobType = keyboard.readLine().toLowerCase().charAt(0);
				while(jobType != 'a' && jobType != 'b' && jobType != 'q') {
					System.out.println("Enter a valid choice! - Must be A, B OR Q!!!!!!!!!");
					jobType = keyboard.readLine().toLowerCase().charAt(0);
				}
				//if they chose to quit
				if (jobType == 'q') {
		
					break;
				}

				// increment job number
				jobNum++;
				
				//write to the master a string consisting of the client number, job type and job number
				writeToServer.println(CLIENTNUM + "" + jobType + "" + jobNum);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
