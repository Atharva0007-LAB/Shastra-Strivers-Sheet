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
private:
    void inorder(TreeNode* root, vector<int>& values) {
        if (root == nullptr)
            return;

        inorder(root->left, values);
        values.push_back(root->val);
        inorder(root->right, values);
    }

public:
    vector<int> findKthElements(TreeNode* root, int k) {
        vector<int> values;
        inorder(root, values);

        int n = values.size();

        if (k < 1 || k > n)
            return {-1, -1};

        return {values[k - 1], values[n - k]};
    }
};

TreeNode* buildTree(const vector<string>& nodes) {
    if (nodes.empty() || nodes[0] == "null")
        return nullptr;

    TreeNode* root = new TreeNode(stoi(nodes[0]));
    queue<TreeNode*> q;
    q.push(root);

    int i = 1;

    while (!q.empty() && i < (int)nodes.size()) {
        TreeNode* current = q.front();
        q.pop();

        if (i < (int)nodes.size() && nodes[i] != "null") {
            current->left = new TreeNode(stoi(nodes[i]));
            q.push(current->left);
        }
        i++;

        if (i < (int)nodes.size() && nodes[i] != "null") {
            current->right = new TreeNode(stoi(nodes[i]));
            q.push(current->right);
        }
        i++;
    }

    return root;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string line;

    // Read the tree and k from a single line.
    if (!getline(cin, line))
        return 0;

    stringstream ss(line);
    vector<string> tokens;
    string token;

    while (ss >> token)
        tokens.push_back(token);

    if (tokens.size() < 2)
        return 0;

    int k = stoi(tokens.back());
    tokens.pop_back();

    TreeNode* root = buildTree(tokens);

    Solution sol;
    vector<int> result = sol.findKthElements(root, k);

    cout << result[0] << " " << result[1] << '\n';

    return 0;
}