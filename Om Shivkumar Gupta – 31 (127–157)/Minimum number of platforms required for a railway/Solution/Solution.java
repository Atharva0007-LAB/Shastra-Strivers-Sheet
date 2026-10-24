import java.util.*;

public class Solution {
    /**
     * Finds the minimum number of platforms required using Two Pointers on sorted events.
     */
    public int findPlatform(int[] arr, int[] dep, int n) {
        Arrays.sort(arr);
        Arrays.sort(dep);

        int platformsNeeded = 0;
        int maxPlatforms = 0;
        int i = 0, j = 0;

        while (i < n && j < n) {
            // If another train arrives before or at the departure of the earliest departing train
            if (arr[i] <= dep[j]) {
                platformsNeeded++;
                maxPlatforms = Math.max(maxPlatforms, platformsNeeded);
                i++;
            } else {
                platformsNeeded--;
                j++;
            }
        }

        return maxPlatforms;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] arr = new int[n];
        int[] dep = new int[n];

        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        for (int i = 0; i < n; i++) dep[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.findPlatform(arr, dep, n);
        System.out.println(ans);
        sc.close();
    }
}
