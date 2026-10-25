# Editorial: Longest Consecutive Sequence


## Difficulty

Medium


## Prerequisites
- Arrays
- Hash Set


## Problem Statement

Given an unsorted array of integers `nums`, find the length of the longest consecutive sequence of elements in $O(n)$ time.


## Explanation & Approach

### Hash Set Lookup ($O(n)$ Time)

A naive sorting approach takes $O(n \log n)$ time. To achieve $O(n)$ time, we use a `HashSet`:

1. **Insert all elements** of `nums` into a `HashSet` to enable $O(1)$ lookups.
2. **Identify sequence starts**:
   Iterate through each element `x` in the set. An element `x` is the beginning of a consecutive sequence if and only if:
   $$x - 1 \notin set$$
   If `x - 1` is in the set, `x` is part of an ongoing sequence and we skip it to prevent redundant checks.
3. **Count the streak**:
   When `x - 1` is not in the set, start counting from `x`:
   ```java
   int cur = x;
   int streak = 1;
   while (set.contains(cur + 1)) {
       cur++;
       streak++;
   }
   maxStreak = Math.max(maxStreak, streak);
   ```

Because each number is visited at most twice (once in the outer loop, and at most once inside the `while` loop when traversing a streak), the total time is strictly $O(n)$.


## Java Solution

```java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            if (n == 0) {
                sb.append("0\n");
                continue;
            }

            Set<Integer> set = new HashSet<>(n);
            for (int i = 0; i < n; i++) set.add(scanner.nextInt());

            int maxLen = 0;
            for (int x : set) {
                if (!set.contains(x - 1)) {
                    int cur = x;
                    int len = 1;
                    while (set.contains(cur + 1)) {
                        cur++;
                        len++;
                    }
                    if (len > maxLen) maxLen = len;
                }
            }
            sb.append(maxLen).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

- Inserting into `HashSet`: $O(n)$.
- Finding sequence starts and checking streaks: each element is checked at most a constant number of times.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(n)$ space for the hash set.
