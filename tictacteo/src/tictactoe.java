/*
 * By: Michael MacDonald (and the thousands of people who did this first in every language ever)
 * Date: idk maybe 21/10/2021
 * Dude did you read the name of the program?! It is tictactoe! Just a game of tictactoe in the console
 */

import java.util.Scanner;

public class tictactoe {
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;
    int ActivePlayer = X_MOVE;

    Scanner scanner;
    String input;

    public tictactoe(){
        // === SETUP ===
        scanner = new Scanner(System.in);
        printBoard();
        System.out.println("WELCOME TO THE AMAZINGLY BAD GAME OF TIC! TAC! TOE!");
        System.out.println("*Insert Clapping Here*");
        System.out.println("Who even named it that???");
        // === END SETUP ===

        while(true){ // Loop the game

            printBoard();

            switch (ActivePlayer) {
                case X_MOVE:
                    System.out.println("Player X's Turn");
                    break;
                case O_MOVE:
                    System.out.println("Player O's Turn");
                    break;
            }

            while(true){ // If the play fails (bad input) ask the player again
                input = scanner.nextLine();
                if(input.length() != 2){
                    System.out.println("Please type the coordinates of an empty space in this format: RowCol (ex. 23)");
                } else if (input.charAt(0) != '0' && input.charAt(0) != '1' && input.charAt(0) != '2') {
                    System.out.println("Please type the coordinates of space in this format: RowCol (ex. 23)");
                }
                else if (input.charAt(1) != '0' && input.charAt(1) != '1' && input.charAt(1) != '2') {
                    System.out.println("Please type the coordinates of space in this format: RowCol (ex. 23)");
                } else {
                    String _rowChar = String.valueOf(input.charAt(0));
                    int _row = Integer.parseInt(_rowChar);
                    String _colChar = String.valueOf(input.charAt(1));
                    int _col = Integer.parseInt(_colChar);
                    board[_row][_col] = ActivePlayer;
                    break;
                }
            }

            if(checkWin()){
                System.out.print("YAY player ");
                if (ActivePlayer == X_MOVE) {
                    System.out.print("X");
                } else if (ActivePlayer == O_MOVE) {
                    System.out.print("O");
                }
                System.out.println(" won!");
                return; // stop game
            }

            switch (ActivePlayer) { // switch active player
                case X_MOVE:
                    ActivePlayer = O_MOVE;
                    break;
                case O_MOVE:
                    ActivePlayer = X_MOVE;
                    break;
            }

        }
        
        
    }

    public void printBoard(){
        System.out.println("  0 1 2");
        for (int row = 0; row < board.length; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < board.length; col++) {
                switch (board[row][col]) {
                    case X_MOVE:
                        System.out.print("X");
                        break;
                    case O_MOVE:
                        System.out.print("O");
                        break;
                    default:
                        System.out.print("-");
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean checkWin(){ // check if anyone won
        int countX = 0;
        int countO = 0;
        // check for all vertical wins
        for (int i = 0; i < 3; i++) { // check all the rows
            for (int s = 0; s < 3; s++) { // check the collums
                if (board[i][s] == X_MOVE) {
                    countX++;
                }
                if (board[i][s] == O_MOVE) {
                    countO++;
                }
            }
            if (countX == 3 || countO == 3){ // if there are 3 in a row verticaly someone won
                return true;
            }
        }
        countX = 0; //reset
        countO = 0;
        // check for all horizontal wins
        for (int i = 0; i < 3; i++) { // check all the collums
            for (int s = 0; s < 3; s++) { // check the collums
                if (board[s][i] == X_MOVE) {
                    countX++;
                }
                if (board[s][i] == O_MOVE) {
                    countO++;
                }
            }
            if (countX == 3 || countO == 3){ // if there are 3 in a row verticaly someone won
                return true;
            }
        }
        countX = 0; //reset
        countO = 0;
        // check diagonal wins (left to right)
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][s] == X_MOVE) {
                countX++;
            }
            if (board[i][s] == O_MOVE) {
                countO++;
            }
            s++;
        }
        if (countX == 3 || countO == 3){
            return true;
        }
        countX = 0; //reset
        countO = 0;
        // check diagonal wins (right to left)
        s = 2;
        for (int i = 0; i < 3; i++) {
            if (board[i][s] == X_MOVE) {
                countX++;
            }
            if (board[i][s] == O_MOVE) {
                countO++;
            }
            s--;
        }
        if (countX == 3 || countO == 3){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        new tictactoe();
    }
}
