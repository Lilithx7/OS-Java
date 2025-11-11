import java.util.*;

class Process {
    int processId, arrivalTime, burstTime;
    int completionTime, turnaroundTime, waitingTime;

    Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFSScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Burst Time: ");
            int bt = sc.nextInt();
            processes.add(new Process(i + 1, at, bt));
        }

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0, totalTAT = 0, totalWT = 0;

        System.out.println("\n\nFCFS Scheduling Results:");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        System.out.println("-------------------------------------------");

        for (Process p : processes) {
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;

            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = p.completionTime;

            totalTAT += p.turnaroundTime;
            totalWT += p.waitingTime;

            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n", p.processId, p.arrivalTime, p.burstTime,
                    p.completionTime, p.turnaroundTime, p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n",
                (double) totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n",
                (double) totalWT / n);

        sc.close();
    }
}