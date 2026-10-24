import java.util.*;

public class Solution {
    private boolean canEatAll(int[] piles, int speed, int h) {
        long totalHours = 0;
        for (int pile : piles) {
            totalHours += (pile + speed - 1) / speed;
            if (totalHours > h) return false;
        }
        return totalHours <= h;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;
        for (int pile : piles) high = Math.max(high, pile);

        int ans = high;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canEatAll(piles, mid, h)) {
                ans = mid;
                high = mid - 1; // Try smaller speed
            } else {
                low = mid + 1;  // Need faster speed
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int h = sc.nextInt();
        int[] piles = new int[n];
        for (int i = 0; i < n; i++) piles[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.minEatingSpeed(piles, h));
        sc.close();
    }
}
