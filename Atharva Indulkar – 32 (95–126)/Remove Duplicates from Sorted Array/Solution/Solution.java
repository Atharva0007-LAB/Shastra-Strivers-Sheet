// Remove Duplicates from Sorted Array - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = scanner.nextInt();

        int k = 1; // a[0..k-1] holds the unique elements found so far
        for (int i = 1; i < n; i++) {
            if (a[i] != a[k - 1]) {
                a[k] = a[i];
                k++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(k).append("\n");
        for (int i = 0; i < k; i++) {
            sb.append(a[i]);
            if (i != k - 1) sb.append(" ");
        }
        System.out.println(sb);
    }
}