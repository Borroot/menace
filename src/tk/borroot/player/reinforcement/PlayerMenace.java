package tk.borroot.player.reinforcement;

import tk.borroot.logic.Board;
import tk.borroot.player.Player;

import java.util.Vector;

public class PlayerMenace extends Player {

	/**
	 * This variable represents all the moves menace made during the last game.
	 * The value will be reset at the end of every game.
	 */
	private Vector<Integer> moved = new Vector<>();

	public PlayerMenace() {
		// ask for initial amount of beats per move per box
		// ask for the punishment value and the reward value

		// generate all the unique boxes
		//   run dfs with the hashmap
		//   for every board check all the transformations in the hashmap
		//      if the board is in the hashmap then stop
		//      else add it to the hashmap together with the appropriate box and continue dfs
	}

	@Override
	public int move(Board board) {
		// search for the corresponding box in a hashmap where the board are keys and the boxes are values
		//   search for all the transformations of the board

		// choose a random move from this box and transform it back using the inverse

		// add this move to the moved vector together with the board
		// return the move
		return 0;
	}

	@Override
	public void learn(Player winner) {

		moved.clear();
	}
}
