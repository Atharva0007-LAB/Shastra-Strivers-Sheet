import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(155);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            List<String> ops = new ArrayList<>();

            if (tc == 0) {
                ops.add("1 1"); ops.add("1 2"); ops.add("3"); ops.add("2"); ops.add("4");
            } else if (tc == 1) {
                ops.add("4"); ops.add("2"); ops.add("3");
            } else if (tc == 2) {
                ops.add("1 100"); ops.add("4"); ops.add("3"); ops.add("2"); ops.add("4");
            } else if (tc == 3) {
                for (int i = 0; i < 4; i++) ops.add("1 " + i);
                for (int i = 0; i < 4; i++) ops.add("2");
            } else if (tc == 4) {
                ops.add("1 -10"); ops.add("1 -20"); ops.add("2"); ops.add("1 30"); ops.add("3");
            } else {
                int q = (tc < 40) ? 50 + rand.nextInt(100) : 500 + rand.nextInt(1000);
                for (int i = 0; i < q; i++) {
                    int t = 1 + rand.nextInt(4);
                    if (t == 1) ops.add("1 " + (rand.nextInt(100000) - 50000));
                    else ops.add(String.valueOf(t));
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(ops.size());
            for (String s : ops) inPw.println(s);
            inPw.close();

            Deque<Integer> stack = new ArrayDeque<>();
            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

            for (String s : ops) {
                String[] p = s.split(" ");
                int t = Integer.parseInt(p[0]);
                if (t == 1) stack.push(Integer.parseInt(p[1]));
                else if (t == 2) outPw.println(stack.isEmpty() ? -1 : stack.pop());
                else if (t == 3) outPw.println(stack.isEmpty() ? -1 : stack.peek());
                else if (t == 4) outPw.println(stack.isEmpty() ? 1 : 0);
            }
            outPw.close();
        }
        System.out.println("Implement Stack using Queue: 100 test cases generated.");
    }
}
