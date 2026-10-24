# 140. Count Occurrences in a Sorted Array - Editorial

## Problem Statement
Given a sorted array `arr` and an element `x`, count how many times `x` occurs in O(log N) time.

---

## Sample Example

### Sample Input
```text
7 2
1 1 2 2 2 2 3
```

### Sample Output
```text
4
```

---

## Intuition & Approach
Instead of linearly traversing in O(N) time, use binary search to locate:
1. The **first occurrence** index of `x`.
2. The **last occurrence** index of `x`.
3. If `first == -1`, return `0`.
4. Otherwise, total occurrences = `last - first + 1`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - two binary searches.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int findFirst(int[] arr, int n, int x) {
        int low = 0, high = n - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == x) { ans = mid; high = mid - 1; }
            else if (arr[mid] < x) low = mid + 1;
            else high = mid - 1;
        }
        return ans;
    }

    private int findLast(int[] arr, int n, int x) {
        int low = 0, high = n - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == x) { ans = mid; low = mid + 1; }
            else if (arr[mid] < x) low = mid + 1;
            else high = mid - 1;
        }
        return ans;
    }

    public int count(int[] arr, int n, int x) {
        int first = findFirst(arr, n, x);
        if (first == -1) return 0;
        return findLast(arr, n, x) - first + 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int x = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.count(arr, n, x));
        sc.close();
    }
}
```
