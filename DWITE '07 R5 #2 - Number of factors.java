import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
         Scanner scanner = new Scanner(System.in);
         StringBuilder out = new StringBuilder();
         
         // input is 5 lines
         int[] num = new int[5];
         for (int i = 0; i < 5; i++) {
             num[i] = scanner.nextInt();
         }
         for (int i = 0; i < 5; i++) {
            System.out.println(factor(num[i]));
            
         }
    }
    
    private static int factor(int check) {
        int count = 0;
        
        while (check % 2 == 0) {
            count++;
            check /= 2;
        }
        
        for (int i = 3; i * i <= check; i+=2) {
            while (check % i == 0) {
                count++;
                check /= i;
            }
        }
        
        if (check > 2) {
            count++;
        }
        if (count == 1) {
            count = 0;
        }
        
        return count;
    }
}
