import java.util.*;

public class Solution {
    /**
     * Computes floor(sqrt(n)) using binary search on answer range [1, n].
     */
    public long floorSqrt(long n) {
        if (n == 0 || n == 1) return n;

        long low = 1, high = n;
        long ans = 1;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (mid <= n / mid) {
                ans = mid;
                low = mid + 1; // Try larger candidate
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLong()) return;
        long n = sc.nextLong();

        Solution sol = new Solution();
        System.out.println(sol.floorSqrt(n));
        sc.close();
    }
}
