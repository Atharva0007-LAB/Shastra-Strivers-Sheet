import java.util.*;

public class Solution {
    /**
     * Groups words that are anagrams of each other.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sortedKey = new String(chars);

            map.putIfAbsent(sortedKey, new ArrayList<>());
            map.get(sortedKey).add(s);
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> group : map.values()) {
            Collections.sort(group);
            result.add(group);
        }

        // Sort groups by their first element for deterministic order
        Collections.sort(result, (a, b) -> a.get(0).compareTo(b.get(0)));

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = sc.next();
        }

        Solution sol = new Solution();
        List<List<String>> grouped = sol.groupAnagrams(strs);

        for (List<String> group : grouped) {
            System.out.print("[");
            for (int i = 0; i < group.size(); i++) {
                System.out.print(group.get(i) + (i == group.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
