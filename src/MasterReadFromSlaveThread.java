	import java.io.*;

public class MasterReadFromSlaveThread extends Thread {
	private BufferedReader readFromSlave;
	private PrintWriter writeToClient1;
	private PrintWriter writeToClient2;
	LoadAmountOnEachSlaveTracker loadTracker;

	public MasterReadFromSlaveThread(BufferedReader r, PrintWriter c1, PrintWriter c2,
			LoadAmountOnEachSlaveTracker loadTracker) {
		this.readFromSlave = r;
		this.writeToClient1 = c1;
		this.writeToClient2 = c2;
		this.loadTracker = loadTracker;

	}

	@Override
	public void run() {
		try {
			String response;
			while ((response = readFromSlave.readLine()) != null) {

				// read which slave its from and then remove this character from the string cuz
				// it isn't necessary
				char slaveItsFrom = response.charAt(response.length() - 1);
				response = removeLastChar(response);

				// print out what's happening
				System.out.println("Slave " + slaveItsFrom + " said " + response);

				// extract the job type from the string
				char jobType = response.charAt(1);

				// if it came from slave a, calculate how much of a load we're removing from
				// slave A
				if (slaveItsFrom == 'a') {
					// if job type was a, a 2 second load is removed from slave a
					if (jobType == 'a') {
						synchronized (loadTracker) {
							loadTracker.removeFromSlaveALoad(2);
						}
						// but if job type was b, a 10 second load is removed from this slave
					} else if (jobType == 'b') {
						synchronized (loadTracker) {
							loadTracker.removeFromSlaveALoad(10);
						}
					}
					// if it came from slave b, calculate how much of a load we're removing from
					// slave b
				} else if (slaveItsFrom == 'b') {
					// if the job type was a then a 10 second load was removed from its load
					if (jobType == 'a') {
						synchronized (loadTracker) {
							loadTracker.removeFromSlaveBLoad(10);
						}
						// if the job type was b, a 2 second load was removed from its load
					} else if (jobType == 'b') {
						synchronized (loadTracker) {
							loadTracker.removeFromSlaveBLoad(2);
						}
					}
				}
				// extract from the string which client sent this job
				char whichClientThisJobIsFrom = response.charAt(0);

				// tell the client the job is finished
				if (whichClientThisJobIsFrom == '1') {
					writeToClient1.println(response.toUpperCase());
				} else {
					writeToClient2.println(response.toUpperCase());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}
}
