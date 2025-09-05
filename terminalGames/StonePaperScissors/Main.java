package StonePaperScissors;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    // Get user move
    public static char getUserChoice(Scanner sc) {
        System.out.println("Enter your Choice: Rock, Paper, Scissors");
        while (true) {
            String str = sc.next().trim().toLowerCase();
            if (str.equals("r") || str.equals("rock")) return 'r';
            else if (str.equals("p") || str.equals("paper")) return 'p';
            else if (str.equals("s") || str.equals("scissors") || str.equals("scissor")) return 's';
            else {
                System.out.println("Invalid input. Type r/p/s or rock/paper/scissors.");
            }
        }
    }

    // Computer move
    public static char getComMove() {
        int random = ThreadLocalRandom.current().nextInt(0, 3);
        char arr[] = {'r', 'p', 's'};
        return arr[random];
    }

    // Convert char to word
    static String toWord(char m) {
        if (m == 'r') return "Rock";
        if (m == 'p') return "Paper";
        if (m == 's') return "Scissors";
        return "?";
    }

    // Decide winner
    static int decideWinner(char user, char comp) {
        if (user == comp) return 0;
        if ((user == 'r' && comp == 's') || 
            (user == 'p' && comp == 'r') || 
            (user == 's' && comp == 'p')) return 1;
        return -1;
    }

    // Main game loop
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int you = 0, computer = 0, draw = 0;

        System.out.println("=== Let's play Rock–Paper–Scissors ===");

        while (true) {
            char userMove = getUserChoice(sc);
            char compMove = getComMove();

            System.out.println("You: " + toWord(userMove) + "  |  Computer: " + toWord(compMove));

            int result = decideWinner(userMove, compMove);
            if (result == 1) {
                System.out.println("You win this round! ");
                you++;
            } else if (result == -1) {
                System.out.println("Computer wins this round.");
                computer++;
            } else {
                System.out.println("It's a draw.");
                draw++;
            }

            System.out.println("Your score: " + you + " | Computer: " + computer + " | Draws: " + draw);
            System.out.print("Play again? (y/n): ");
            String ans = sc.next().trim().toLowerCase();
            if (!ans.startsWith("y")) break;
        }

        System.out.println("\n=== Final Score ===");
        System.out.println("You: " + you + " | Computer: " + computer + " | Draws: " + draw);
        if (you > computer) System.out.println(" You won the game! Hurray!");
        else if (you < computer) System.out.println(" Computer wins overall.");
        else System.out.println("It's a tie overall!");
        System.out.println("Thanks for playing!");
        sc.close();
    }
}
