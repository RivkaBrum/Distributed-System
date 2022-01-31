import java.io.*;
import java.util.ArrayList;

public class SlaveReadFromMasterThread extends Thread {
	private BufferedReader readFromMaster;
	private ArrayList<String> jobs;

	public SlaveReadFromMasterThread(BufferedReader r, ArrayList<String> jobs) {
		this.readFromMaster = r;
		this.jobs = jobs;

	}

	@Override
	public void run() {
		try {

			String response;
			// infinite loop so this slave can keep on receiving jobs from the server
			while (true) {
				response = readFromMaster.readLine();
				// add this job to the jobs arrayList
				synchronized (jobs) {
					jobs.add(response);
				}
				// print out what's happening
				System.out.println("got job " + response);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
