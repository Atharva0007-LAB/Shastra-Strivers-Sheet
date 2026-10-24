# Editorial: Longest Increasing Subsequence

## Approach: Binary Search

We maintain a vector called `tails`, where `tails[i]` stores the smallest possible ending value of an increasing subsequence of length `i + 1`.

For each element `x` in the array:

1. Use `lower_bound()` to find the first element in `tails` that is greater than or equal to `x`.
2. If no such element exists, append `x` to extend the longest increasing subsequence.
3. Otherwise, replace the found element with `x` to maintain the smallest possible ending value for that subsequence length.

The `tails` vector does not necessarily represent an actual subsequence. However, its size gives the length of the longest strictly increasing subsequence.

We use `lower_bound()` instead of `upper_bound()` because equal elements must not increase the length.

## C++ Solution (Function Only)

```cpp
class Solution {
public:
    int LIS(vector<int>& nums) {
        vector<int> tails;

        for (int x : nums) {
            auto it = lower_bound(tails.begin(), tails.end(), x);

            if (it == tails.end()) {
                tails.push_back(x);
            } else {
                *it = x;
            }
        }

        return (int)tails.size();
    }
};
```

## Example Walkthrough

For the array:

`[10, 9, 2, 5, 3, 7, 101, 18]`

The `tails` vector evolves as follows:

- `10` → `[10]`
- `9` → `[9]`
- `2` → `[2]`
- `5` → `[2, 5]`
- `3` → `[2, 3]`
- `7` → `[2, 3, 7]`
- `101` → `[2, 3, 7, 101]`
- `18` → `[2, 3, 7, 18]`

The final vector has size `4`, so the answer is `4`.

## Complexity Analysis

**Time Complexity:** `O(N log N)`, because each element requires a binary search.

**Space Complexity:** `O(N)` in the worst case, when the input array is strictly increasing.