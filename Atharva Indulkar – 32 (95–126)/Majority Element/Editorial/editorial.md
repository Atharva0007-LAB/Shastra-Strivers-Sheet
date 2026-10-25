# Editorial: Majority Element


## Difficulty

Easy


## Prerequisites
- Arrays
- Boyer-Moore Voting Algorithm


## Problem Statement

Given an array of n integers in which one value appears more than ⌊n / 2⌋ times, find that value.


## Explanation & Approach

### Step 1: Cancel Out Pairs of Different Elements

Imagine removing pairs of different elements from the array, one at a time. Since the majority element appears more than half the time, it can never be fully cancelled by the other elements. Whatever is left at the end must be the majority element.

### Step 2: Keep a Candidate and a Counter

Doing this cancelling in one pass uses a `candidate` and a `count`. For each element `x`:
- If `count` is 0, make `x` the new candidate.
- If `x` equals the candidate, increase `count`, since it adds one more vote for the candidate.
- Otherwise decrease `count`, since `x` cancels one vote of the candidate.

### Step 3: The Last Candidate Is the Answer

Because the problem guarantees that a majority element exists, the candidate left after the pass is that element. No second verification pass is needed, and no extra memory is used.


## Example

### Input:

```text
2
3
3 2 3
7
2 2 1 1 1 2 2
```

### Trace:

Test case 1 (`3 2 3`):
- x = 3: count is 0, candidate = 3, count = 1
- x = 2: differs from candidate, count = 0
- x = 3: count is 0, candidate = 3, count = 1

Test case 2 (`2 2 1 1 1 2 2`):
- x = 2: candidate = 2, count = 1
- x = 2: count = 2
- x = 1: count = 1
- x = 1: count = 0
- x = 1: count is 0, candidate = 1, count = 1
- x = 2: differs from candidate, count = 0
- x = 2: count is 0, candidate = 2, count = 1

Output:
```text
3
2
```


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = scanner.nextInt();

            int candidate = 0;
            int count = 0;
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                if (count == 0) {
                    candidate = x;
                }
                if (x == candidate) {
                    count++;
                } else {
                    count--;
                }
            }

            sb.append(candidate).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the size of the array in a test case. The array is read and processed in a single pass.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space, since the elements are processed as they are read.
