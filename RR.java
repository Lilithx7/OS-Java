import java.util.*;

public class RR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Processes: "); int n = sc.nextInt();
        System.out.print("Time Quantum: "); int tq = sc.nextInt();

        int[] at = new int[n], bt = new int[n], rt = new int[n], ct = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " AT BT: ");
            at[i] = sc.nextInt(); bt[i] = rt[i] = sc.nextInt();
        }

        Queue<Integer> q = new LinkedList<>();
        int time = 0, done = 0;
        System.out.println("\nGantt Chart:");

        while (done < n) {
            for (int i = 0; i < n; i++)
                if (at[i] <= time && rt[i] > 0 && !q.contains(i)) q.add(i);

            if (q.isEmpty()) { System.out.print(" IDLE "); time++; continue; }

            int p = q.poll();
            System.out.print(" P" + (p + 1) + " ");
            int exec = Math.min(tq, rt[p]);
            rt[p] -= exec; time += exec;

            for (int i = 0; i < n; i++)
                if (at[i] <= time && rt[i] > 0 && !q.contains(i) && i != p) q.add(i);

            if (rt[p] > 0) q.add(p);
            else ct[p] = time;
            done = (int) Arrays.stream(rt).filter(r -> r == 0).count();
        }

        System.out.println("\n\nPID\tAT\tBT\tCT\tTAT\tWT");
        float tatSum = 0, wtSum = 0;
        for (int i = 0; i < n; i++) {
            int tat = ct[i] - at[i], wt = tat - bt[i];
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d%n", i + 1, at[i], bt[i], ct[i], tat, wt);
            tatSum += tat; wtSum += wt;
        }

        System.out.printf("\nAvg TAT: %.2f\nAvg WT: %.2f", tatSum / n, wtSum / n);
        sc.close();
    }
}
