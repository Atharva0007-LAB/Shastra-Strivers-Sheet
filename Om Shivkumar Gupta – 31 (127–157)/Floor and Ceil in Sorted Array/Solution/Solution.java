import java.util.*;

public class Solution {
    /**
     * Finds floor and ceil of x in sorted array arr.
     * Floor: largest element <= x (-1 if none)
     * Ceil: smallest element >= x (-1 if none)
     */
    public int[] getFloorAndCeil(int[] arr, int n, int x) {
        int floor = -1;
        int ceil = -1;

        // Binary search for floor
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                floor = arr[mid];
                low = mid + 1; // Try to find a larger element <= x
            } else {
                high = mid - 1;
            }
        }

        // Binary search for ceil
        low = 0; high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                ceil = arr[mid];
                high = mid - 1; // Try to find a smaller element >= x
            } else {
                low = mid + 1;
            }
        }

        return new int[]{floor, ceil};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int x = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        int[] res = sol.getFloorAndCeil(arr, n, x);
        System.out.println(res[0] + " " + res[1]);
        sc.close();
    }
}
