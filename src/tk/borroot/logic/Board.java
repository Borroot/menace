package tk.borroot.logic;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * Handles all the logic concerning the tic tac toe board.
 * @author Bram Pulles
 */
public class Board {

    private final int N = 3;
    private Symbol[][] board = new Symbol[N][N];

    /**
     * Create a board and initialize all the cells to empty.
     */
    public Board () {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Set a value on the board.
     * @param x position
     * @param y position
     * @param symbol value to set the cell to
     */
    public void set (final int x, final int y, final Symbol symbol) {
        board[y][x] = symbol;
    }

    /**
     * Get a value on the board.
     * @param x position
     * @param y position
     * @return the corresponding cell value
     */
    public Symbol get (final int x, final int y) {
        return board[y][x];
    }

    /**
     * Check if the board is full.
     * @return if the board is full
     */
    public boolean isFull () {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
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
            for (int j = 0; j < board[i].length; j++) {
                String value = "";
                switch (board[i][j]) {
                    case EMPTY:  value = " "; break;
                    case CROSS:  value = "X"; break;
                    case CIRCLE: value = "O"; break;
                }
                result += value + ((j < board[i].length - 1)? "|" : "");
            }
            result += "\n";
        }
        return result;
    }
}
