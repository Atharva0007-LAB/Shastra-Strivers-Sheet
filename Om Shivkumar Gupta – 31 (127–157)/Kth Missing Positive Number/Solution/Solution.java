import java.util.*;

public class Solution {
    /**
     * Finds the kth missing positive integer in O(log N) time using binary search.
     * Number of missing integers before index i is: arr[i] - (i + 1).
     */
    public int findKthPositive(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int missingCount = arr[mid] - (mid + 1);

            if (missingCount < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        // At the end, the kth missing number is low + k
        return low + k;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findKthPositive(arr, k));
        sc.close();
    }
}
