# Editorial: Longest Subarray with Sum K


## Difficulty

Medium


## Prerequisites
- Arrays
- Prefix Sum
- Hash Map


## Problem Statement

Given an array of integers that can be positive, zero, or negative, find the maximum length of a contiguous subarray whose sum is equal to $k$.


## Explanation & Approach

### Prefix Sum + Hash Map ($O(n)$ Time)

Let $S[i]$ be the prefix sum up to index $i$:
$$S[i] = \sum_{j=0}^{i} arr[j]$$

A subarray from index $j + 1$ to $i$ has sum equal to $k$ if and only if:
$$S[i] - S[j] = k \iff S[j] = S[i] - k$$

To maximize the length $i - j$, we need the earliest occurrence of the prefix sum $S[i] - k$.

1. Maintain a hash map `map` mapping each prefix sum to the **earliest index** where it appeared.
2. Initialize `map.put(0L, -1)` (representing an empty prefix before index 0).
3. For each index $i$ from 0 to $n - 1$:
   - Add `arr[i]` to `prefixSum`.
   - If `prefixSum - k` exists in `map`, the candidate length is `i - map.get(prefixSum - k)`. Update `maxLen`.
   - If `prefixSum` is NOT in `map`, insert `map.put(prefixSum, i)`. (If it already exists, **do not** overwrite it, because we want the smallest index to maximize length!).


## Java Solution

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            long k = scanner.nextLong();
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) arr[i] = scanner.nextLong();

            Map<Long, Integer> map = new HashMap<>();
            map.put(0L, -1);
            long prefixSum = 0;
            int maxLen = 0;

            for (int i = 0; i < n; i++) {
                prefixSum += arr[i];
                if (map.containsKey(prefixSum - k)) {
                    maxLen = Math.max(maxLen, i - map.get(prefixSum - k));
                }
                if (!map.containsKey(prefixSum)) {
                    map.put(prefixSum, i);
                }
            }
            sb.append(maxLen).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

A single linear scan with $O(1)$ average hash map lookups.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(n)$ space for the hash map.
