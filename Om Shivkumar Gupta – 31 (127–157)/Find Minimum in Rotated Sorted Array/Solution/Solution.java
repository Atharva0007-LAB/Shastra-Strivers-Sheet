import java.util.*;

public class Solution {
    /**
     * Finds the minimum element in a rotated sorted array of unique elements.
     * Uses binary search comparing nums[mid] with nums[high].
     */
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) {
                // Minimum must lie strictly in the right half
                low = mid + 1;
            } else {
                // Minimum could be nums[mid] or in the left half
                high = mid;
            }
        }
        return nums[low];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findMin(nums));
        sc.close();
    }
}
