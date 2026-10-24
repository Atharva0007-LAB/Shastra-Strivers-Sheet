# Editorial: Sort an Array of 0's, 1's and 2's

## Difficulty

Medium

## Prerequisites

- Arrays
- Two Pointers
- Dutch National Flag Algorithm

## Approach: Dutch National Flag Algorithm

The array contains only three possible values: 0, 1, and 2.
We can sort it in-place in a single traversal using three pointers.

### Step 1: Initialize Three Pointers

Maintain three pointers:

- `low`: position where the next 0 should be placed.
- `mid`: current element being examined.
- `high`: position where the next 2 should be placed.

Initially:

- `low = 0`
- `mid = 0`
- `high = n - 1`

The array is divided into four regions:

- Indices before `low`: all 0s.
- Indices from `low` to `mid - 1`: all 1s.
- Indices from `mid` to `high`: unprocessed elements.
- Indices after `high`: all 2s.

### Step 2: Process the Current Element

Continue while `mid <= high`.

**Case 1: `nums[mid] == 0`**

Swap `nums[mid]` with `nums[low]`, then increment both `low` and `mid`.

The current zero is placed in its correct region.

**Case 2: `nums[mid] == 1`**

Increment `mid`.

The element is already in the correct region.

**Case 3: `nums[mid] == 2`**

Swap `nums[mid]` with `nums[high]`, then decrement `high`.

Do not increment `mid` here because the element swapped from the right side has not yet been examined.

### Step 3: Return the Sorted Array

When `mid > high`, every element has been processed and the array is sorted in-place.

## C++ Solution

```cpp
class Solution {
public:
    void sortZeroOneTwo(vector<int>& nums) {
        int low = 0;
        int mid = 0;
        int high = nums.size() - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums[low], nums[mid]);
                low++;
                mid++;
            }
            else if (nums[mid] == 1) {
                mid++;
            }
            else {
                swap(nums[mid], nums[high]);
                high--;
            }
        }
    }
};

Example Walkthrough
Input:

[2, 0, 2, 1, 1, 0]

Initially, low = 0, mid = 0, and high = 5.
Step	Action	Array
1	Swap 2 with the element at high	[0, 0, 2, 1, 1, 2]
2	Move 0 into the left region	[0, 0, 2, 1, 1, 2]
3	Move the next 0 into the left region	[0, 0, 2, 1, 1, 2]
4	Move 2 into the right region	[0, 0, 1, 1, 2, 2]
5	Advance past the first 1	[0, 0, 1, 1, 2, 2]
6	Advance past the second 1	[0, 0, 1, 1, 2, 2]

Output:

[0, 0, 1, 1, 2, 2]
## Complexity Analysis
- Time Complexity
O(N), because each element is processed in a single traversal.

- Space Complexity
O(1) auxiliary space, because the algorithm sorts the original array
using only three pointers and swaps.