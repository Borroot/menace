package tk.borroot.logic;

import java.util.Arrays;
import java.util.Vector;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * Handles all the logic concerning the tic tac toe board.
 *
 * @author Bram Pulles
 */
public class Board {

	public static final int N = 3;
	private Symbol[] board = new Symbol[N * N];

	/**
	 * Create a board and initialize all the cells to empty.
	 */
	public Board() {
		Arrays.fill(board, EMPTY);
	}

	/**
	 * Set a value on the board.
	 *
	 * @param cell   a cell on the board with value [0..8]
	 * @param symbol value to set the cell to
	 */
	public void set(final int cell, final Symbol symbol) {
		board[cell] = symbol;
	}

	/**
	 * Get a value on the board.
	 *
	 * @param cell a cell on the board with value [0..8]
	 * @return the corresponding cell value
	 */
	public Symbol get(final int cell) {
		return board[cell];
	}

	/**
	 * Check if the board is full.
	 *
	 * @return if the board is full
	 */
	public boolean isFull() {
		for (Symbol symbol : board) {
			if (symbol == EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get all the valid moves aka empty cells on the board.
	 *
	 * @return a vector with all the empty cells on the board
	 */
	public Vector<Integer> moves() {
		Vector<Integer> moves = new Vector<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == EMPTY) {
				moves.add(i);
			}
		}
		return moves;
	}

	/**
	 * Get the size of the board.
	 *
	 * @return the size of the board
	 */
	public int size() {
		return board.length;
	}

	@Override
	public Board clone() {
		Board board = new Board();
		for (int i = 0; i < this.board.length; i++) {
			board.set(i, this.board[i]);
		}
		return board;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Board) {
			for (int i = 0; i < board.length; i++) {
				if (board[i] != ((Board) o).get(i)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Print the board.
	 *
	 * @return a string representation of the board.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			result.append(board[i].toString()).append((i % N != 2) ? "|" : "\n");
		}
		return result.substring(0, result.length() - 1);
	}
}
