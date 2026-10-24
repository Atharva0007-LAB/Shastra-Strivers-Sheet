import java.util.*;

public class Solution {
    /**
     * Finds the lower bound of x in a sorted array arr.
     * Lower bound is the smallest index where arr[index] >= x.
     * Returns n if no such element exists.
     */
    public int lowerBound(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        int ans = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                ans = mid;
                high = mid - 1; // Look for smaller index on the left
            } else {
                low = mid + 1;  // Look on the right
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int x = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.lowerBound(arr, n, x));
        sc.close();
    }
}
