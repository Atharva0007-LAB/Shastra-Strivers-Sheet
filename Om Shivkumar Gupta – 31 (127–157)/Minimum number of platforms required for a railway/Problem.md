### Problem 150: Minimum number of platforms required for a railway
**Difficulty:** Medium | **Tags:** Greedy, Sorting, Two Pointers

**Problem Statement:**
Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms required for the railway station so that no train is kept waiting.

Consider that all trains arrive and depart on the same day. At any given instance of time, the same platform cannot be used for both departure of a train and arrival of another train (if arrival == departure, 2 platforms are required).

### Input Format
* The first line contains an integer `N`, representing the number of trains.
* The second line contains `N` space-separated integers representing the arrival times.
* The third line contains `N` space-separated integers representing the departure times.

### Output Format
* Print a single integer representing the minimum number of platforms required.

### Constraints
* 1 <= N <= 10^5
* 0 <= arr[i] <= dep[i] <= 2359

### Example
#### Input:
```text
6
900 940 950 1100 1500 1800
910 1200 1120 1130 1900 2000
```

#### Output:
```text
3
```

#### Explanation
* Maximum overlap occurs between 11:00 and 11:20 when 3 trains are present at the station simultaneously (trains arriving at 940, 950, and 1100).
* Hence, minimum 3 platforms are needed.
