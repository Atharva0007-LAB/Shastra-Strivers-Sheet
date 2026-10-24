### Problem 133: N Meetings in One Room
**Difficulty:** Easy | **Tags:** Greedy, Sorting

**Problem Statement:**
There is one meeting room in a firm. There are `N` meetings in the form of `(start[i], end[i])` where `start[i]` is the start time of meeting `i` and `end[i]` is the finish time of meeting `i`.

Find the maximum number of meetings that can be accommodated in the meeting room, assuming only one meeting can be held at a time.

Note: A new meeting can only start after the previous meeting ends (i.e. start time of the next meeting must be strictly greater than the end time of the previous meeting: `start[next] > end[prev]`).

### Input Format
* The first line contains an integer `N`, representing the number of meetings.
* The second line contains `N` space-separated integers representing the start times.
* The third line contains `N` space-separated integers representing the end times.

### Output Format
* Print a single integer representing the maximum number of meetings that can be accommodated.

### Constraints
* 1 <= N <= 10^5
* 0 <= start[i] < end[i] <= 10^9

### Example
#### Input:
```text
6
1 3 0 5 8 5
2 4 6 7 9 9
```

#### Output:
```text
4
```

#### Explanation
* The sorted meetings by finish time are:
  - (1, 2) -> accommodated, finish time = 2
  - (3, 4) -> accommodated (3 > 2), finish time = 4
  - (0, 6) -> overlaps (0 <= 4), skipped
  - (5, 7) -> accommodated (5 > 4), finish time = 7
  - (8, 9) -> accommodated (8 > 7), finish time = 9
  - (5, 9) -> overlaps (5 <= 9), skipped
* Maximum meetings = 4.
