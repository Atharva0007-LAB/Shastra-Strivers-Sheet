// Intersection of Two Sorted Arrays - Solution/Solution.java
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
        int count = 0;
        int last = 0; // values are >= 1, so 0 means "nothing added yet"
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] < b[j]) {
                i++;
            } else if (a[i] > b[j]) {
                j++;
            } else {
                if (a[i] != last) {
                    if (count > 0) line.append(" ");
                    line.append(a[i]);
                    last = a[i];
                    count++;
                }
                i++;
                j++;
            }
        }

        if (count == 0) System.out.println(-1);
        else System.out.println(line);
    }
}