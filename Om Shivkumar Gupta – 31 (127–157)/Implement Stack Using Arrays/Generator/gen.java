import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(144);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            List<String> ops = new ArrayList<>();

            if (tc == 0) {
                ops.add("1 10"); ops.add("1 20"); ops.add("3");
                ops.add("2"); ops.add("4"); ops.add("2");
            } else if (tc == 1) {
                ops.add("2"); ops.add("3"); ops.add("4");
            } else if (tc == 2) {
                ops.add("1 5"); ops.add("4"); ops.add("2"); ops.add("4");
            } else if (tc == 3) {
                for (int i = 0; i < 5; i++) ops.add("1 " + (i * 10));
                for (int i = 0; i < 5; i++) ops.add("2");
            } else if (tc == 4) {
                ops.add("1 -100"); ops.add("3"); ops.add("1 200"); ops.add("3"); ops.add("2"); ops.add("2");
            } else {
                int q = (tc < 40) ? 100 + rand.nextInt(200) : 1000 + rand.nextInt(2000);
                for (int i = 0; i < q; i++) {
                    int type = 1 + rand.nextInt(4);
                    if (type == 1) ops.add("1 " + (rand.nextInt(200000) - 100000));
                    else ops.add(String.valueOf(type));
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(ops.size());
            for (String line : ops) inPw.println(line);
            inPw.close();

            int[] stack = new int[ops.size() + 10];
            int top = -1;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (String line : ops) {
                String[] p = line.split(" ");
                int t = Integer.parseInt(p[0]);
                if (t == 1) {
                    stack[++top] = Integer.parseInt(p[1]);
                } else if (t == 2) {
                    outPw.println(top == -1 ? -1 : stack[top--]);
                } else if (t == 3) {
                    outPw.println(top == -1 ? -1 : stack[top]);
                } else if (t == 4) {
                    outPw.println(top == -1 ? 1 : 0);
                }
            }
            outPw.close();
        }
        System.out.println("Implement Stack Using Arrays: 100 test cases generated.");
    }
}
