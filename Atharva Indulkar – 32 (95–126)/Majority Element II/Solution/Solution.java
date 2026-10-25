// Majority Element II - Solution/Solution.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

        int cand1 = 0, count1 = 0;
        int cand2 = 0, count2 = 0;

        for (int x : nums) {
            if (count1 > 0 && x == cand1) {
                count1++;
            } else if (count2 > 0 && x == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = x;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = x;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        int actual1 = 0, actual2 = 0;
        for (int x : nums) {
            if (count1 > 0 && x == cand1) actual1++;
            else if (count2 > 0 && x == cand2) actual2++;
        }

        List<Integer> res = new ArrayList<>();
        int threshold = n / 3;
        if (count1 > 0 && actual1 > threshold) res.add(cand1);
        if (count2 > 0 && actual2 > threshold) res.add(cand2);
        Collections.sort(res);

        if (res.isEmpty()) {
            System.out.println(-1);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < res.size(); i++) {
                sb.append(res.get(i));
                if (i != res.size() - 1) sb.append(" ");
            }
            System.out.println(sb);
        }
    }
}
