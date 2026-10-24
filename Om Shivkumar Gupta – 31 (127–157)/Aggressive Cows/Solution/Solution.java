import java.util.*;

public class Solution {
    /**
     * Checks if K cows can be placed with at least 'minDist' distance apart.
     */
    private boolean canWePlace(int[] stalls, int minDist, int k) {
        int countCows = 1;
        int lastPos = stalls[0];

        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= minDist) {
                countCows++;
                lastPos = stalls[i];
            }
            if (countCows >= k) return true;
        }
        return false;
    }

    /**
     * Finds the maximum possible minimum distance between K aggressive cows.
     */
    public int aggressiveCows(int[] stalls, int k) {
        Arrays.sort(stalls);
        int n = stalls.length;
        int low = 1;
        int high = stalls[n - 1] - stalls[0];
        int ans = 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canWePlace(stalls, mid, k)) {
                ans = mid;
                low = mid + 1; // Try larger distance
            } else {
                high = mid - 1; // Distance too large
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] stalls = new int[n];
        for (int i = 0; i < n; i++) stalls[i] = sc.nextInt();

        Solution sol = new Solution();
        int res = sol.aggressiveCows(stalls, k);
        System.out.println(res);
        sc.close();
    }
}
