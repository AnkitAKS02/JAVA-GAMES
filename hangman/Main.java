package hangman;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Main{

    static final String[] WORDS = {
        "apple", "banana", "computer", "programming", "java",
        "hangman", "variable", "function", "object", "class",
        "inheritance", "array", "string", "keyboard", "mouse"
    };
    public static String getWord(){
        int size = WORDS.length;
        return WORDS[ThreadLocalRandom.current().nextInt(0,size)];
    }
    public static boolean containsUnderscore(char[] dup){
        for(int val:dup) if(val == '_') return true;
        return false;
    }
    public static void printDup(char[] dup){
        for(char val : dup){
            System.out.print(val);
        }
        System.out.println();
    }
    static void revealLetter(String secret, char[] mask, char ch) {
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == ch) mask[i] = ch;
        }
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String secret = getWord();
        char[] dup =  new char[secret.length()];
        Arrays.fill(dup,'_');
        int turns = 5;
        Set<Character> correctGuesses = new LinkedHashSet<>();
        Set<Character> wrongGuesses = new LinkedHashSet<>();
        while(turns > 0 && containsUnderscore(dup)){
            printDup(dup);
            System.out.println("Turns left: " + turns);
            System.out.println("Wrong guesses: " + wrongGuesses);
            System.out.print("Guess a letter or the whole word: ");
            String userGuess = sc.next().trim().toLowerCase();

            if(userGuess.length() == 1){
                char ch = userGuess.charAt(0);
                if(correctGuesses.contains(ch) || wrongGuesses.contains(ch)){
                    System.out.println("you have already guessed this"+ch+ " word/letter");
                    continue;
                }
                if (!Character.isLetter(ch)) {
                    System.out.println("Please enter a letter (a-z).");
                    continue;
                }

                else if(secret.indexOf(ch) >= 0){
                    correctGuesses.add(ch);
                    revealLetter(secret, dup, ch);
                    System.out.println("you have got one letter ,way to go for others");
                } else {
                    wrongGuesses.add(ch);
                    turns--;
                    System.out.println("OOPS!! Wrong guess.TRY ONE MORE TIME");
                }
            }else{
                if (userGuess.equals(secret)) {
                    // reveal whole word and win
                    for (int i = 0; i < secret.length(); i++) dup[i] = secret.charAt(i);
                    break;
                } else {
                    turns--;
                    System.out.println("You have lose you turn in Wrong word guess!");
                }
            }
        }
        if(!containsUnderscore(dup)){
            System.out.println("You have won this game , but the next game is mine");
        }else{
            System.out.println("HHHHHAAAAAA ,i guess i am better than you");
        }
        sc.close();
    }
}
