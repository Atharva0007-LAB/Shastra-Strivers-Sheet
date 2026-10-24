import java.util.*;

public class Solution {
    private boolean possible(int[] bloomDay, int day, int m, int k) {
        int count = 0;
        int noOfB = 0;
        for (int b : bloomDay) {
            if (b <= day) {
                count++;
            } else {
                noOfB += count / k;
                count = 0;
            }
        }
        noOfB += count / k;
        return noOfB >= m;
    }

    public int minDays(int[] bloomDay, int m, int k) {
        long totalNeeded = (long) m * k;
        if (totalNeeded > bloomDay.length) return -1;

        int minDay = Integer.MAX_VALUE;
        int maxDay = Integer.MIN_VALUE;
        for (int b : bloomDay) {
            minDay = Math.min(minDay, b);
            maxDay = Math.max(maxDay, b);
        }

        int low = minDay, high = maxDay;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (possible(bloomDay, mid, m, k)) {
                ans = mid;
                high = mid - 1; // Try fewer days
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
        int m = sc.nextInt();
        int k = sc.nextInt();
        int[] bloomDay = new int[n];
        for (int i = 0; i < n; i++) bloomDay[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.minDays(bloomDay, m, k));
        sc.close();
    }
}
