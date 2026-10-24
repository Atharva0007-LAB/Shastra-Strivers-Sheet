import java.util.*;

public class Solution {
    private int countSubarrays(int[] nums, long maxSum) {
        int count = 1;
        long currentSum = 0;
        for (int x : nums) {
            if (currentSum + x > maxSum) {
                count++;
                currentSum = x;
            } else {
                currentSum += x;
            }
        }
        return count;
    }

    public int splitArray(int[] nums, int k) {
        long low = 0;
        long high = 0;
        for (int x : nums) {
            low = Math.max(low, x);
            high += x;
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countSubarrays(nums, mid) <= k) {
                ans = mid;
                high = mid - 1; // Try smaller largest sum
            } else {
                low = mid + 1;
            }
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.splitArray(nums, k));
        sc.close();
    }
}
