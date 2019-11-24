package tk.borroot.player.reinforcement.qlearning;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.logic.Transform;
import tk.borroot.player.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import static tk.borroot.logic.Symbol.*;
import static tk.borroot.view.View.*;

/**
 * This class represents a player which plays using Q-learning!
 *
 * @author Bram Pulles
 */
public class PlayerQlearning extends Player {

	/**
	 * The learning rate (alpha) and the discount factor (gamma) they range in [0,1].
	 */
	private final float LEARNING_RATE, DISCOUNT_FACTOR;

	/**
	 * The n'th round where the epsilon value will start to decrease
	 * and where the epsilon value will reach zero. Between the start
	 * and the end the value will decrease uniformly.
	 */
	private final int START_DEGRADATION, END_DEGRADATION;

	/**
	 * The exploration value (epsilon) which describes the amount
	 * of random moves to be made.
	 */
	private float exploration;

	/**
	 * The average decrease with every round of the exploration value.
	 */
	private final float DEGRADATION_PER_ROUND;

	/**
	 * It is currently the n'th round.
	 */
	private int round = 1;

	/**
	 * This variable represents all the moves qlearning made during the last game.
	 * It also has a field to say if the board was swapped yes or no, this is very
	 * useful for the learning function.The value will be reset at the end of every
	 * game.
	 */
	private Vector<Triplet<Board, Integer, Boolean>> moved = new Vector<>();

	/**
	 * This variable represents the qlearning table with all the states and all
	 * the actions which can be made from a given state. All the states are made
	 * in such a way that every game was started by player 'X'. This means that
	 * when making a move the board needs to be swapped if the first player was
	 * not 'X'.
	 */
	private HashMap<Board, Actions> table = new HashMap<>();

	public PlayerQlearning() {
		LEARNING_RATE = askFloat("Please choose the learning rate (alpha) between 0 and 1: ");
		DISCOUNT_FACTOR = askFloat("Please choose the discount factor (gamma) between 0 and 1: ");

		exploration = askFloat("Please choose the exploration value (epsilon, amount of random moves): ");

		START_DEGRADATION = askInt("Please choose a start for exploration degradation: ");
		END_DEGRADATION = askInt("Please choose an end for exploration degradation: ");
		DEGRADATION_PER_ROUND = exploration / (END_DEGRADATION - START_DEGRADATION);

		searchStates(new Board(), true);
	}

	/**
	 * Add the board to the table hashmap with a new actions object if
	 * it is not already in there.
	 *
	 * @param board to be added to the table
	 * @return if the board was already in the table hashmap
	 */
	private boolean processState(Board board) {
		for (Transform transform : Transform.values()) {
			Board trans = Transform.apply(board, transform);
			if (table.containsKey(trans)) {
				return true;
			}
		}
		table.put(board.clone(), new Actions(board.moves()));
		return false;
	}

	/**
	 * Search for all the states which can be reached and add these
	 * to the table hashmap. This is done in a depth first search
	 * manner. We also check for all the possible transformations
	 * of the board and only add the unique ones to the table hashmap.
	 *
	 * @param board  to be further explored
	 * @param onturn true means 'X', false means 'O'
	 */
	private void searchStates(Board board, boolean onturn) {
		if (board.isFull() || Logic.won(board) != null) {
			processState(board);
			return;
		}

		Vector<Integer> moves = board.moves();
		if (!processState(board)) {
			for (Integer move : moves) {
				board.set(move, (onturn) ? CROSS : CIRCLE);
				searchStates(board, !onturn);
				board.set(move, EMPTY);
			}
		}
	}

	/**
	 * Check if this player started with this game.
	 *
	 * @param board the board to be checked
	 * @return if this player started the game corresponding to this board
	 */
	private boolean first(Board board) {
		int countThis = 0;
		int countThat = 0;
		for (int i = 0; i < board.size(); i++) {
			if (this.getSymbol() == board.get(i)) {
				countThis++;
			} else if (board.get(i) != EMPTY) {
				countThat++;
			}
		}
		return countThis == countThat;
	}

	/**
	 * Check if the board needs to be swapped such that the
	 * first move was done by a 'X'. This is needed because
	 * the states hashmap is made with 'X' starting.
	 *
	 * @param board to be swapped yes or no
	 * @return if the board needs to be swapped
	 */
	private boolean needsSwap(Board board) {
		if (first(board)) {
			return this.getSymbol() == CIRCLE;
		} else {
			return this.getSymbol() == CROSS;
		}
	}

	@Override
	public int move(Board board) {
		// Swap the boards 'X' and 'O' values so the first move was made by an 'X'.
		boolean needsSwap = needsSwap(board);
		Board swapped = (needsSwap) ? Transform.swapAll(board) : board.clone();

		// Search for the board in the states hashmap by applying transformations until the board is found.
		for (Transform transform : Transform.values()) {
			Board trans = Transform.apply(swapped, transform);
			if (table.containsKey(trans)) {
				Actions actions = table.get(trans);

				// Make either a random move or take the best move.
				int transMove;
				if (new Random().nextFloat() < exploration) {
					Vector<Integer> moves = trans.moves();
					transMove = moves.get(new Random().nextInt(moves.size()));
				} else {
					transMove = actions.bestMove();
				}

				// Update the epsilon value for degradation.
				if (round >= START_DEGRADATION && round < END_DEGRADATION) {
					exploration -= DEGRADATION_PER_ROUND;
				}

				moved.add(new Triplet<>(trans, transMove, needsSwap));
				return Transform.move(transMove, transform, true);
			}
		}
		return -1;
	}

	/**
	 * Get the maximum value for the future. This value is calculated by
	 * first making the move on the board and then taking the maximum
	 * value of all actions from this new board.
	 *
	 * @param board to be made a move on
	 * @param move  to be made on the board
	 * @param swapped if the board was swapped before
	 * @return the max value of the new board
	 */
	private float maxFuture(Board board, int move, boolean swapped) {
		Board next = board.clone();
		next.set(move, (!swapped) ? this.getSymbol() : (this.getSymbol() == CROSS) ? CIRCLE : CROSS);

		// Search for the next board in the table and take the max value.
		for (Transform transform : Transform.values()) {
			Board transNext = Transform.apply(next, transform);
			if (table.containsKey(transNext)) {
				Actions actions = table.get(transNext);
				return actions.maxValue();
			}
		}
		// This should never occur.
		return 0;
	}

	@Override
	public void learn(Player winner) {
		for (Triplet triplet : moved) {
			Board board = (Board) triplet.x;
			int move = (int) triplet.y;
			boolean swapped = (boolean) triplet.z;

			Actions actions = table.get(board);
			final int REWARD = (winner == null) ? 2 : (this.equals(winner)) ? 2 : -2;
			final float MAX_FUTURE = maxFuture(board, move, swapped);

			// Apply the value iteration formula.
			float value = (1 - LEARNING_RATE) * actions.get(move) + LEARNING_RATE * (REWARD + DISCOUNT_FACTOR * MAX_FUTURE);
			actions.set(move, value);
		}
		moved.clear();
		round++;
	}
}
