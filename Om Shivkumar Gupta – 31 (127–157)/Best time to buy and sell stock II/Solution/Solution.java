import java.util.Scanner;

public class Solution {
    /**
     * Calculates maximum profit with unlimited transactions using Greedy slope accumulation.
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) prices[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.maxProfit(prices);
        System.out.println(ans);
        sc.close();
    }
}
