#include <bits/stdc++.h>
using namespace std;

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

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> nums(n);

    for (int i = 0; i < n; i++) {
        cin >> nums[i];
    }

    Solution solution;
    cout << solution.LIS(nums) << '\n';

    return 0;
}