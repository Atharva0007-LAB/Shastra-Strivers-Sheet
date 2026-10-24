#include <bits/stdc++.h>
using namespace std;

class TreeNode {
public:
    int val;
    TreeNode* left;
    TreeNode* right;

    TreeNode(int x) {
        val = x;
        left = right = nullptr;
    }
};

class Solution {
public:
    void inorder(TreeNode* root, vector<int>& ans) {
        if (root == nullptr)
            return;

        inorder(root->left, ans);
        ans.push_back(root->val);
        inorder(root->right, ans);
    }

    vector<int> inorderTraversal(TreeNode* root) {
        vector<int> ans;
        inorder(root, ans);
        return ans;
    }
};

TreeNode* buildTree(vector<string>& arr) {
    if (arr.empty() || arr[0] == "null")
        return nullptr;

    TreeNode* root = new TreeNode(stoi(arr[0]));
    queue<TreeNode*> q;
    q.push(root);

    int i = 1;

    while (!q.empty() && i < arr.size()) {
        TreeNode* curr = q.front();
        q.pop();

        // Left child
        if (i < arr.size() && arr[i] != "null") {
            curr->left = new TreeNode(stoi(arr[i]));
            q.push(curr->left);
        }
        i++;

        // Right child
        if (i < arr.size() && arr[i] != "null") {
            curr->right = new TreeNode(stoi(arr[i]));
            q.push(curr->right);
        }
        i++;
    }

    return root;
}

int main() {
    string input;
    getline(cin, input);

    stringstream ss(input);
    string token;
    vector<string> arr;

    // Read space-separated values
    while (ss >> token) {
        arr.push_back(token);
    }

    TreeNode* root = buildTree(arr);

    Solution obj;
    vector<int> ans = obj.inorderTraversal(root);

    // Print values without brackets or commas
    for (int i = 0; i < ans.size(); i++) {
        if (i > 0)
            cout << " ";
        cout << ans[i];
    }

    cout << endl;

    return 0;
}