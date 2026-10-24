import java.util.*;

public class Solution {
    private int numberOfGasStationsRequired(double dist, int[] stations) {
        int count = 0;
        for (int i = 1; i < stations.length; i++) {
            int numberInBetween = (int) ((stations[i] - stations[i - 1]) / dist);
            if ((stations[i] - stations[i - 1]) == numberInBetween * dist) {
                numberInBetween--;
            }
            count += numberInBetween;
        }
        return count;
    }

    public double findSmallestMaxDist(int[] stations, int k) {
        int n = stations.length;
        double low = 0;
        double high = 0;
        for (int i = 0; i < n - 1; i++) {
            high = Math.max(high, (double) (stations[i + 1] - stations[i]));
        }

        double diff = 1e-6;
        while (high - low > diff) {
            double mid = (low + high) / (2.0);
            int count = numberOfGasStationsRequired(mid, stations);
            if (count > k) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] stations = new int[n];
        for (int i = 0; i < n; i++) stations[i] = sc.nextInt();

        Solution sol = new Solution();
        double res = sol.findSmallestMaxDist(stations, k);
        System.out.printf(Locale.US, "%.6f\n", res);
        sc.close();
    }
}
