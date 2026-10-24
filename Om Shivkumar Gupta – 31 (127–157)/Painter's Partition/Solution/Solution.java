import java.util.*;

public class Solution {
    private int countPainters(int[] boards, long maxAllowed) {
        int painters = 1;
        long currentLoad = 0;
        for (int b : boards) {
            if (currentLoad + b > maxAllowed) {
                painters++;
                currentLoad = b;
            } else {
                currentLoad += b;
            }
        }
        return painters;
    }

    public long findLargestMinDistance(int[] boards, int k) {
        long low = 0;
        long high = 0;
        for (int b : boards) {
            low = Math.max(low, b);
            high += b;
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countPainters(boards, mid) <= k) {
                ans = mid;
                high = mid - 1; // Try smaller maximum load
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] boards = new int[n];
        for (int i = 0; i < n; i++) boards[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findLargestMinDistance(boards, k));
        sc.close();
    }
}
