
import java.io.*;
import java.util.ArrayList;

public class SlaveExecuteJobsAndWriteToMasterThread extends Thread {
	private PrintWriter writeToMaster;
	private ArrayList<String> jobs;
	private char optimizedJob;

	public SlaveExecuteJobsAndWriteToMasterThread(PrintWriter p, ArrayList<String> jobs, char optimizedJob) {
		this.writeToMaster = p;
		this.jobs = jobs;
		this.optimizedJob = optimizedJob;

	}

	@Override
	public void run() {
		try {
			// infinite loop to keep the thread alive cuz as long as the client sends jobs
			// you need to be able to execute them
			while (true) {
				boolean thereAreJobsToDo;
				
				synchronized (jobs) {
					thereAreJobsToDo = !jobs.isEmpty();
				}
				// if there is a job in the job list
				if (thereAreJobsToDo) {
					
					String thisJob;
					//get the next job and remove it from the jobs arraylist
					synchronized (jobs) {
						thisJob = jobs.get(0).toLowerCase();
						jobs.remove(0);
					}
					
					
					// set the job type
					char jobType = thisJob.charAt(1);
					// thread sleeps for 2 seconds if job type is the optimized job
					if (jobType == optimizedJob) {
						Thread.sleep(2000);
						// thread sleeps for 10 seconds if job type is the not optimized job
					} else {
						Thread.sleep(10000);
					}

					// print out to the console that this job is done executing
					System.out.println(thisJob + " is done executing");
					// write to the server that this job is done executing.. add at the end which
					// slave this is from (optimized job) because the server needs to know
					writeToMaster.println(thisJob + " is done executing" + optimizedJob);
	
				}

	
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
