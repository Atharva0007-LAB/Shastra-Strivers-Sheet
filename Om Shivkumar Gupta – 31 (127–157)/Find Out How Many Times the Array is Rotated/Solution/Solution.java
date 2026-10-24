import java.util.*;

public class Solution {
    /**
     * Finds rotation count by locating the index of the minimum element.
     * Uses binary search: O(log N) time, O(1) space.
     */
    public int findKRotation(int[] arr, int n) {
        int low = 0, high = n - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > arr[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findKRotation(arr, n));
        sc.close();
    }
}
