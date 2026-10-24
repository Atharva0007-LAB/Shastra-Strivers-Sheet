# 134. Clone a LL with Random and Next Pointer - Editorial

## Problem Statement
A linked list of length `N` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a deep copy of the list. The deep copy should consist of exactly `N` brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

---

## Sample Example

### Sample Input
```text
5
7 -1
13 0
11 4
10 2
1 0
```

### Sample Output
```text
7 -1
13 0
11 4
10 2
1 0
```

### Explanation
* The cloned list has 5 distinct nodes whose values and random relationships perfectly mirror the original list.

---

## Intuition
A basic approach uses an auxiliary HashMap to map `originalNode -> copyNode`, which takes O(N) extra space.

To optimize auxiliary space to O(1), we can interleave each copied node directly after its corresponding original node in the list:
`original -> copy -> next_original -> next_copy`

Because each copy node is directly adjacent to its original node:
`copy.random = original.random.next`
This eliminates the need for any lookup hash table!

## Approach
1. **Pass 1 (Interweaving):**
   Iterate through the original list. For each node `curr`, instantiate a new copy node `copy` with value `curr.val`. Insert `copy` between `curr` and `curr.next`.
2. **Pass 2 (Assigning Random Pointers):**
   Traverse the weaved list. For each original node `curr`, set:
   `curr.next.random = (curr.random != null) ? curr.random.next : null`.
3. **Pass 3 (Separating the Lists):**
   Unweave the interleaved list back into the original list and the cloned copy list by restoring `curr.next` pointers.
4. Return the head of the copied list.

---

## Complexity Analysis
* **Time Complexity:** O(N) because the algorithm makes 3 linear passes over the list.
* **Space Complexity:** O(1) auxiliary space as the pointers are manipulated in-place without any hash map.

---

## Reference Implementation

### Java Solution
```java
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
```
