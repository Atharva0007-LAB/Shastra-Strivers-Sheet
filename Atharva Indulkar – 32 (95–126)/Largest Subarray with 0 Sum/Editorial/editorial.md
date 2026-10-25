# Editorial: Largest Subarray with 0 Sum


## Difficulty

Medium


## Prerequisites
- Arrays
- Prefix Sum
- Hash Map


## Problem Statement

Given an array of integers, find the length of the longest subarray with sum equal to 0.


## Explanation & Approach

### Prefix Sum + Hash Map ($O(n)$ Time)

If the prefix sum up to index $i$ is equal to the prefix sum up to index $j$ ($j < i$):
$$\sum_{k=0}^{i} arr[k] = \sum_{k=0}^{j} arr[k] \implies \sum_{k=j+1}^{i} arr[k] = 0$$

Thus, any two equal prefix sums indicate that the subarray between them sums to zero!
To maximize the subarray length $i - j$:
- We store each prefix sum in a hash map associated with its **first occurrence** index.
- If we see the same prefix sum again at index $i$, the length of the subarray with sum 0 is $i - map.get(prefixSum)$.
- Initialize `map.put(0L, -1)` to handle subarrays starting from index 0.


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
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) arr[i] = scanner.nextLong();

            Map<Long, Integer> map = new HashMap<>();
            map.put(0L, -1);
            long prefixSum = 0;
            int maxLen = 0;

            for (int i = 0; i < n; i++) {
                prefixSum += arr[i];
                if (map.containsKey(prefixSum)) {
                    maxLen = Math.max(maxLen, i - map.get(prefixSum));
                } else {
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

Linear scan of $n$ elements with $O(1)$ average hash map operations.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(n)$ space for the hash map.
