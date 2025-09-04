package DiceRoller;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("🎲 Welcome to Dice Roller Simulator!");
        int rolls = 0;

        while (true) {
            System.out.print("Press 'r' to roll the dice or 'q' to quit: ");
            String input = sc.next().trim().toLowerCase();

            if (input.equals("q")) {
                System.out.println("Game Over! You rolled " + rolls + " times.");
                break;
            } else if (input.equals("r")) {
                int dice = ThreadLocalRandom.current().nextInt(1, 7); // 1–6
                rolls++;
                System.out.println("You rolled: " + dice + " 🎲");
            } else {
                System.out.println("Invalid choice! Type 'r' or 'q'.");
            }
        }

        sc.close();
    }
}
