import java.io.*;
import java.util.*;

public class Main {
    static class Item implements Comparable<Item> {
        public final int start, end;
        
        public Item(int s, int e) {
            this.start = s;
            this.end = e;
        }
        
        public int compareTo(Item that) {
            return this.start - that.start;
        }
    }
    
    public static void main(String[] args) throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        Item[] item = new Item[n];
        int minLength = 1000000; 
    
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            int parts = line.indexOf(' ');
            item[i] = new Item(Integer.parseInt(line.substring(0, parts)), Integer.parseInt(line.substring(parts + 1)));
            minLength = Math.min(minLength, item[i].end - item[i].start);
        }
    
        Arrays.sort(item);
        
        // binary search
        double left = 1, right = minLength;
        for (int j = 0; j < 50; j++) {
            double mid = (left + right) / 2;
            double last = item[0].start;
            boolean fail = false;
            
            // check if all intervals can be covered with mid
            for (Item i : item) {
                last = Math.max(last, i.start) + mid;
                if (last > i.end + 1e-9) {
                    fail = true;
                    break;
                }
                last = Math.min(last, i.end);
            }

            if (fail) {
                right = mid;
            } else {
                left = mid;
            }
        }

        findPrintOptimal(left, item, n);
    }
    
    private static void findPrintOptimal(double interval, Item[] item, int n) {
        int div = -1, num = -1;
    
        for (long div2 = 1; div2 <= n; div2++) {
            double res = div2 * interval;
            long iv = (long) (res + 0.5);
            // check if aprox an int and irreducible
            if (Math.abs(res - iv) < 1e-4 && gcd((int) div2, (int) iv) == 1) {
                long last = item[0].start * div2;
                boolean fail = false;

                // check if fits interval
                for (Item i : item) {
                    last = Math.max(last, i.start * div2) + iv;
                    if (last > i.end * div2) {
                        fail = true;
                        break;
                    }
                }

                // keep the best fraction found so far
                if (!fail) {
                    if (div == -1 || num * div2 < div * iv) {
                        div = (int)(div2);
                        num = (int)(iv);
                    }
                }
            }
        }

        System.out.println(num + "/" + div);
    }
    
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
