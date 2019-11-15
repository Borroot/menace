package tk.borroot.player.reinforcement.menace;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * This class represents a matchbox. In this matchbox
 * there are beats for every valid move. When a move is
 * to be taken a random beat will be taken out.
 * <p>
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
	 * @param moves      valid moves
	 * @param INIT_BEATS initial amount of beats per move
	 */
	Matchbox(Vector<Integer> moves, final int INIT_BEATS) {
		for (Integer move : moves) {
			this.moves[move] += INIT_BEATS;
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

		if (total > 0) {
			int rand = 1 + new Random().nextInt(total);
			for (int i = 0; i < moves.length; i++) {
				if (rand <= moves[i]) {
					return i;
				}
				rand -= moves[i];
			}
		}
		return -1;
	}

	/**
	 * Add/remove the specified amount of beats to the specified move.
	 *
	 * @param move  to add/remove the beats to
	 * @param beats to be added/removed to the move
	 */
	public void add(int move, int beats) {
		if (moves[move] + beats < 0) {
			moves[move] = 0;
		} else {
			moves[move] += beats;
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(moves);
	}
}
