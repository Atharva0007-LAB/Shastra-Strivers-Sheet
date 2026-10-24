import java.util.Scanner;

public class Solution {
    /**
     * Finds maximum obtainable revenue by cutting the rod using 1D Dynamic Programming.
     */
    public int cutRod(int[] price, int n) {
        int[] dp = new int[n + 1];

        // dp[i] stores the maximum revenue achievable for rod of length i
        for (int i = 1; i <= n; i++) {
            int maxVal = Integer.MIN_VALUE;
            // Try every possible first cut of length j
            for (int j = 1; j <= i; j++) {
                maxVal = Math.max(maxVal, price[j - 1] + dp[i - j]);
            }
            dp[i] = maxVal;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] price = new int[n];
        for (int i = 0; i < n; i++) {
            price[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int ans = sol.cutRod(price, n);
        System.out.println(ans);
        sc.close();
    }
}
