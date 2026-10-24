import java.util.*;

public class Solution {
    /**
     * Searches for target in a rotated sorted array of distinct elements.
     * Identifies the sorted half at each iteration to maintain O(log N).
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return mid;

            // Check if left half is sorted
            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1; // Target lies in the left half
                } else {
                    low = mid + 1;  // Target lies in the right half
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;  // Target lies in the right half
                } else {
                    high = mid - 1; // Target lies in the left half
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.search(nums, target));
        sc.close();
    }
}
