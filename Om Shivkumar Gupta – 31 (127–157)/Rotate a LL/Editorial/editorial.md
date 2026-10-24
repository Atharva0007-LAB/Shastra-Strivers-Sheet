# 149. Rotate a LL - Editorial

## Problem Statement
Given the head of a singly linked list, rotate the list to the right by `K` places.

---

## Sample Example

### Sample Input
```text
5 2
1 2 3 4 5
```

### Sample Output
```text
4 5 1 2 3
```

### Explanation
* Rotate 1 step right: 5 -> 1 -> 2 -> 3 -> 4
* Rotate 2 steps right: 4 -> 5 -> 1 -> 2 -> 3

---

## Intuition
Rotating a list of length N by K is equivalent to rotating by `K % N`.
If we connect the end of the list to the head, the list becomes a circular loop.
The new head will be located at index `N - (K % N)` (0-indexed from the original head), and the node right before it becomes the new tail.
We can simply walk to this new tail, save the reference to the new head, and sever the circular link.

## Approach
1. Handle base cases: if `head == null` or `head.next == null` or `k == 0`, return `head`.
2. Traverse to the tail of the list while counting the total length `len`.
3. Connect `tail.next = head` to form a circular ring.
4. Compute effective rotation `k = k % len`.
5. Advance `len - k` steps from the tail to reach the new tail node.
6. The new head is `newTail.next`.
7. Set `newTail.next = null` to break the ring and return `newHead`.

---

## Complexity Analysis
* **Time Complexity:** O(N) because we traverse the list to find the length and then traverse at most N nodes to break the loop.
* **Space Complexity:** O(1) auxiliary space as only a few pointer variables are used.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /**
     * Rotates linked list to the right by K places in O(N) time and O(1) space.
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Calculate the length and locate the tail node
        ListNode tail = head;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Make the list circular
        tail.next = head;

        // Step 3: Find the new tail at position (length - (k % length) - 1)
        k = k % length;
        int stepsToNewTail = length - k;
        ListNode newTail = tail;
        while (stepsToNewTail-- > 0) {
            newTail = newTail.next;
        }

        // Step 4: Break the circle
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();

        if (n == 0) return;

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }

        Solution sol = new Solution();
        ListNode rotatedHead = sol.rotateRight(dummy.next, k);

        curr = rotatedHead;
        while (curr != null) {
            System.out.print(curr.val + (curr.next == null ? "" : " "));
            curr = curr.next;
        }
        System.out.println();
        sc.close();
    }
}
```
