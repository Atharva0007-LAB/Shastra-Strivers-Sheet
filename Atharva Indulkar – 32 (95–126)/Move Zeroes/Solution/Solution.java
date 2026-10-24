import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = scanner.nextInt();

            int pos = 0; // where the next non-zero element should go
            for (int i = 0; i < n; i++) {
                if (a[i] != 0) {
                    int tmp = a[pos];
                    a[pos] = a[i];
                    a[i] = tmp;
                    pos++;
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(a[i]);
                sb.append(i != n - 1 ? " " : "\n");
            }
        }
        System.out.print(sb);
    }
}
