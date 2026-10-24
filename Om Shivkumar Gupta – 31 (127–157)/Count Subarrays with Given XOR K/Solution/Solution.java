import java.util.*;

public class Solution {
    /**
     * Counts the total number of continuous subarrays with XOR equal to B.
     * Uses prefix XORs and a HashMap for O(N) time complexity.
     */
    public int solve(int[] A, int B) {
        int count = 0;
        int currentXor = 0;
        Map<Integer, Integer> xorFreq = new HashMap<>();
        xorFreq.put(0, 1);

        for (int x : A) {
            currentXor ^= x;
            int requiredXor = currentXor ^ B;
            if (xorFreq.containsKey(requiredXor)) {
                count += xorFreq.get(requiredXor);
            }
            xorFreq.put(currentXor, xorFreq.getOrDefault(currentXor, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int b = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.solve(a, b);
        System.out.println(ans);
        sc.close();
    }
}
