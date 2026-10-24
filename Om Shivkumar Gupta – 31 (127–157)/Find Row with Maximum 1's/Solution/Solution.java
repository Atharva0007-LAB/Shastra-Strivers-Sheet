import java.util.*;

public class Solution {
    /**
     * Finds the index of the first row with the maximum number of 1's.
     * Uses binary search (lower bound of 1) on each row: O(N log M) time.
     */
    private int lowerBound(int[] row, int m) {
        int low = 0, high = m - 1;
        int ans = m;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (row[mid] >= 1) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public int rowWithMax1s(int[][] mat, int n, int m) {
        int maxOnes = 0;
        int rowIdx = -1;

        for (int i = 0; i < n; i++) {
            int firstOne = lowerBound(mat[i], m);
            int count = m - firstOne;
            if (count > maxOnes) {
                maxOnes = count;
                rowIdx = i;
            }
        }
        return rowIdx;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] mat = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        Solution sol = new Solution();
        System.out.println(sol.rowWithMax1s(mat, n, m));
        sc.close();
    }
}
