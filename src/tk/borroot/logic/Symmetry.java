package tk.borroot.logic;

/**
 * This class can be used to get all the possible transformation on
 * the board and therefore get all the symmetries of the board.
 *
 * @author Bram Pulles
 */
public class Symmetry {

	private static final int N = Board.N;

	/**
	 * This class is not supposed to be instantiated.
	 */
	private Symmetry() {
	}

	/**
	 * Flip the board horizontally.
	 *
	 * @param board to be flipped
	 */
	public static void flipHorizontal(Board board) {
		for (int i = 0; i < N; i++) {
			Symbol tmp = board.get(i);
			board.set(i, board.get(i + 2 * N));
			board.set(i + 2 * N, tmp);
		}
	}

	/**
	 * Flip the board vertically.
	 *
	 * @param board to be flipped
	 */
	public static void flipVertical(Board board) {
		for (int i = 0, base = 0; i < N; i++, base += N) {
			Symbol tmp = board.get(base);
			board.set(base, board.get(base + 2));
			board.set(base + 2, tmp);
		}
	}

	/**
	 * Flip the board over the main diagonal (top-left to bottom-right).
	 *
	 * @param board to be flipped
	 */
	public static void flipMainDiagonal(Board board) {
		Symbol tmp = board.get(1);
		board.set(1, board.get(3));
		board.set(3, tmp);
		tmp = board.get(2);
		board.set(2, board.get(6));
		board.set(6, tmp);
		tmp = board.get(5);
		board.set(5, board.get(7));
		board.set(7, tmp);
	}

	/**
	 * Flip the board over the non-main diagonal (top-right to bottom-left).
	 *
	 * @param board to be flipped
	 */
	public static void flipOtherDiagonal(Board board) {
		Symbol tmp = board.get(1);
		board.set(1, board.get(5));
		board.set(5, tmp);
		tmp = board.get(0);
		board.set(0, board.get(8));
		board.set(8, tmp);
		tmp = board.get(3);
		board.set(3, board.get(7));
		board.set(7, tmp);
	}

	/**
	 * Rotate the board counter-clockwise the specified amount of times.
	 *
	 * @param board to be rotated
	 * @param TIMES amount of times to rotate the board
	 */
	public static void rotate(Board board, final int TIMES) {
		for (int i = 0; i < TIMES; i++) {
			// rotate the corners
			Symbol tmp = board.get(0);
			board.set(0, board.get(2));
			board.set(2, board.get(8));
			board.set(8, board.get(6));
			board.set(6, tmp);
			// rotate the edges
			tmp = board.get(1);
			board.set(1, board.get(5));
			board.set(5, board.get(7));
			board.set(7, board.get(3));
			board.set(3, tmp);
		}
	}
}
