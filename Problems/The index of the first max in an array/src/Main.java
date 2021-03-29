import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lengthOfArray = Integer.parseInt(scanner.nextLine());
        String input = scanner.nextLine();
        String[] fromInput = input.split(" ");
        int[] numbers = new int[lengthOfArray];
        for (int i = 0; i < fromInput.length; i++) {
            numbers[i] = Integer.parseInt(fromInput[i]);
        }
        System.out.println(getIndex(numbers));
    }

    private static int getIndex(int[] numbers) {
        int index = 0;
        int max = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
                index = i;
            }
        }
        return index;
    }
}

interface cat {
    void miauMiau();

}

abstract class Dog {
    static void gauGau() {
        System.out.println("Gau");
    }
}