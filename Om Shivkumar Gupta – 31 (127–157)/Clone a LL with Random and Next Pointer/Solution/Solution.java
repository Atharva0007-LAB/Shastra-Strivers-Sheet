import java.util.*;

public class Solution {
    static class Node {
        int val;
        Node next;
        Node random;

        Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * Clones the linked list with next and random pointers in O(1) auxiliary space.
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;

        // Step 1: Create copy nodes and weave them into the original list
        // Original: A -> B -> C
        // Weaved:   A -> A' -> B -> B' -> C -> C'
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }

        // Step 2: Assign random pointers for the copy nodes
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        // Step 3: Separate original and cloned lists
        Node dummyHead = new Node(0);
        Node copyCurr = dummyHead;
        curr = head;

        while (curr != null) {
            Node nextOriginal = curr.next.next;

            // Extract copy node
            Node copy = curr.next;
            copyCurr.next = copy;
            copyCurr = copy;

            // Restore original link
            curr.next = nextOriginal;
            curr = nextOriginal;
        }

        return dummyHead.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n == 0) return;

        Node[] nodes = new Node[n];
        int[] randomIndices = new int[n];

        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            int randIdx = sc.nextInt();
            nodes[i] = new Node(val);
            randomIndices[i] = randIdx;
        }

        for (int i = 0; i < n; i++) {
            if (i < n - 1) nodes[i].next = nodes[i + 1];
            if (randomIndices[i] != -1) nodes[i].random = nodes[randomIndices[i]];
        }

        Solution sol = new Solution();
        Node clonedHead = sol.copyRandomList(nodes[0]);

        // Map cloned nodes to their indices
        List<Node> clonedList = new ArrayList<>();
        Map<Node, Integer> clonedMap = new HashMap<>();
        Node c = clonedHead;
        int idx = 0;
        while (c != null) {
            clonedList.add(c);
            clonedMap.put(c, idx++);
            c = c.next;
        }

        for (Node node : clonedList) {
            int randIdx = node.random == null ? -1 : clonedMap.get(node.random);
            System.out.println(node.val + " " + randIdx);
        }
        sc.close();
    }
}
