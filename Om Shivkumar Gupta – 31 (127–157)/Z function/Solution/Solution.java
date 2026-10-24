import java.util.Scanner;

public class Solution {
    /**
     * Computes the Z-array in linear O(N) time.
     */
    public int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int[] z = sol.zFunction(s);

        for (int i = 0; i < z.length; i++) {
            System.out.print(z[i] + (i == z.length - 1 ? "" : " "));
        }
        System.out.println();
        sc.close();
    }
}
