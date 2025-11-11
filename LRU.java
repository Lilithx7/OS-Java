import java.util.*;

public class LRU {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Frames: "); int f = sc.nextInt();
        System.out.print("Pages: "); int n = sc.nextInt();
        int[] p = new int[n];
        System.out.println("Enter pages:");
        for (int i = 0; i < n; i++) p[i] = sc.nextInt();

        List<Integer> frames = new ArrayList<>();
        int faults = 0;
        System.out.println("\nPage Replacement Process:");

        for (int x : p) {
            System.out.print("Page " + x + " -> ");
            if (frames.contains(x)) {
                frames.remove((Integer) x);
                frames.add(x);
                System.out.println("Hit\tFrames: " + frames);
            } else {
                faults++;
                if (frames.size() == f) {
                    System.out.print("Fault - Replace " + frames.remove(0) + " ");
                }
                frames.add(x);
                System.out.println("Fault\tFrames: " + frames);
            }
        }

        System.out.println("\nTotal Faults: " + faults);
        System.out.println("Total Hits: " + (n - faults));
        sc.close();
    }
}
