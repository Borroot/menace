package tk.borroot.player.reinforcement;

import java.util.Random;
import java.util.Vector;

/**
 * This class represents a matchbox. In this matchbox
 * there are beats for every valid move. When a move is
 * to be taken a random beat will be taken out.
 *
 * After the move has been made and the game is finished
 * the beat will either be removed or move beats will be
 * added to make menace learn with reinforcement.
 *
 * @author Bram Pulles
 */
public class Matchbox {

	private int[] moves = {0, 0, 0, 0, 0, 0, 0, 0, 0};

	/**
	 * Create a new matchbox with the specified moves and
	 * initialize the amount of beats per move accordingly.
	 *
	 * @param moves valid moves
	 * @param beats initial amount of beats per move
	 */
	public Matchbox(Vector<Integer> moves, int beats) {
		for (Integer move : moves) {
			this.moves[move] += beats;
		}
	}

	/**
	 * Get a random move from the matchbox. The moves which
	 * have more corresponding beats have a higher chance of
	 * getting chosen in proportion to the amount of beats.
	 *
	 * @return a random move
	 */
	public int move() {
		int total = 0;
		for (int move : moves) {
			total += move;
		}

		int rand = 1 + new Random().nextInt(total - 1);
		for (int i = 0; i < moves.length; i++) {
			if (rand <= moves[i]) {
				return i;
			}
			rand -= moves[i];
		}
		return -1;
	}

	/**
	 * Add the specified amount of beats to the specified move.
	 *
	 * @param move  to add the beats to
	 * @param beats to be added to the move
	 */
	public void add(int move, int beats) {
		moves[move] += beats;
	}

	/**
	 * Remove the specified amount of beats from the specified move.
	 * The amount of beats for the specified move will never be smaller than zero.
	 *
	 * @param move  to remove the beats from
	 * @param beats to be removed from the move
	 */
	public void remove(int move, int beats) {
		if (moves[move] - beats < 0) {
			moves[move] = 0;
		} else {
			moves[move] -= beats;
		}
	}

}
