import java.util.Scanner;

public class Solution {
    static class Node {
        Node[] links = new Node[26];

        boolean containsKey(char ch) {
            return links[ch - 'a'] != null;
        }

        Node get(char ch) {
            return links[ch - 'a'];
        }

        void put(char ch, Node node) {
            links[ch - 'a'] = node;
        }
    }

    /**
     * Counts distinct substrings using a Trie in O(N^2) time.
     */
    public int countDistinctSubstrings(String s) {
        Node root = new Node();
        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            Node node = root;
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                if (!node.containsKey(ch)) {
                    count++; // Each new Trie node represents a brand new unique substring
                    node.put(ch, new Node());
                }
                node = node.get(ch);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int ans = sol.countDistinctSubstrings(s);
        System.out.println(ans);
        sc.close();
    }
}
