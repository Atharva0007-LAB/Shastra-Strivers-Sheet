# 128. Subsets II - Editorial

## Problem Statement
Given an integer array `nums` that may contain duplicate elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Subsets can be generated in any order, but each subset's elements must be sorted in non-decreasing order, and subsets are printed in standard lexicographical order.

---

## Sample Example

### Sample Input
```text
3
1 2 2
```

### Sample Output
```text
[]
[1]
[1, 2]
[1, 2, 2]
[2]
[2, 2]
```

### Explanation
* The subsets of [1, 2, 2] without duplicates are:
  - Empty set: []
  - 1-element sets: [1], [2]
  - 2-element sets: [1, 2], [2, 2]
  - 3-element set: [1, 2, 2]

---

## Intuition
When generating subsets from an array with duplicates, generating every combination normally yields identical subsets multiple times (e.g. picking the first '2' vs picking the second '2').

To prevent duplicate subsets:
1. Sort the array so that identical elements are adjacent.
2. At any choice point in the recursion, if the current element is identical to the preceding element and the preceding element was not included at this level (`i > start && nums[i] == nums[i-1]`), skip it.

## Approach
1. Sort the input array `nums`.
2. Define a helper recursive function `backtrack(start, currentList)`:
   - Add a clone of `currentList` to the result list.
   - Loop `i` from `start` to `nums.length - 1`:
     - If `i > start && nums[i] == nums[i - 1]`, continue to skip duplicate branches.
     - Include `nums[i]` into `currentList`.
     - Recurse with `backtrack(i + 1, currentList)`.
     - Backtrack by removing the last element.
3. Return the result.

---

## Complexity Analysis
* **Time Complexity:** O(2^N * N) because there are at most 2^N subsets and copying each subset takes O(N) time.
* **Space Complexity:** O(N) auxiliary space for the recursive call stack and the current subset tracking.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    /**
     * Generates all unique subsets of an array with duplicates.
     * 
     * @param nums Array containing possibly duplicate integers.
     * @return List of all unique subsets.
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // Sort first so duplicates are adjacent
        Arrays.sort(nums);
        backtrack(0, nums, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            // Skip duplicate elements at the same recursion depth
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            current.add(nums[i]);
            backtrack(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("[]");
            return;
        }
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        List<List<Integer>> subsets = sol.subsetsWithDup(nums);

        for (List<Integer> subset : subsets) {
            System.out.print("[");
            for (int i = 0; i < subset.size(); i++) {
                System.out.print(subset.get(i) + (i == subset.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
```
