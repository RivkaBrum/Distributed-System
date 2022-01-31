import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Slave {
	public static void main(String args[]) {
				
		if(args.length!=3) {
			System.err.println("Enter: java Slave <IPAddress> <portNumber> <a/b>");
			System.exit(1);
		}
		
		String ipAddress = args[0];
		int portNumber = Integer.parseInt(args[1]);
		//which slave this is
		char optimizedJob = args[2].toLowerCase().charAt(0);

		
		try (Socket socket = new Socket(ipAddress, portNumber);
				BufferedReader readFromMaster = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter writeToMaster = new PrintWriter(socket.getOutputStream(), true);

		) {
			//arrayList to hold the jobs it receives from the master in order
			ArrayList<String> jobs = new ArrayList<String>();
			//thread to read in jobs from the master
			Thread readFromMasterThread = new SlaveReadFromMasterThread(readFromMaster, jobs);
			//thread to execute jobs and report to the master that it finished the job
			//does the job 
			Thread doJobsAndWriteToMaster = new SlaveExecuteJobsAndWriteToMasterThread(writeToMaster, jobs, optimizedJob);
			
			readFromMasterThread.start();
			doJobsAndWriteToMaster.start();
			try {
				readFromMasterThread.join();
				doJobsAndWriteToMaster.join();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);

		}
	}
}
