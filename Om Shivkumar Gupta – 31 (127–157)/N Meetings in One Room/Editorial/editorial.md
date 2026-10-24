# 133. N Meetings in One Room - Editorial

## Problem Statement
There is one meeting room in a firm. There are `N` meetings in the form of `(start[i], end[i])` where `start[i]` is the start time of meeting `i` and `end[i]` is the finish time of meeting `i`.

Find the maximum number of meetings that can be accommodated in the meeting room, assuming only one meeting can be held at a time.

Note: A new meeting can only start after the previous meeting ends (i.e. start time of the next meeting must be strictly greater than the end time of the previous meeting: `start[next] > end[prev]`).

---

## Sample Example

### Sample Input
```text
6
1 3 0 5 8 5
2 4 6 7 9 9
```

### Sample Output
```text
4
```

### Explanation
* The sorted meetings by finish time are:
  - (1, 2) -> accommodated, finish time = 2
  - (3, 4) -> accommodated (3 > 2), finish time = 4
  - (0, 6) -> overlaps (0 <= 4), skipped
  - (5, 7) -> accommodated (5 > 4), finish time = 7
  - (8, 9) -> accommodated (8 > 7), finish time = 9
  - (5, 9) -> overlaps (5 <= 9), skipped
* Maximum meetings = 4.

---

## Intuition
This is the classic Activity Selection Problem. 

Greedy choice property:
To maximize the number of non-overlapping meetings accommodated in a single room, we should always pick the meeting that finishes earliest. Finishing early leaves the room free as early as possible, maximizing the remaining time available for subsequent meetings.

## Approach
1. Pair each `start[i]` and `end[i]` into a `Meeting` object.
2. Sort the list of meetings in ascending order of their end times.
3. Select the first meeting. Set `lastEndTime = meeting[0].end` and `count = 1`.
4. Iterate through the remaining meetings:
   - If `meeting[i].start > lastEndTime`, select this meeting, increment `count`, and update `lastEndTime = meeting[i].end`.
   - Otherwise, skip the meeting because of conflict.
5. Return `count`.

---

## Complexity Analysis
* **Time Complexity:** O(N log N) dominated by sorting the meetings array.
* **Space Complexity:** O(N) to store the meeting objects.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    static class Meeting {
        int start;
        int end;
        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Finds maximum meetings possible using a greedy approach.
     */
    public int maxMeetings(int[] start, int[] end, int n) {
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i]);
        }

        // Sort meetings according to their end times in ascending order
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.end, b.end));

        int count = 1;
        int lastEndTime = meetings[0].end;

        for (int i = 1; i < n; i++) {
            // A meeting can start strictly after the last one ends
            if (meetings[i].start > lastEndTime) {
                count++;
                lastEndTime = meetings[i].end;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] start = new int[n];
        int[] end = new int[n];

        for (int i = 0; i < n; i++) {
            start[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            end[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int ans = sol.maxMeetings(start, end, n);
        System.out.println(ans);
        sc.close();
    }
}
```
