import java.util.*;

public class Solution {
    /**
     * Returns:
     * 1 if mid^n == m
     * 0 if mid^n < m
     * 2 if mid^n > m
     */
    private int checkPower(int mid, int n, int m) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans = ans * mid;
            if (ans > m) return 2;
        }
        if (ans == m) return 1;
        return 0;
    }

    public int nthRoot(int n, int m) {
        int low = 1, high = m;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midN = checkPower(mid, n, m);
            if (midN == 1) return mid;
            if (midN == 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.nthRoot(n, m));
        sc.close();
    }
}
