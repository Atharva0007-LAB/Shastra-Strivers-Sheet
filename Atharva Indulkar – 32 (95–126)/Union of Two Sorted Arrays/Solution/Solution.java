// Union of Two Sorted Arrays - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] a = new int[n];
        int[] b = new int[m];
        for (int i = 0; i < n; i++) a[i] = scanner.nextInt();
        for (int j = 0; j < m; j++) b[j] = scanner.nextInt();

        StringBuilder line = new StringBuilder();
        int i = 0, j = 0;
        boolean hasLast = false;
        int last = 0;

        while (i < n && j < m) {
            int val;
            if (a[i] < b[j]) {
                val = a[i++];
            } else if (a[i] > b[j]) {
                val = b[j++];
            } else {
                val = a[i];
                i++;
                j++;
            }
            if (!hasLast || val != last) {
                if (hasLast) line.append(" ");
                line.append(val);
                last = val;
                hasLast = true;
            }
        }

        while (i < n) {
            int val = a[i++];
            if (!hasLast || val != last) {
                if (hasLast) line.append(" ");
                line.append(val);
                last = val;
                hasLast = true;
            }
        }

        while (j < m) {
            int val = b[j++];
            if (!hasLast || val != last) {
                if (hasLast) line.append(" ");
                line.append(val);
                last = val;
                hasLast = true;
            }
        }

        System.out.println(line);
    }
}
