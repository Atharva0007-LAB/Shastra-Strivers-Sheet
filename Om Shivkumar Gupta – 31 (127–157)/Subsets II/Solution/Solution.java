import java.util.*;

public class Solution {
    /**
     * Generates all unique subsets of an array with duplicates.
     * 
     * @param nums Array containing possibly duplicate integers.
     * @return List of all unique subsets.
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // Sort first so duplicates are adjacent
        Arrays.sort(nums);
        backtrack(0, nums, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            // Skip duplicate elements at the same recursion depth
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            current.add(nums[i]);
            backtrack(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("[]");
            return;
        }
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        List<List<Integer>> subsets = sol.subsetsWithDup(nums);

        for (List<Integer> subset : subsets) {
            System.out.print("[");
            for (int i = 0; i < subset.size(); i++) {
                System.out.print(subset.get(i) + (i == subset.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
