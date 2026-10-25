# Editorial: Majority Element II


## Difficulty

Medium


## Prerequisites
- Arrays
- Extended Boyer-Moore Voting Algorithm


## Problem Statement

Given an array of $n$ integers, find all elements that appear more than $\lfloor n / 3 \rfloor$ times.


## Explanation & Approach

### Step 1: Why at most 2 elements?

If three distinct elements each appeared more than $\lfloor n / 3 \rfloor$ times:
Each appears at least $\lfloor n / 3 \rfloor + 1$ times.
Total count $\ge 3 \times (\lfloor n / 3 \rfloor + 1) > n$, which is impossible.
Therefore, at most **2** such majority elements can exist in any array.

### Step 2: Extended Boyer-Moore Voting Algorithm

We maintain two potential candidates, `cand1` and `cand2`, along with their vote counters `count1` and `count2`.
Initialize both counts to 0.

For each number `x` in the array:
1. If `x == cand1`, increment `count1`.
2. Else if `x == cand2`, increment `count2`.
3. Else if `count1 == 0`, set `cand1 = x` and `count1 = 1`.
4. Else if `count2 == 0`, set `cand2 = x` and `count2 = 1`.
5. Else, decrement both `count1` and `count2` (three distinct elements cancel out).

### Step 3: Verification Pass

Unlike the $n/2$ majority element problem, a majority element is not guaranteed to exist. We must make a second pass through the array to count the actual frequencies of `cand1` and `cand2`.
If an element appears more than $\lfloor n / 3 \rfloor$ times, we include it in our result.

Finally, sort any valid candidates in increasing order. If neither candidate qualifies, print `-1`.


## Example

### Input:

```text
3
3
3 2 3
1
1
2
1 2
```

### Trace:

Test case 1 (`3 2 3`):
- x = 3: count1 is 0 -> cand1 = 3, count1 = 1
- x = 2: count2 is 0 -> cand2 = 2, count2 = 1
- x = 3: matches cand1 -> count1 = 2
Verification:
- Count of 3 is 2 > ⌊3 / 3⌋ = 1 -> Valid!
- Count of 2 is 1 not > 1 -> Not valid.
Result: `3`

Output:
```text
3
1
1 2
```


## Java Solution

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            int cand1 = 0, count1 = 0;
            int cand2 = 0, count2 = 0;

            for (int x : nums) {
                if (count1 > 0 && x == cand1) {
                    count1++;
                } else if (count2 > 0 && x == cand2) {
                    count2++;
                } else if (count1 == 0) {
                    cand1 = x;
                    count1 = 1;
                } else if (count2 == 0) {
                    cand2 = x;
                    count2 = 1;
                } else {
                    count1--;
                    count2--;
                }
            }

            int actual1 = 0, actual2 = 0;
            for (int x : nums) {
                if (count1 > 0 && x == cand1) actual1++;
                else if (count2 > 0 && x == cand2) actual2++;
            }

            List<Integer> res = new ArrayList<>();
            int threshold = n / 3;
            if (count1 > 0 && actual1 > threshold) res.add(cand1);
            if (count2 > 0 && actual2 > threshold) res.add(cand2);
            Collections.sort(res);

            if (res.isEmpty()) {
                sb.append("-1\n");
            } else {
                for (int i = 0; i < res.size(); i++) {
                    sb.append(res.get(i));
                    if (i != res.size() - 1) sb.append(" ");
                }
                sb.append("\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the number of elements in a test case.
- Pass 1 (Finding candidates): $O(n)$
- Pass 2 (Verification): $O(n)$
- Sorting result: at most 2 elements, $O(1)$.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space beyond storing input and output.
