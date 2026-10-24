import java.util.Scanner;

public class Solution {
    /**
     * Finds the repeating and missing numbers in O(N) time and O(1) space using Mathematics.
     * 
     * @return Array of size 2: [repeating, missing]
     */
    public int[] findTwoElement(int[] nums, int n) {
        long N = n;
        long sumN = (N * (N + 1)) / 2;
        long sumSqN = (N * (N + 1) * (2 * N + 1)) / 6;

        long sumArr = 0;
        long sumSqArr = 0;

        for (int x : nums) {
            sumArr += (long) x;
            sumSqArr += (long) x * (long) x;
        }

        // diff = repeating - missing
        long diff = sumArr - sumN;

        // sqDiff = repeating^2 - missing^2 = (repeating - missing) * (repeating + missing)
        long sqDiff = sumSqArr - sumSqN;

        // sumVal = repeating + missing
        long sumVal = sqDiff / diff;

        long repeating = (diff + sumVal) / 2;
        long missing = repeating - diff;

        return new int[]{(int) repeating, (int) missing};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int[] ans = sol.findTwoElement(nums, n);
        System.out.println(ans[0] + " " + ans[1]);
        sc.close();
    }
}
