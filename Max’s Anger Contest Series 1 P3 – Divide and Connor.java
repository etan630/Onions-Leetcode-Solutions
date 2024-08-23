import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        
        int[] result = divConqHelper(arr);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }
    
    public static int[] divConqHelper(int[] arr){
    if (arr.length == 1) {
        return arr;
    }
    
    int third = arr.length / 3;
    int[] firstThird = Arrays.copyOfRange(arr, 0, third);
    int[] secondThird = Arrays.copyOfRange(arr, third, 2 * third);
    int[] thirdThird = Arrays.copyOfRange(arr, 2 * third, arr.length);
    
    firstThird = divConqHelper(firstThird);
    secondThird = divConqHelper(secondThird);
    thirdThird = divConqHelper(thirdThird);
    
    int[] merged = new int[arr.length];
    System.arraycopy(thirdThird, 0, merged, 0, third);
    System.arraycopy(firstThird, 0, merged, third, third);
    System.arraycopy(secondThird, 0, merged, 2 * third, third);
    
    return merged;
}
}
