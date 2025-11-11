import java.util.*;

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n], bt = new int[n], rt = new int[n], ct = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("AT & BT for P" + (i + 1) + ": ");
            at[i] = sc.nextInt();
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }

        int time = 0, done = 0;
        System.out.println("\nGantt Chart:");
        while (done < n) {
            int idx = -1, min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++)
                if (at[i] <= time && rt[i] > 0 && rt[i] < min) { min = rt[i]; idx = i; }

            if (idx == -1) { System.out.print(" IDLE "); time++; continue; }
            System.out.print(" P" + (idx + 1) + " ");
            rt[idx]--; time++;
            if (rt[idx] == 0) { done++; ct[idx] = time; }
        }

        float avgTAT = 0, avgWT = 0;
        System.out.println("\n\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            int tat = ct[i] - at[i], wt = tat - bt[i];
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat + "\t" + wt);
            avgTAT += tat; avgWT += wt;
        }

        System.out.printf("\nAverage TAT: %.2f\nAverage WT: %.2f", avgTAT / n, avgWT / n);
        sc.close();
    }
}
