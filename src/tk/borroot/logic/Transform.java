package tk.borroot.logic;

/**
 * This class can be used to get all the possible transformation on
 * the board and therefore get all the symmetries of the board.
 *
 * @author Bram Pulles
 */
public class Transform {

	public static final int[] HOR = {6, 7, 8, 3, 4, 5, 0, 1, 2}; // horizontal
	public static final int[] VER = {2, 1, 0, 5, 4, 3, 8, 7, 6}; // vertical
	public static final int[] ROT = {3, 0, 1, 6, 4, 2, 7, 8, 5}; // counter clockwise
	public static final int[] MDIA = {0, 3, 6, 1, 4, 7, 2, 5, 8}; // main diagonal is top-left -> bottom-right
	public static final int[] ODIA = {8, 5, 2, 7, 4, 1, 6, 3, 0}; // other diagonal is top-right -> bottom-left

	/**
	 * This class is not supposed to be instantiated.
	 */
	private Transform() {
	}

	/**
	 * Transform the move and return the transformed move.
	 *
	 * @param move      to be transformed
	 * @param transform a matrix with on each index the new index
	 * @return the transformed move
	 */
	public static int move(int move, int[] transform) {
		return transform[move];
	}

	/**
	 * Transform the move the specified amount of times and return
	 * the transformed move.
	 *
	 * @param move      to be transformed
	 * @param transform a matrix with on each index the new index
	 * @param times     amount of times to apply the transformation
	 * @return the transformed move
	 */
	public static int move(int move, int[] transform, int times) {
		for (int i = 0; i < times; i++) {
			move = transform[move];
		}
		return move;
	}

	/**
	 * Apply a given transformation matrix on the board.
	 *
	 * @param board     to be transformed
	 * @param transform a matrix with on each index the new index
	 * @return the transformed board
	 */
	public static Board apply(Board board, int[] transform) {
		assert (board.size() == transform.length);

		Board trans = new Board();
		for (int i = 0; i < board.size(); i++) {
			trans.set(transform[i], board.get(i));
		}
		return trans;
	}


	/**
	 * Apply a given transformation matrix on the board the
	 * given amount of times.
	 *
	 * @param board     to be transformed
	 * @param transform a matrix with on each index the new index
	 * @param times     amount of times to apply the transform
	 * @return the transformed board
	 */
	public static Board apply(Board board, int[] transform, int times) {
		assert (board.size() == transform.length);

		Board trans = board.clone();
		for (int i = 0; i < times; i++) {
			trans = apply(trans, transform);
		}
		return trans;
	}
}
