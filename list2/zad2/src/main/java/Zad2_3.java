import java.util.Scanner;

public class Zad2_3 {
    public static void main(String[] args) {
        NetworkTester nt = new NetworkTester();
        System.out.print("Number of tests: ");
        Scanner s = new Scanner(System.in);
        System.out.println(nt.testNetwork(s.nextInt()));
    }
}
