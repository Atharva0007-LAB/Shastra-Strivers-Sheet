import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(128);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                nums = new int[]{1, 2, 2};
            } else if (tc == 1) {
                nums = new int[]{0};
            } else if (tc == 2) {
                nums = new int[]{};
            } else if (tc == 3) {
                nums = new int[]{1, 1, 1, 1};
            } else if (tc == 4) {
                nums = new int[]{-1, 2, -1, 2};
            } else {
                // Stress cases: heavy duplicates to kill naive exponential branchers
                n = 4 + (tc % 7); // n between 4 and 10
                nums = new int[n];
                int uniqueCount = 1 + rand.nextInt(3);
                int[] pool = new int[uniqueCount];
                for (int i = 0; i < uniqueCount; i++) pool[i] = rand.nextInt(15) - 7;
                for (int i = 0; i < n; i++) {
                    nums[i] = pool[rand.nextInt(uniqueCount)];
                }
            }

            n = nums.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    inPw.print(nums[i] + (i == n - 1 ? "" : " "));
                }
                inPw.println();
            }
            inPw.close();

            // Optimal Subsets II
            Arrays.sort(nums);
            List<List<Integer>> result = new ArrayList<>();
            backtrack(0, nums, new ArrayList<>(), result);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (List<Integer> subset : result) {
                outPw.print("[");
                for (int i = 0; i < subset.size(); i++) {
                    outPw.print(subset.get(i) + (i == subset.size() - 1 ? "" : ", "));
                }
                outPw.println("]");
            }
            outPw.close();
        }
        System.out.println("Subsets II: 100 test cases generated.");
    }

    private static void backtrack(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            current.add(nums[i]);
            backtrack(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }
}
