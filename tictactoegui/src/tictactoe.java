/*
 * By: Michael MacDonald (and the thousands of people who did this first in every language ever)
 * Date: idk maybe 21/10/2021
 * Dude did you read the name of the program?! It is tictactoe! Just a game of tictactoe in the console
 */

import javax.swing.*;
import java.awt.*;

public class tictactoe {
    private static JFrame frame = new JFrame("Tic Tac Toe");

    // start menu
    private static JButton start = new JButton("START GAME");
    JPanel panelX = new JPanel();
    private static JLabel XNameDisplay = new JLabel();
    private static JTextField XNameInput = new JTextField("Player X");
    private static JButton XNameSet = new JButton("Set Name");
    JPanel panelO = new JPanel();
    private static JLabel ONameDisplay = new JLabel();
    private static JTextField ONameInput = new JTextField("Player O");
    private static JButton ONameSet = new JButton("Set Name");

    // game
    JPanel board = new JPanel();
    GridLayout grid = new GridLayout(4,4);
    JButton[][] buttons = new JButton[3][3];
    JTextField banner = new JTextField("Player X's Turn");

    // vars
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;
    int ActivePlayer = X_MOVE;

    String Xname = "Player X";
    String Oname = "Player O";

    int Xscore = 0;
    int Oscore = 0;
    boolean playing = true;

    public tictactoe(){
        // === SETUP ===
        board.setLayout(grid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);

        XNameDisplay.setText(Xname);
        ONameDisplay.setText(Oname);
        XNameDisplay.setPreferredSize(new Dimension(80, 20));
        XNameInput.setPreferredSize(new Dimension(80, 20));
        XNameSet.setPreferredSize(new Dimension(80, 20));
        ONameDisplay.setPreferredSize(new Dimension(80, 20));
        ONameInput.setPreferredSize(new Dimension(80, 20));
        ONameSet.setPreferredSize(new Dimension(80, 20));

        panelX.add(XNameDisplay, BorderLayout.NORTH);
        panelX.add(XNameInput, BorderLayout.CENTER);
        panelX.add(XNameSet, BorderLayout.SOUTH);
        panelO.add(ONameDisplay, BorderLayout.NORTH);
        panelO.add(ONameInput, BorderLayout.CENTER);
        panelO.add(ONameSet, BorderLayout.SOUTH);

        // buttons[0][0] = new JButton("-"); // row 0
        // buttons[0][1] = new JButton("1");
        // buttons[0][2] = new JButton("2");
        // buttons[0][3] = new JButton("3");
        // buttons[1][0] = new JButton("1"); // row 1
        // buttons[1][1] = new JButton("-");
        // buttons[1][2] = new JButton("-");
        // buttons[1][3] = new JButton("-");
        // buttons[2][0] = new JButton("2"); // row 2
        // buttons[2][1] = new JButton("-");
        // buttons[2][2] = new JButton("-");
        // buttons[2][3] = new JButton("-");
        // buttons[3][0] = new JButton("3"); // row 3
        // buttons[3][1] = new JButton("-");
        // buttons[3][2] = new JButton("-");
        // buttons[3][3] = new JButton("-");

        buttons[0][0] = new JButton("-"); // row 0
        buttons[0][1] = new JButton("-");
        buttons[0][2] = new JButton("-");
        buttons[1][0] = new JButton("-"); // row 1
        buttons[1][1] = new JButton("-");
        buttons[1][2] = new JButton("-");
        buttons[2][0] = new JButton("-"); // row 2
        buttons[2][1] = new JButton("-");
        buttons[2][2] = new JButton("-");

        // buttons[0][0].setBorderPainted( false );
        // buttons[0][0].setFocusPainted( false );
        // buttons[0][1].setBorderPainted( false );
        // buttons[0][1].setFocusPainted( false );
        // buttons[0][2].setBorderPainted( false );
        // buttons[0][2].setFocusPainted( false );
        // buttons[0][3].setBorderPainted( false );
        // buttons[0][3].setFocusPainted( false );
        // buttons[1][0].setBorderPainted( false );
        // buttons[1][0].setFocusPainted( false );
        // buttons[2][0].setBorderPainted( false );
        // buttons[2][0].setFocusPainted( false );
        // buttons[3][0].setBorderPainted( false );
        // buttons[3][0].setFocusPainted( false );

        
        
        // buttons[0][0].setEnabled(false);
        // buttons[0][1].setEnabled(false);
        // buttons[0][2].setEnabled(false);
        // buttons[0][3].setEnabled(false);
        // buttons[1][0].setEnabled(false);
        // buttons[2][0].setEnabled(false);
        // buttons[3][0].setEnabled(false);

        for (int i=0; i<3; i++) {
            for (int s=0; s<3; s++) {
                board.add(buttons[i][s]);
            }
        }

        // Add All Components To Window
        frame.getContentPane().add(panelX, BorderLayout.WEST);
        frame.getContentPane().add(start, BorderLayout.NORTH);
        frame.getContentPane().add(panelO, BorderLayout.EAST);
        // Set frame as visible
        frame.setVisible(true);

        while (true) { // loop game start menu
            frame.repaint();
            if (XNameSet.getModel().isPressed()){
                String content = XNameInput.getText();
                if (content != null) {
                    Xname = content;
                    XNameDisplay.setText(content);
                    XNameInput.setText(content);
                }
            }

            if (ONameSet.getModel().isPressed()){
                String content = ONameInput.getText();
                if (content != null) {
                    Oname = content;
                    ONameDisplay.setText(content);
                    ONameInput.setText(content);
                }
            }

            if (start.getModel().isPressed()){ // start game
                break;
            }
        }

        while(true){ // loop the game



            for (int i=0; i<3; i++) {
                for (int s=0; s<3; s++) {
                    buttons[i][s].setEnabled(true);
                    buttons[i][s].setText("-");
                }
            }

            frame.getContentPane().removeAll(); // clear
            // Add All Components To Window

            frame.repaint();
            frame.getContentPane().add(banner, BorderLayout.NORTH);
            frame.getContentPane().add(board, BorderLayout.CENTER);
            frame.setVisible(true);
            frame.repaint();

            playing = true;

            while(playing){ // Loop the round
                frame.repaint();
                // updateBoard();

                switch (ActivePlayer) {
                    case X_MOVE:

                        banner.setText(Xname + ": " + Xscore + "    " + Oname + ": " + Oscore + "    " + Xname + "'s Turn");
                        break;
                    case O_MOVE:
                        banner.setText(Xname + ": " + Xscore + "    " + Oname + ": " + Oscore + "    " + Oname + "'s Turn");
                        break;
                }
                boolean waiting = true;
                while(waiting){ // wait for player to input
                    for (int i = 0; i < 3; i++) {
                        for (int s = 0; s < 3; s++) {
                            if(buttons[i][s].getModel().isPressed()){ // check for button press
                                if (ActivePlayer == X_MOVE) {
                                    buttons[i][s].setText("X");
                                } else {
                                    buttons[i][s].setText("O");
                                }
                                buttons[i][s].setEnabled(false); // disable button
                                waiting = false;
                            }
                        }
                    }
                }

                if(checkWin()){
                    // String text = "YAY ";
                    // // banner.setText("YAY player ");
                    // if (ActivePlayer == X_MOVE) {
                    //     // banner.setText("X");
                    //     text += Xname;
                    // } else if (ActivePlayer == O_MOVE) {
                    //     // banner.setText("O");
                    //     text += Oname;
                    // }
                    // // banner.setText(" won!");
                    // text += " won!";
                    // banner.setText(text);
                    // for (int i=0; i<3; i++) {
                    //     for (int s=0; s<3; s++) {
                    //         buttons[i][s].setEnabled(false); // Disable all buttons
                    //     }
                    // }
                    // return; // stop game

                    // banner.setText(Xname + ": " + Xscore + "    " + Oname + ": " + Oscore + "    " + "YAY player ");

                    switch (ActivePlayer) { // switch active player
                        case X_MOVE:
                            Xscore++;
                            break;
                        case O_MOVE:
                            Oscore++;
                            break;
                    }

                    playing = false;
                }

                switch (ActivePlayer) { // switch active player
                    case X_MOVE:
                        ActivePlayer = O_MOVE;
                        break;
                    case O_MOVE:
                        ActivePlayer = X_MOVE;
                        break;
                }
            } // round loop

        } // game loop

    }


    public boolean checkWin(){ // check if anyone won
        int countX = 0;
        int countO = 0;
        // check for all vertical wins
        for (int i = 0; i < 3; i++) { // check all the rows
            // for (int s = 0; s < 3; s++) { // check the collums
            //     if(!buttons[i][s].getText().equals("-")){
            //         if (buttons[i][s].getText().equals("X")) {
            //             countX++;
            //         }
            //         if (buttons[i][s].getText().equals("O")) {
            //             countO++;
            //         }
            //     }
            // }
            // if (countX == 3 || countO == 3){ // if there are 3 in a row verticaly someone won
            //     return true;
            // }

            if (buttons[i][0].getText().equals("X")&&buttons[i][1].getText().equals("X")&&buttons[i][2].getText().equals("X")) {
                return true;
            }
            if (buttons[i][0].getText().equals("O")&&buttons[i][1].getText().equals("O")&&buttons[i][2].getText().equals("O")) {
                return true;
            }
        }
        countX = 0; //reset
        countO = 0;
        // check for all horizontal wins
        for (int i = 0; i < 3; i++) { // check all the collums
            // for (int s = 0; s < 3; s++) { // check the collums
            //     if(!buttons[s][i].getText().equals("-")){
            //         if (buttons[s][i].getText().equals("X")) {
            //             countX++;
            //         }
            //         if (buttons[s][i].getText().equals("O")) {
            //             countO++;
            //         }
            //     }
            // }
            // if (countX == 3 || countO == 3){ // if there are 3 in a row verticaly someone won
            //     return true;
            // }
            if (buttons[0][i].getText().equals("X")&&buttons[1][i].getText().equals("X")&&buttons[2][i].getText().equals("X")) {
                return true;
            }
            if (buttons[0][i].getText().equals("O")&&buttons[1][i].getText().equals("O")&&buttons[2][i].getText().equals("O")) {
                return true;
            }
            
        }
        countX = 0; //reset
        countO = 0;
        // check diagonal wins (left to right)
        // int s = 0;
        // for (int i = 0; i < 3; i++) {
        //     if(!buttons[i][s].getText().equals("-")){
        //         if (buttons[i][s].getText().equals("X")) {
        //             countX++;
        //         }
        //         if (buttons[i][s].getText().equals("O")) {
        //             countO++;
        //         }
        //     }
        //     s++;
        // }

        if (buttons[0][0].getText().equals("X")&&buttons[1][1].getText().equals("X")&&buttons[2][2].getText().equals("X")) {
            countX = 1;
        }
        if (buttons[0][2].getText().equals("O")&&buttons[1][1].getText().equals("O")&&buttons[2][0].getText().equals("O")) {
            countO = 1;
        }

        if (countX == 1 || countO == 1){
            return true;
        }
        countX = 0; //reset
        countO = 0;
        // check diagonal wins (right to left)
        // s = 2;
        // for (int i = 0; i < 3; i++) {
        //     if(!buttons[i][s].getText().equals("-")){
        //         if (buttons[i][s].getText().equals("X")) {
        //             countX++;
        //         }
        //         System.out.println(buttons[i][s].getText());
        //         if (buttons[i][s].getText().equals("O")) {
        //             countO++;
        //         }
        //     }
        //     s--;
        // }
        
        if (buttons[0][2].getText().equals("X")&&buttons[1][1].getText().equals("X")&&buttons[2][0].getText().equals("X")) {
            countX = 1;
        }
        if (buttons[0][2].getText().equals("O")&&buttons[1][1].getText().equals("O")&&buttons[2][0].getText().equals("O")) {
            countO = 1;
        }
        if (countX == 1 || countO == 1){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        new tictactoe();
    }
}
