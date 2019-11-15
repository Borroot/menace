package tk.borroot.logic;

/**
 * This class can be used to get all the possible transformation on
 * the board and therefore to get all the symmetries of the board.
 *
 * @author Bram Pulles
 */
public enum Transform {
	IDENTITY(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}),
	HORIZONTAL(new int[]{6, 7, 8, 3, 4, 5, 0, 1, 2}),
	VERTICAL(new int[]{2, 1, 0, 5, 4, 3, 8, 7, 6}),
	ROTATE_ONCE(new int[]{2, 5, 8, 1, 4, 7, 0, 3, 6}, new int[]{6, 3, 0, 7, 4, 1, 8, 5, 2}),
	ROTATE_TWICE(new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0}),
	ROTATE_THRICE(new int[]{6, 3, 0, 7, 4, 1, 8, 5, 2}, new int[]{2, 5, 8, 1, 4, 7, 0, 3, 6}),
	MAIN_DIAGONAL(new int[]{0, 3, 6, 1, 4, 7, 2, 5, 8}), // main diagonal is top-left -> bottom-right
	OTHER_DIAGONAL(new int[]{8, 5, 2, 7, 4, 1, 6, 3, 0}); // other diagonal is top-right -> bottom-left

	private int[] transform;
	private int[] inverse;

	/**
	 * Create a transform object where the inverse is the same as the transform.
	 *
	 * @param transform a matrix with on each index the new index
	 */
	Transform(int[] transform) {
		this(transform, transform);
	}

	/**
	 * Create a transform object where the inverse and the transform matrix are different.
	 *
	 * @param transform a matrix with on each index the new index
	 * @param inverse   a matrix with on each index the new index, the inverse of transform
	 */
	Transform(int[] transform, int[] inverse) {
		this.transform = transform;
		this.inverse = inverse;
	}

	/**
	 * Transform the move and return the transformed move.
	 *
	 * @param move      to be transformed
	 * @param transform a matrix with on each index the new index
	 * @param inverse   inverse the transformation
	 * @return the transformed move
	 */
	public static int move(int move, Transform transform, boolean inverse) {
		return (inverse) ? transform.inverse[move] : transform.transform[move];
	}

	/**
	 * Apply a given transformation matrix on the board.
	 *
	 * @param board     to be transformed
	 * @param transform a matrix with on each index the new index
	 * @param inverse   inverse the transformation
	 * @return the transformed board
	 */
	public static Board apply(Board board, Transform transform, boolean inverse) {
		Board trans = new Board();
		for (int i = 0; i < board.size(); i++) {
			int index = (inverse) ? transform.inverse[i] : transform.transform[i];
			trans.set(index, board.get(i));
		}
		return trans;
	}
}
