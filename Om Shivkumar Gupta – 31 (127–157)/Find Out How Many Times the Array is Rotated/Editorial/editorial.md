# 138. Find Out How Many Times the Array is Rotated - Editorial

## Problem Statement
Given a rotated sorted array of distinct elements, find the number of right rotations `k`.

---

## Sample Example

### Sample Input
```text
5
5 1 2 3 4
```

### Sample Output
```text
1
```

---

## Intuition & Approach
When an array is right-rotated `k` times, the minimum element (which was originally at index 0) shifts to index `k`.
Therefore, **the rotation count is simply the index of the minimum element**!
1. Use binary search to find the minimum element's index:
   - If `arr[mid] > arr[high]`, the minimum lies strictly in the right half: `low = mid + 1`.
   - Else, the minimum is at `mid` or in the left half: `high = mid`.
2. When `low == high`, return `low`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - binary search.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int findKRotation(int[] arr, int n) {
        int low = 0, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > arr[high]) low = mid + 1;
            else high = mid;
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
```
