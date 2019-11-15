package tk.borroot.player.reinforcement.menace;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.logic.Transform;
import tk.borroot.player.Player;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import static tk.borroot.logic.Symbol.*;

public class PlayerMenace extends Player {

	private Scanner input = new Scanner(System.in);

	private final int INIT_BEATS;
	private final int PUNISHMENT = -1;
	private final int REWARD_TIE = 1;
	private final int REWARD_WON = 3;

	/**
	 * This variable represents all the moves menace made during the last game.
	 * The value will be reset at the end of every game.
	 */
	private Vector<Pair<Matchbox, Integer>> moved = new Vector<>();

	/**
	 * This variable represents all the states in menace. So all the matchboxes
	 * with their appropriate board. All the states are made in such a way that
	 * every game was started by player 'X'. This means that when making a move
	 * the board needs to be swapped if the first player was not 'X'.
	 */
	private HashMap<Board, Matchbox> states = new HashMap<>();

	public PlayerMenace() {
		INIT_BEATS = ask("Enter the initial amount of beats per move per box: ");
		// ask for the punishment value and the reward value

		Board board = new Board(CROSS);
		searchStates(board, true);
	}

	/**
	 * Add the board to the states hashmap with a new matchbox if
	 * it is not already in there.
	 *
	 * @param board to be added to the states
	 * @return if the board was already in the states hashmap
	 */
	private boolean processState(Board board) {
		for (Transform transform : Transform.values()) {
			Board trans = Transform.apply(board, transform);
			if (states.containsKey(trans)) {
				return true;
			}
		}
		states.put(board, new Matchbox(board.moves(), INIT_BEATS));
		return false;
	}

	/**
	 * Search for all the states which can be reached and add these
	 * to the states hashmap. This is done in a depth first search
	 * manner. We also check for all the possible transformations
	 * of the board and only add the unique ones to the states hashmap.
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
		for (Integer move : moves) {
			if (!processState(board)) {
				board.set(move, (onturn) ? CROSS : CIRCLE);
				searchStates(board, !onturn);
				board.set(move, EMPTY);
			}
		}
	}

	/**
	 * Ask for an integer from the player with the given question.
	 *
	 * @param question to be asked to the player
	 * @return the integer chosen by the player
	 */
	private int ask(String question) {
		System.out.print(question);
		try {
			return input.nextInt();
		} catch (Exception e) {
			input.nextLine();
			return ask(question);
		}
	}

	@Override
	public int move(Board board) {
		Board swapped = (board.getFirstMove() == CROSS) ? board.clone() : Transform.swapAll(board);
		for (Transform transform : Transform.values()) {
			Board trans = Transform.apply(swapped, transform);
			if (states.containsKey(trans)) {
				Matchbox matchbox = states.get(trans);
				int transMove = matchbox.move();
				moved.add(new Pair<>(matchbox, transMove));
				return Transform.move(transMove, transform, true);
			}
		}
		return -1;
	}

	@Override
	public void learn(Player winner) {
		int learn;
		if (winner == null) {
			learn = REWARD_TIE;
		} else {
			learn = (this.equals(winner) ? REWARD_WON : PUNISHMENT);
		}

		for (Pair<Matchbox, Integer> pair : moved) {
			pair.x.remove(pair.y, learn);
		}
		moved.clear();
	}
}
