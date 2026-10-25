// Leaders in an Array - Solution/Solution.java
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

        List<Integer> leaders = new ArrayList<>();
        int maxRight = Integer.MIN_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] >= maxRight) {
                leaders.add(nums[i]);
                maxRight = nums[i];
            }
        }

        Collections.reverse(leaders);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < leaders.size(); i++) {
            sb.append(leaders.get(i));
            if (i != leaders.size() - 1) sb.append(" ");
        }
        System.out.println(sb);
    }
}
