import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // reading N
        int N = Integer.parseInt(br.readLine());
        int[] ages = new int[N];
        
        // reading N space-separated integers x_i
        String[] ageInputs = br.readLine().trim().split(" "); 
        // inserting into ages array
        for (int i = 0; i < N; i++) {
            ages[i] = Integer.parseInt(ageInputs[i]);
        }
        
        Arrays.sort(ages);
        
        // Reading Q
        int Q = Integer.parseInt(br.readLine());
        
        StringBuilder out = new StringBuilder();
        
        // go through each pair comparisons -- binary search???
        for (int i = 0; i < Q; i++) {
            String[] comparisons = br.readLine().trim().split(" ");
            int a = Integer.parseInt(comparisons[0]);
            int b = Integer.parseInt(comparisons[1]);
            
            // find viewers btwn a and b
            int start = findStart(ages, a);
            int end = findEnd(ages, b);
            
            out.append(end - start).append("\n");
        }
        
        System.out.print(out.toString());
    }
    
    private static int findStart(int[] ages, int a) {
        int index = Arrays.binarySearch(ages, a);
        if (index < 0) {
            index = -index - 1;
        }
        return index;
    }
    
    private static int findEnd(int[] ages, int b) {
        int index = Arrays.binarySearch(ages, b);
        if (index < 0) {
            index = -index - 1;
        } else {
            while (index < ages.length && ages[index] == b) {
                index++;
            }
        }
        return index;
    }
}
