package tk.borroot.logic;

import static tk.borroot.logic.Symbol.*;

/**
 * Handles all the logic concerning the tic tac toe board.
 * @author Bram Pulles
 */
public class Board {

    public final int N = 3;
    private Symbol[] board = new Symbol[N*N];

    /**
     * Create a board and initialize all the cells to empty.
     */
    public Board () {
        for (int i = 0; i < board.length; i++) {
            board[i] = EMPTY;
        }
    }

    /**
     * Set a value on the board.
     * @param cell a cell on the board with value [0..8]
     * @param symbol value to set the cell to
     */
    public void set (final int cell, final Symbol symbol) {
        board[cell] = symbol;
    }

    /**
     * Get a value on the board.
     * @param cell a cell on the board with value [0..8]
     * @return the corresponding cell value
     */
    public Symbol get (final int cell) {
        return board[cell];
    }

    /**
     * Check if the board is full.
     * @return if the board is full
     */
    public boolean isFull () {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Print the board.
     * @return a string representation of the board.
     */
    @Override
    public String toString () {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            String value = "";
            switch (board[i]) {
                case EMPTY:
                    value = " ";
                    break;
                case CROSS:
                    value = "X";
                    break;
                case CIRCLE:
                    value = "O";
                    break;
            }
            result += value + ((i % N != 2) ? "|" : "\n");
        }
        return result;
    }
}
