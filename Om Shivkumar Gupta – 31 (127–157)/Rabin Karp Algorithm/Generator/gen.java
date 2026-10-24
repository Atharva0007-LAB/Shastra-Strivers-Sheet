import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(141);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            String text, pattern;

            if (tc == 0) {
                text = "GEEKS FOR GEEKS";
                pattern = "GEEK";
            } else if (tc == 1) {
                text = "A"; pattern = "A";
            } else if (tc == 2) {
                text = "AAAAAA"; pattern = "AAA";
            } else if (tc == 3) {
                text = "ABCDEFGH"; pattern = "XYZ";
            } else if (tc == 4) {
                text = "ABABDABACDABABCABAB"; pattern = "ABABCABAB";
            } else {
                int n = (tc < 40) ? 100 + rand.nextInt(200) : 2000 + rand.nextInt(5000);
                int m = 3 + rand.nextInt(8);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) sb.append((char)('a' + rand.nextInt(4)));
                text = sb.toString();

                if (rand.nextBoolean()) {
                    int start = rand.nextInt(n - m);
                    pattern = text.substring(start, start + m);
                } else {
                    StringBuilder pb = new StringBuilder();
                    for (int i = 0; i < m; i++) pb.append((char)('a' + rand.nextInt(4)));
                    pattern = pb.toString();
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(text);
            inPw.println(pattern);
            inPw.close();

            // Match positions
            List<Integer> list = new ArrayList<>();
            int idx = text.indexOf(pattern);
            while (idx != -1) {
                list.add(idx);
                idx = text.indexOf(pattern, idx + 1);
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            if (list.isEmpty()) {
                outPw.println("-1");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    outPw.print(list.get(i) + (i == list.size() - 1 ? "" : " "));
                }
                outPw.println();
            }
            outPw.close();
        }
        System.out.println("Rabin Karp Algorithm: 100 test cases generated.");
    }
}
