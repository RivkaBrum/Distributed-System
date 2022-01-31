# Distributed-System
This was a project done for operating systems. This project mimics a distributed system of computers.

There are client programs that send jobs to the master. The master can send each job to either slave A or slave B. Each slave is optimized for a specific job type but can perform either. The slave sleeps to symbolize that a job is being executed (will sleep for longer when executing a job it isn't optimized for). Based on what the job load is on each slave, the master program determines which slave it makes the most sense to a job to. It then sends the job to the slave who performs the job by sleep and messages back to the master that the job is done who in return tells the client that the job is done.
