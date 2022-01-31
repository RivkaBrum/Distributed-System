import java.net.*;
import java.io.*;

public class Master {
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.println("Enter: java Master <portNumber>");
			System.exit(1);
		}
		int portNumber = Integer.parseInt(args[0]);
		
		try ( // the server
				ServerSocket serverSocket = new ServerSocket(portNumber);
				// slave sockets and objects that allow you to read from
				// and write to these slaves
				Socket slaveA = serverSocket.accept();
				Socket slaveB = serverSocket.accept();
				BufferedReader readFromSlaveA = new BufferedReader(new InputStreamReader(slaveA.getInputStream()));
				BufferedReader readFromSlaveB = new BufferedReader(new InputStreamReader(slaveB.getInputStream()));
				PrintWriter writeToSlaveA = new PrintWriter(slaveA.getOutputStream(),true);
				PrintWriter writeToSlaveB = new PrintWriter(slaveB.getOutputStream(),true);
				
				// client socket and objects that enable you to
				// read from and write to these client sockets
				Socket clientSocket1 = serverSocket.accept();
				Socket clientSocket2 = serverSocket.accept();
				BufferedReader readFromClient1 = new BufferedReader(
						new InputStreamReader(clientSocket1.getInputStream()));
				BufferedReader readFromClient2 = new BufferedReader(
						new InputStreamReader(clientSocket2.getInputStream()));
				PrintWriter writeToClient1 = new PrintWriter(clientSocket1.getOutputStream(),true);
				PrintWriter writeToClient2 = new PrintWriter(clientSocket2.getOutputStream(),true);

				) {
			LoadAmountOnEachSlaveTracker loadTrackerObject = new LoadAmountOnEachSlaveTracker();
			//object to communicate 

			//thread that reads info from slaveA and can write to either client
			Thread readFromSlaveAThread = new MasterReadFromSlaveThread(readFromSlaveA, writeToClient1, writeToClient2, loadTrackerObject);
			// thread that reads from slaveB and can write to either client
			Thread readFromSlaveBThread = new MasterReadFromSlaveThread(readFromSlaveB, writeToClient1, writeToClient2, loadTrackerObject);
			// thread that reads from client 1 and can write to either slave
			Thread readFromClient1Thread = new MasterReadFromClientThread(readFromClient1,  writeToSlaveA, writeToSlaveB,
					loadTrackerObject);
			// thread that reads from client 2 and can write to either slave
			Thread readFromClient2Thread = new MasterReadFromClientThread(readFromClient2,  writeToSlaveA, writeToSlaveB,
					loadTrackerObject);

			readFromClient1Thread.start();
			readFromClient2Thread.start();
			readFromSlaveAThread.start();
			readFromSlaveBThread.start();
			try {
				readFromClient1Thread.join(); 
				readFromClient2Thread.join();
				readFromSlaveAThread.join();
				readFromSlaveBThread.join();
			} catch (Exception e) {
				System.out.println(e);

			}

		}catch (Exception e) {
			System.out.println(e);

		}
	}
}