import java.util.*;

public class Solution {
    private void backtrack(int open, int close, int n, StringBuilder sb, List<String> result) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }

        // Can add open parenthesis if remaining open count < n
        if (open < n) {
            sb.append('(');
            backtrack(open + 1, close, n, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }

        // Can add close parenthesis only if close < open
        if (close < open) {
            sb.append(')');
            backtrack(open, close + 1, n, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(0, 0, n, new StringBuilder(), result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        Solution sol = new Solution();
        List<String> ans = sol.generateParenthesis(n);

        for (String s : ans) {
            System.out.println(s);
        }
        sc.close();
    }
}
