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
    }

    @Override
    public int move(Board board) {
        // search for the corresponding box
        // choose a random move from this box
        // add this move to the moved vector
        // return the move
        return 0;
    }

    @Override
    public void learn(Player winner) {

        moved.clear();
    }
}
