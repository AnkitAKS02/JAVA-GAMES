package GuessTheNumber;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Main{
     public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        do{
            int randomNum = ThreadLocalRandom.current().nextInt(1,101);
            int attempts = 0;
            System.out.println("Our system has guessed a number you also guess one and lets play:");

            while(true){
                System.out.println("Your Guess:");
                if(!sc.hasNextInt()){
                    sc.next();//removes the char from terminal
                    System.out.println("Please enter a valid integer.");
                    continue;
                }

                int guess = sc.nextInt();
                attempts++;

                if (guess < 1 || guess > 100) {
                    System.out.println("Out of range! Guess between 1 and 100.");
                } else if (guess < randomNum) {
                    System.out.println("Too low! 📉");
                } else if (guess > randomNum) {
                    System.out.println("Too high! 📈");
                } else {
                    System.out.println("🎉 Correct! You got it in " + attempts + " guesses.");
                    break;
                }
            }

            System.out.println("Play again?(y/n):");
            String ans = sc.next().trim().toLowerCase();
            if(!ans.startsWith("y")) {
                System.out.println("Thanks for playing witht us.");
                break;
            };
            
        }while(true);
        sc.close();
    }
}