# Editorial: Two Sum


## Difficulty

Easy


## Prerequisites
- Arrays
- Hash Map


## Problem Statement

Given an array of integers `nums` and an integer `target`, return the 0-based indices of the two numbers such that they add up to `target`.


## Explanation & Approach

### One-Pass Hash Table

The optimal approach uses a hash map to store each number and its index as we iterate through the array.

For each index `i` with element `x`:
1. Calculate the complement: `comp = target - x`.
2. Check if `comp` already exists in the map:
   - If it does, we found the pair! Its index is `map.get(comp)`, and the current index is `i`. We return `map.get(comp) + " " + i`.
   - If not, insert the current number `x` with its index `i` into the map: `map.put(x, i)`.

This guarantees a single-pass solution without checking all pairs.


## Example

### Input:

```text
3
4 9
2 7 11 15
3 6
3 2 4
2 6
3 3
```

### Trace:

Test case 1 (`nums = [2, 7, 11, 15], target = 9`):
- i = 0, x = 2: comp = 9 - 2 = 7. Not in map. Insert `(2 -> 0)`.
- i = 1, x = 7: comp = 9 - 7 = 2. Found in map with index 0! Pair is `0 1`.

Output:
```text
0 1
1 2
0 1
```


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
            int target = scanner.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            Map<Integer, Integer> map = new HashMap<>();
            boolean found = false;
            for (int i = 0; i < n; i++) {
                int comp = target - nums[i];
                if (map.containsKey(comp)) {
                    sb.append(map.get(comp)).append(" ").append(i).append("\n");
                    found = true;
                    break;
                }
                map.put(nums[i], i);
            }
            if (!found) {
                sb.append("-1\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Lookups and insertions in a hash map take $O(1)$ average time.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(n)$ space to store elements in the hash map.
