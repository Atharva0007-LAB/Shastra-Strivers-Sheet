# 146. Merge Two Sorted Arrays Without Extra Space - Editorial

## Problem Statement
Given two sorted arrays `arr1` of size `N` and `arr2` of size `M`. Merge the two arrays in-place so that `arr1` contains the smallest `N` elements in sorted non-decreasing order and `arr2` contains the remaining `M` elements in sorted non-decreasing order.

Do not use any extra auxiliary arrays ($mathcal{O}(1)$ auxiliary space).

---

## Sample Example

### Sample Input
```text
4 5
1 3 5 7
0 2 6 8 9
```

### Sample Output
```text
0 1 2 3
5 6 7 8 9
```

### Explanation
* Merging both arrays in sorted order yields: [0, 1, 2, 3, 5, 6, 7, 8, 9]
* arr1 gets the first 4 elements: [0, 1, 2, 3]
* arr2 gets the remaining 5 elements: [5, 6, 7, 8, 9]

---

## Intuition
Allocating an auxiliary array of size N + M takes O(N + M) extra memory, violating the problem constraints.

To achieve O(1) auxiliary space, we use the Gap Method (derived from Shell Sort):
Initially, let `gap = ceil((N + M) / 2)`.
We compare elements that are `gap` distance apart across both arrays (treating them as one virtual concatenated array of size N + M). If the left element is greater than the right element, we swap them.
After completing a pass, we halve the gap: `gap = ceil(gap / 2)`.
We repeat this process until `gap == 1`, after which both arrays are guaranteed to be fully sorted.

## Approach
1. Initialize `gap = ceil((N + M) / 2)`.
2. While `gap > 0`:
   - Set pointers `left = 0` and `right = left + gap`.
   - While `right < N + M`:
     - If `left < N` and `right >= N`: compare `arr1[left]` and `arr2[right - N]`.
     - Else if `left >= N`: compare `arr2[left - N]` and `arr2[right - N]`.
     - Else: compare `arr1[left]` and `arr1[right]`.
     - Swap if left element > right element.
     - Increment `left` and `right`.
   - If `gap == 1`, terminate. Else set `gap = ceil(gap / 2)`.
3. Output `arr1` and `arr2`.

---

## Complexity Analysis
* **Time Complexity:** O((N + M) log(N + M)) because the gap is halved in each step (log(N+M) passes), and each pass does O(N+M) comparisons.
* **Space Complexity:** O(1) auxiliary space as all swaps occur in-place.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    private void swapIfGreater(long[] arr1, long[] arr2, int ind1, int ind2) {
        if (arr1[ind1] > arr2[ind2]) {
            long temp = arr1[ind1];
            arr1[ind1] = arr2[ind2];
            arr2[ind2] = temp;
        }
    }

    /**
     * Merges arr1 and arr2 in O((N+M) log(N+M)) time and O(1) space using the Gap Method (Shell Sort).
     */
    public void merge(long[] arr1, long[] arr2, int n, int m) {
        int len = n + m;
        int gap = (len / 2) + (len % 2); // ceil(len / 2)

        while (gap > 0) {
            int left = 0;
            int right = left + gap;

            while (right < len) {
                // Case 1: left in arr1, right in arr2
                if (left < n && right >= n) {
                    swapIfGreater(arr1, arr2, left, right - n);
                }
                // Case 2: both in arr2
                else if (left >= n) {
                    swapIfGreater(arr2, arr2, left - n, right - n);
                }
                // Case 3: both in arr1
                else {
                    swapIfGreater(arr1, arr1, left, right);
                }
                left++;
                right++;
            }

            if (gap == 1) break;
            gap = (gap / 2) + (gap % 2);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();

        long[] arr1 = new long[n];
        long[] arr2 = new long[m];

        for (int i = 0; i < n; i++) arr1[i] = sc.nextLong();
        for (int j = 0; j < m; j++) arr2[j] = sc.nextLong();

        Solution sol = new Solution();
        sol.merge(arr1, arr2, n, m);

        for (int i = 0; i < n; i++) {
            System.out.print(arr1[i] + (i == n - 1 ? "" : " "));
        }
        System.out.println();

        for (int j = 0; j < m; j++) {
            System.out.print(arr2[j] + (j == m - 1 ? "" : " "));
        }
        System.out.println();
        sc.close();
    }
}
```
