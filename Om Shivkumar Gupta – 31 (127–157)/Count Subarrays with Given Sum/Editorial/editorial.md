# 127. Count Subarrays with Given Sum - Editorial

## Problem Statement
Given an array of integers `nums` and an integer `k`, return the total number of continuous subarrays whose sum equals `k`.

---

## Sample Example

### Sample Input
```text
3 2
1 1 1
```

### Sample Output
```text
2
```

### Explanation
Subarrays with sum 2 are:
- Index 0 to 1: `[1, 1]` -> sum = 2
- Index 1 to 2: `[1, 1]` -> sum = 2
Total count = 2.

---

## Intuition
A brute force approach considers all pairs `(i, j)` and calculates their sum in O(N^2) or O(N^3) time, which leads to Time Limit Exceeded for N = 10^5.
Instead, observe that if the prefix sum up to index `j` is `S_j` and the prefix sum up to index `i - 1` is `S_{i-1}`, the subarray sum from `i` to `j` is:
`Sum(i, j) = S_j - S_{i-1}`
For this to equal `k`:
`S_j - S_{i-1} = k  =>  S_{i-1} = S_j - k`
Thus, at each index `j`, we only need to count how many prior prefix sums were equal to `S_j - k`.

## Approach
1. Initialize a hash map `prefixSumMap` storing frequencies of prefix sums.
2. Seed the map with `{0: 1}` to handle subarrays starting from index 0.
3. Maintain a running sum `currentSum`.
4. Iterate through each element in `nums`:
   - Add the element to `currentSum`.
   - If `currentSum - k` exists in the map, add its frequency to `count`.
   - Update the frequency of `currentSum` in the map.
5. Return `count`.

---

## Complexity Analysis
* **Time Complexity:** O(N) on average, as each array element is processed once and hash map lookups take O(1) average time.
* **Space Complexity:** O(N) in the worst case to store distinct prefix sums in the hash map.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
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
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.subarraySum(nums, k));
        sc.close();
    }
}
```
