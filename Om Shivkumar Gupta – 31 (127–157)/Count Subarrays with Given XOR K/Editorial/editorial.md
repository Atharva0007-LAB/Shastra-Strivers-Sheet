# 128. Count Subarrays with Given XOR K - Editorial

## Problem Statement
Given an array `A` and an integer `B`, count the number of continuous subarrays with bitwise XOR equal to `B`.

---

## Sample Example

### Sample Input
```text
5 6
4 2 2 6 4
```

### Sample Output
```text
4
```

---

## Intuition
Let `XR` be the cumulative XOR from index 0 to `j`.
If a prefix up to index `i-1` has cumulative XOR `P`, then the XOR of subarray `A[i...j]` is:
`SubarrayXOR = XR ^ P`
We want:
`XR ^ P = B  =>  P = XR ^ B` (by XOR properties: X ^ Y = Z => X ^ Z = Y)
So at each step `j`, the count of valid subarrays ending at `j` is simply the frequency of previously seen prefix XOR equal to `XR ^ B`.

## Approach
1. Use a hash map `xorFreq` to store the count of prefix XOR values seen so far.
2. Initialize `xorFreq[0] = 1` to account for subarrays starting at index 0.
3. Keep track of running `currentXor`.
4. Traverse the array:
   - `currentXor ^= A[i]`
   - Look up `currentXor ^ B` in the map and add its frequency to `count`.
   - Update the frequency of `currentXor` in the map.
5. Return `count`.

---

## Complexity Analysis
* **Time Complexity:** O(N) average time with hash map lookup and insertion taking O(1) average.
* **Space Complexity:** O(N) auxiliary space to store distinct prefix XOR values.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int solve(int[] A, int B) {
        int count = 0;
        int currentXor = 0;
        Map<Integer, Integer> xorFreq = new HashMap<>();
        xorFreq.put(0, 1);

        for (int x : A) {
            currentXor ^= x;
            int requiredXor = currentXor ^ B;
            if (xorFreq.containsKey(requiredXor)) {
                count += xorFreq.get(requiredXor);
            }
            xorFreq.put(currentXor, xorFreq.getOrDefault(currentXor, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int b = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.solve(a, b));
        sc.close();
    }
}
```
