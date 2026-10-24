import java.util.*;

public class Solution {
    /**
     * Counts the number of continuous subarrays whose sum equals k.
     * Uses prefix sums and a hash map to achieve O(N) time complexity.
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        long currentSum = 0;
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1);

        for (int num : nums) {
            currentSum += num;
            long targetPrefix = currentSum - k;
            if (prefixSumMap.containsKey(targetPrefix)) {
                count += prefixSumMap.get(targetPrefix);
            }
            prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int result = sol.subarraySum(nums, k);
        System.out.println(result);
        sc.close();
    }
}
