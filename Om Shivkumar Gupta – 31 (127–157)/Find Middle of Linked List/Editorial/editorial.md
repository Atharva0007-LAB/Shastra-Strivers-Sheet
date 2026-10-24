# 157. Find Middle of Linked List - Editorial

## Problem Statement
Given the head of a singly linked list, return the middle node of the linked list.

If there are two middle nodes (i.e. even number of nodes), return the second middle node.

Print space-separated node values from the middle node to the end of the list.

---

## Sample Example

### Sample Input
```text
5
1 2 3 4 5
```

### Sample Output
```text
3 4 5
```

### Explanation
* The middle node of the list is 3.
* Printing from middle to end yields: 3 4 5.

---

## Intuition
A standard two-pass approach traverses the list once to count total length `N`, then walks `N / 2` steps to reach the middle.

We can achieve this in a single pass using the Tortoise and Hare approach (Two Pointers):
- `slow` moves 1 step at a time.
- `fast` moves 2 steps at a time.
When `fast` reaches the end of the list (`fast == null` or `fast.next == null`), `slow` is guaranteed to be at the exact middle (or second middle for even lengths)!

## Approach
1. Initialize `slow = head` and `fast = head`.
2. While `fast != null && fast.next != null`:
   - `slow = slow.next`
   - `fast = fast.next.next`
3. Return `slow`.

---

## Complexity Analysis
* **Time Complexity:** O(N) with a single pass through the list.
* **Space Complexity:** O(1) auxiliary space as only two pointers are used.

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
     * Finds the middle node using Tortoise and Hare (Slow and Fast pointers).
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n == 0) return;

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }

        Solution sol = new Solution();
        ListNode mid = sol.middleNode(dummy.next);

        curr = mid;
        while (curr != null) {
            System.out.print(curr.val + (curr.next == null ? "" : " "));
            curr = curr.next;
        }
        System.out.println();
        sc.close();
    }
}
```
