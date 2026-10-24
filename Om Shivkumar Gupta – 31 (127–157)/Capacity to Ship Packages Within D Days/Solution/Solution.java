import java.util.*;

public class Solution {
    private int countDays(int[] weights, int capacity) {
        int days = 1;
        int load = 0;
        for (int w : weights) {
            if (load + w > capacity) {
                days++;
                load = w;
            } else {
                load += w;
            }
        }
        return days;
    }

    public int shipWithinDays(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int w : weights) {
            maxWeight = Math.max(maxWeight, w);
            totalWeight += w;
        }

        int low = maxWeight;
        int high = totalWeight;
        int ans = totalWeight;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int daysNeeded = countDays(weights, mid);
            if (daysNeeded <= days) {
                ans = mid;
                high = mid - 1; // Try smaller capacity
            } else {
                low = mid + 1;  // Capacity too small
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int days = sc.nextInt();
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) weights[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.shipWithinDays(weights, days));
        sc.close();
    }
}
