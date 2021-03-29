import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] fromInput = input.split("");
        if (fromInput[0].equals(fromInput[3]) && fromInput[1].equals(fromInput[2])) {
            System.out.println(1);
        } else {
            Random random = new Random();
            System.out.println(random.nextInt(100));
        }
    }
}