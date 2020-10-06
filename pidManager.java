package ciscpack;

/*
 * 
 * Name: Shanna Benammar 
 * Class: CISC 3320
 * Assignment One
 * Summary: 
		 * An operating system’s pid manager is responsible for managing process identifiers. When a process is
		first created, it is assigned a unique pid by the pid manager. The pid is returned to the pid manager
		when the process completes execution, and the manager may later reassign this pid. Process identifiers
		must be unique; no two active processes may have the same pid.
		Use the following constants to identify the range of possible pid values:
		#define MIN_PID 300
		#define MAX_PID 5000
		You may use any data structure of your choice to represent the availability of process identifiers. One
		strategy is to adopt what Linux has done and use a bitmap in which a value of 0 at position i indicates
		that a process id of value i is available and a value of 1 indicates that the process id is currently in use.
		Implement the following API for obtaining and releasing a pid:
		• int allocate_map(void) – Creates and initializes a data structure for representing processes;
		returns -1 if unsuccessful and 1 if successful
		• int allocate_pid(void) – Allocates and returns a pid; returns -1 if if unable to allocate a
		pid (all processes are in use)
		• void release_pid(int_pid) – Releases a pid.
		NOTE: This programming problem will be modified later in Chapters 4 and 5


 */
import java.util.*;
import java.util.Random;
import java.io.PrintWriter;
import java.lang.Process;


public class pidManager{
	
	static boolean empty = true;
	static PrintWriter outFile;
	String message;
    static boolean status[];
    static Process processes[];
	final static int MIN_PID = 300;
	final static int MAX_PID = 5000;
	
	
	public static void main(String[] args)
	{
		
		
		try {
			
		   
			outFile = new PrintWriter("C:\\Users\\shanna\\eclipse-space\\Cisc3320\\src\\ciscpack\\output.txt");
			ProcessBuilder process = new ProcessBuilder("notepad.exe");
			
			if(allocate_map() == 1)
                outFile.println("Successful allocation.\n");
            else
                outFile.println("Failed allocation.\n");
			
			int pid1 = allocate_pid();
			int pid2 = allocate_pid();
			int pid3 = allocate_pid();
			if(pid1 == -1)
				outFile.println("No available PID");
			else {
				outFile.println("PID " + pid1 + " created.");
				Process process1 = process.start();
				processes [pid1] = process1;
			}
			
			
			if(pid2 == -1)
				outFile.println("No available PID");
			else {
				outFile.println("PID " + pid2 + " created.");
				Process process2 = process.start();
				processes [pid2] = process2;
			}
			
			
			if(pid3 == -1)
				outFile.println("No available PID");
			else {
				outFile.println("PID " + pid3 + " created.");
				Process process3 = process.start();
				processes [pid3] = process3;
			}
			
			outFile.println("\n--Before release--");
			outFile.println(pid1 + " status: " + status[pid1]);
			outFile.println(pid1 + " " + processes[pid1].isAlive());
			release_pid(pid1);
			outFile.println("\n--After release--");
			outFile.println(pid1 + " status: " + status[pid1]);
			outFile.println(pid1 + " " + processes[pid1].isAlive());
			
			outFile.println("\n--Before release--");
			outFile.println(pid2 + " status: " + status[pid2]);
			outFile.println(pid2 + " " + processes[pid2].isAlive());
			release_pid(pid2);
			outFile.println("\n--After release--\n");
			outFile.println(pid2 + " status: " + status[pid2]);
			outFile.println(pid2 + " " + processes[pid2].isAlive());
			
			outFile.println("\n--Before release--\n");
			outFile.println(pid3 + " status: " + status[pid3]);
			outFile.println(pid3 + " " + processes[pid3].isAlive());
			release_pid(pid3);
			outFile.println("\n--After release--\n");
			outFile.println(pid3 + " status: " + status[pid3]);
			outFile.println(pid3 + " " + processes[pid3].isAlive());
			
			outFile.close();
			
		}catch(Exception e){
			
			System.out.println("Exception caught in main.");
            e.printStackTrace();
		}
	}
	
	public static int allocate_map() {
		
		
		processes = new Process [5001];
		status = new boolean [5001];
		int index = 300;
		
		do {
			
			if(status[index] == true)
				empty = false;
				
			index ++;
				
			
			
		}while(empty == true && (index != 5001));
		
		if(empty)
			return 1;
		else
			return -1;
		
	}
	
	public static int allocate_pid() {
		
		int index = 300;
		boolean full = true;
		
		while (index <= 5001 && full == true) {
		      if(status[index] == false) 
				full = false;
			
			index ++;
		}
		
		if(!full) {
			int pid = 0;
			boolean processestatus = false;
			do {
				
				Random random = new Random();
				pid = random.nextInt(MAX_PID - MIN_PID +1) + MIN_PID;
				
				if(status[pid] == false)
					processestatus = true;
				
			}while(!processestatus);
			
		empty = false;
		status[pid] = true;
		return pid;
			
		}
		else {
			return -1;
		}
		
	}
	//Mark PID as free to use
	public static void release_pid(int pid) {
		
		if(!empty) {
			status[pid] = false;
			processes[pid].destroy();
			
			outFile.println("\n-------------\nThe PID " + pid + " is released\n-------------");
			int space = 0;

            for(int i = 300; i < 5001; i++)                              //After releasing pid it checks if the booleanArray is completely empty
            {
                if(status[i] == false )
                    space++;
            }
            if(space == (MAX_PID-MIN_PID+1) )                      //If numFreeSpace equal to 4701 the booleanArray is empty
                empty = true;
		}
		else {
			outFile.println("No pid to release, list is empty.");
		}
	}
}
