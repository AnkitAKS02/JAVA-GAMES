package ticktactoe;

import java.util.Scanner;

public class Main {

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
    }

    public static boolean checkWin(char[][] board, char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take player names
        System.out.print("Enter name for Player 1 (X): ");
        String player1 = sc.nextLine();
        System.out.print("Enter name for Player 2 (O): ");
        String player2 = sc.nextLine();

        // Board setup
        char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };

        int moves = 0;
        char currentPlayerSymbol = 'X';
        String currentPlayerName = player1;

        System.out.println("\nWelcome " + player1 + " (X) and " + player2 + " (O)!");
        System.out.println("Let's start the game 🎮\n");

        while (true) {
            printBoard(board);
            System.out.println(currentPlayerName + " (" + currentPlayerSymbol + "), enter row and column (0-2): ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2) {
                System.out.println("Invalid position! Try again.");
                continue;
            }

            if (board[row][col] != ' ') {
                System.out.println("Cell already taken! Try again.");
                continue;
            }

            board[row][col] = currentPlayerSymbol;
            moves++;

            if (checkWin(board, currentPlayerSymbol)) {
                printBoard(board);
                System.out.println( currentPlayerName + " wins!");
                break;
            }

            if (moves == 9) {
                printBoard(board);
                System.out.println("It's a draw 🤝");
                break;
            }

            // Switch player
            if (currentPlayerSymbol == 'X') {
                currentPlayerSymbol = 'O';
                currentPlayerName = player2;
            } else {
                currentPlayerSymbol = 'X';
                currentPlayerName = player1;
            }
        }

        sc.close();
    }
}
