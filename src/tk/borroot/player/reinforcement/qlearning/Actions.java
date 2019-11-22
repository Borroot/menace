package tk.borroot.player.reinforcement.qlearning;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

class Actions {

	private Float[] moves = {null, null, null, null, null, null, null, null, null};

	/**
	 * Create a new actions object with the specified moves (actions).
	 * Each actions will have an initial value of 0f.
	 *
	 * @param moves valid moves
	 */
	Actions(Vector<Integer> moves) {
		for (Integer move : moves) {
			this.moves[move] = 0f;
		}
	}

	/**
	 * Best move will return the best move in the available actions.
	 * It will return one of the best moves if there are multiple
	 * moves with the same best value.
	 * <p>
	 * Note that this function should not be called if there are no
	 * moves available.
	 *
	 * @return one of the best moves
	 */
	int bestMove() {
		Vector<Integer> bestMoves = new Vector<>();
		float bestValue = Float.NEGATIVE_INFINITY;
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] != null) {
				if (moves[i] > bestValue) {
					bestValue = moves[i];
					bestMoves.clear();
					bestMoves.add(i);
				} else if (moves[i] == bestValue) {
					bestMoves.add(i);
				}
			}
		}
		return bestMoves.get(new Random().nextInt(bestMoves.size()));
	}

	/**
	 * This function will be called to estimate the optimal future value.
	 * So this is the maximum value of all the possible actions available.
	 *
	 * @return the maximum value of all the possible actions available if non then 0
	 */
	public float maxValue() {
		float max = 0;
		for (Float move : moves) {
			if (move != null && move > max) {
				max = move;
			}
		}
		return max;
	}

	/**
	 * Set the value for a move (action).
	 *
	 * @param move  (action) to be set
	 * @param value to be assigned to the action
	 */
	public void set(int move, float value) {
		moves[move] = value;
	}

	/**
	 * Get the value of a move (action).
	 *
	 * @param move to be get
	 * @return the value assigned to the move
	 */
	public float get(int move) {
		return moves[move];
	}

	@Override
	public String toString() {
		return Arrays.toString(moves);
	}
}
