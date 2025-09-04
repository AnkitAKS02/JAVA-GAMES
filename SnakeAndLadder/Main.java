package SnakeAndLadder;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Main{
    static int[] board = new int[101]; // 0 unused, 1-100 are valid squares
    public static void CreateBoard(){
        for (int i = 1; i <= 100; i++) {
            board[i] = i; // fill board with numbers
        }
        board[99] = 54; // snake
        board[70] = 55;
        board[52] = 42;
        board[25] = 2;
        board[95] = 72;

        board[6] = 25; // ladder
        board[11] = 40;
        board[60] = 85;
        board[46] = 90;
        board[17] = 69;

    }

    public static  int diceRoll(){
        return ThreadLocalRandom.current().nextInt(1,7);
    }

    public static int move(int pos, int dice){
        int curpos = pos + dice;

        if(curpos > 100) return pos;
        if(board[curpos] < curpos){
            System.out.println("you have been bit by a snake at:" + curpos);
            return board[curpos];
        }else if(board[curpos] > curpos){
            System.out.println("you have found a ladder at:" + curpos);
            return board[curpos];
        }
        return curpos;
    }

    public static void main(String args[]){
        CreateBoard();
        Scanner sc = new Scanner(System.in);
        int player1 = 0,player2 = 0;
        boolean isturnP1 = true;

        while(player1 <= 100 && player2 <= 100){
            System.out.println("\n" + (isturnP1 ? "Player 1" : "Player 2") + " turn! Press enter to roll dice...");
            sc.nextLine();//enter

            int dice = diceRoll();
            System.out.println("Rolled: " + dice);

            if (isturnP1) {
                player1 = move(player1, dice);
                System.out.println("Player 1 is at " + player1);
                if (player1 == 100) {
                    System.out.println(" Player 1 WINS!");
                    break;
                }
            } else {
                player2 = move(player2, dice);
                System.out.println("Player 2 is at " + player2);
                if (player2 == 100) {
                    System.out.println("Player 2 WINS!");
                    break;
                }
            }
            isturnP1 = !isturnP1;
            
        }
        System.out.println("Thanku for playing my game:");
        sc.close();
    }
}