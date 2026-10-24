# 138. Find the Repeating and Missing Number - Editorial

## Problem Statement
Given an unsorted array `nums` of size `N` containing numbers from `1` to `N`. One number from the set `{1, 2, ..., N}` is missing and one number occurs twice in the array.

Find the repeating number and the missing number.

Output the two numbers space-separated on a single line: `repeating missing`.

---

## Sample Example

### Sample Input
```text
5
3 1 2 5 3
```

### Sample Output
```text
3 4
```

### Explanation
* In the array [3, 1, 2, 5, 3]:
  - 3 occurs twice (repeating)
  - 4 is missing from {1, 2, 3, 4, 5}

---

## Intuition
Let `R` be the repeating number and `M` be the missing number.
Let `S` be the sum of numbers in the array, and `S_N` be the expected sum of 1 to N:
`S - S_N = R - M`   --- (Equation 1)

Let `S_sq` be the sum of squares in the array, and `S_sq_N` be expected sum of squares:
`S_sq - S_sq_N = R^2 - M^2 = (R - M)(R + M)`

Dividing the two yields:
`R + M = (S_sq - S_sq_N) / (R - M)` --- (Equation 2)

Adding (1) and (2) gives:
`2R = (R - M) + (R + M)  =>  R = ((R - M) + (R + M)) / 2`
`M = R - (R - M)`

This achieves an optimal O(N) time and O(1) auxiliary space without modifying the input array!

## Approach
1. Calculate expected sum `sumN = N * (N + 1) / 2` and sum of squares `sumSqN = N * (N + 1) * (2N + 1) / 6` using 64-bit integers.
2. Iterate through the array to compute actual `sumArr` and `sumSqArr`.
3. Compute `diff = sumArr - sumN` (`R - M`).
4. Compute `sumVal = (sumSqArr - sumSqN) / diff` (`R + M`).
5. Calculate:
   `repeating = (diff + sumVal) / 2`
   `missing = repeating - diff`
6. Return `[repeating, missing]`.

---

## Complexity Analysis
* **Time Complexity:** O(N) to compute the sum and square sum of the array.
* **Space Complexity:** O(1) auxiliary space as only a few 64-bit variables are used.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Finds the repeating and missing numbers in O(N) time and O(1) space using Mathematics.
     * 
     * @return Array of size 2: [repeating, missing]
     */
    public int[] findTwoElement(int[] nums, int n) {
        long N = n;
        long sumN = (N * (N + 1)) / 2;
        long sumSqN = (N * (N + 1) * (2 * N + 1)) / 6;

        long sumArr = 0;
        long sumSqArr = 0;

        for (int x : nums) {
            sumArr += (long) x;
            sumSqArr += (long) x * (long) x;
        }

        // diff = repeating - missing
        long diff = sumArr - sumN;

        // sqDiff = repeating^2 - missing^2 = (repeating - missing) * (repeating + missing)
        long sqDiff = sumSqArr - sumSqN;

        // sumVal = repeating + missing
        long sumVal = sqDiff / diff;

        long repeating = (diff + sumVal) / 2;
        long missing = repeating - diff;

        return new int[]{(int) repeating, (int) missing};
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
        int[] ans = sol.findTwoElement(nums, n);
        System.out.println(ans[0] + " " + ans[1]);
        sc.close();
    }
}
```
