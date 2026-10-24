import java.util.*;

public class Solution {
    /**
     * Finds all palindrome partitions using backtracking.
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(0, s, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, String s, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backtrack(end + 1, s, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return;

        Solution sol = new Solution();
        List<List<String>> partitions = sol.partition(s);

        for (List<String> part : partitions) {
            System.out.print("[");
            for (int i = 0; i < part.size(); i++) {
                System.out.print(part.get(i) + (i == part.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
