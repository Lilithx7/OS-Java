import java.util.*;

public class Priority {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        int[] at = new int[n];  // Arrival Time
        int[] bt = new int[n];  // Burst Time  
        int[] priority = new int[n]; // Priority (lower number = higher priority)
        int[] ct = new int[n];  // Completion Time
        boolean[] completed = new boolean[n];
        
        // Input process details
        for(int i = 0; i < n; i++) {
            System.out.println("\nProcess " + (i+1) + ":");
            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();
            System.out.print("Priority: ");
            priority[i] = sc.nextInt();
        }
        
        // Non-Preemptive Priority Scheduling
        int currentTime = 0;
        int completedProcesses = 0;
        
        System.out.println("\nGantt Chart:");
        
        while(completedProcesses < n) {
            int highestPriority = -1;
            int selectedProcess = -1;
            
            // Find process with highest priority that has arrived and not completed
            for(int i = 0; i < n; i++) {
                if(!completed[i] && at[i] <= currentTime) {
                    if(selectedProcess == -1 || priority[i] < highestPriority) {
                        highestPriority = priority[i];
                        selectedProcess = i;
                    }
                }
            }
            
            if(selectedProcess == -1) {
                // No process available, move time forward
                System.out.print(" IDLE ");
                currentTime++;
            } else {
                // Execute the selected process completely (non-preemptive)
                System.out.print(" P" + (selectedProcess+1) + " ");
                currentTime += bt[selectedProcess];
                ct[selectedProcess] = currentTime;
                completed[selectedProcess] = true;
                completedProcesses++;
            }
        }
        
        // Calculate and display results
        System.out.println("\n\nPID\tAT\tBT\tPriority\tCT\tTAT\tWT");
        float avgTAT = 0, avgWT = 0;
        
        for(int i = 0; i < n; i++) {
            int tat = ct[i] - at[i];  // Turnaround Time
            int wt = tat - bt[i];     // Waiting Time
            
            System.out.println("P" + (i+1) + "\t" + at[i] + "\t" + bt[i] + "\t" + 
                             priority[i] + "\t\t" + ct[i] + "\t" + tat + "\t" + wt);
            
            avgTAT += tat;
            avgWT += wt;
        }
        
        System.out.printf("\nAverage Turnaround Time: %.2f", avgTAT/n);
        System.out.printf("\nAverage Waiting Time: %.2f", avgWT/n);
        
        sc.close();
    }
}