import java.io.BufferedReader;
import java.io.PrintWriter;

public class MasterReadFromClientThread extends Thread {
	private BufferedReader readFromClient;
	private PrintWriter writeToSlaveA;
	private PrintWriter writeToSlaveB;
	private LoadAmountOnEachSlaveTracker loadTracker;

	public MasterReadFromClientThread(BufferedReader r, PrintWriter s1, PrintWriter s2,
			LoadAmountOnEachSlaveTracker loadTracker) {
		this.readFromClient = r;
		this.writeToSlaveA = s1;
		this.writeToSlaveB = s2;
		this.loadTracker = loadTracker;

	}

	@Override
	public void run() {
		try {
			String response;

			while ((response = readFromClient.readLine()) != null) {
				System.out.println(response);
				char jobType = response.toLowerCase().charAt(1);
				char whereToSend;
				// see how many jobs slave and and b currently have

				synchronized (loadTracker) {
					// determine which slave to send this job to dont want to overload one
					if (jobType == 'a') {
						if ((loadTracker.getCurrentLoadOnSlaveA() + 2) <= (loadTracker.getCurrentLoadOnSlaveB() + 10)) {
							whereToSend = 'a';
						} else {
							whereToSend = 'b';
						}
					} else {
						if ((loadTracker.getCurrentLoadOnSlaveB() + 2) <= (loadTracker.getCurrentLoadOnSlaveA() + 10)) {
							whereToSend = 'b';
						} else {
							whereToSend = 'a';
						}
					}

				}

				if (whereToSend == 'a') {
					// send this to slave a
					writeToSlaveA.println(response.toUpperCase());
					// print what we're doing
					System.out.println("Sending job " + response.toUpperCase() + " to slaveA");
					// add 2 seconds to the slave A load
					synchronized (loadTracker) {
						loadTracker.addToSlaveALoad(jobType == 'a' ? 2 : 10);

					}
				}

				else {

					// send this to slave B
					writeToSlaveB.println(response.toUpperCase());
					// print out what we're doing
					System.out.println("Sending job " + response.toUpperCase() + " to slaveB");
					// add 10 seconds to the slave B load
					synchronized (loadTracker) {
						loadTracker.addToSlaveBLoad(jobType == 'b' ? 2 : 10);
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
