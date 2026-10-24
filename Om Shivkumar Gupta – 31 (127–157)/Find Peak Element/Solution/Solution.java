import java.util.Scanner;

public class Solution {
    /**
     * Finds a peak element in O(log N) using Binary Search.
     * 
     * @param nums Integer array with no two adjacent equal elements.
     * @return Index of any peak element.
     */
    public int findPeakElement(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            // If mid element is smaller than its right neighbor,
            // an upward slope exists to the right, guaranteeing a peak on the right.
            if (nums[mid] < nums[mid + 1]) {
                low = mid + 1;
            } else {
                // Otherwise, a peak exists on the left side (including mid itself).
                high = mid;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int peakIndex = sol.findPeakElement(nums);
        System.out.println(peakIndex);
        sc.close();
    }
}
