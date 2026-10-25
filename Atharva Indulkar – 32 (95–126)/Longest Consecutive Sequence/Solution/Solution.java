// Longest Consecutive Sequence - Solution/Solution.java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            System.out.println(0);
            return;
        }
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }

        Set<Integer> set = new HashSet<>(n);
        for (int i = 0; i < n; i++) set.add(scanner.nextInt());

        int maxLen = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) {
                int cur = x;
                int len = 1;
                while (set.contains(cur + 1)) {
                    cur++;
                    len++;
                }
                if (len > maxLen) maxLen = len;
            }
        }
        System.out.println(maxLen);
    }
}
