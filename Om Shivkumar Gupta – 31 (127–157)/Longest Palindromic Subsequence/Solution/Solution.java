import java.util.Scanner;

public class Solution {
    /**
     * Finds the length of the longest palindromic subsequence using Dynamic Programming.
     */
    public int longestPalindromeSubseq(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        // LCS between s and reverse(s)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int ans = sol.longestPalindromeSubseq(s);
        System.out.println(ans);
        sc.close();
    }
}
