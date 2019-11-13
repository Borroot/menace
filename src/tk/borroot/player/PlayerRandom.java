package tk.borroot.player;

import tk.borroot.logic.Board;

import java.util.Random;
import java.util.Vector;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * This player makes random moves.
 *
 * @author Bram Pulles
 */
public class PlayerRandom extends Player {

    /**
     * Get all the valid moves aka empty cells on the board.
     *
     * @param board to be checked on empty cells
     * @return a vector with all the empty cells on the board
     */
    private Vector<Integer> validMoves(Board board) {
        Vector<Integer> moves = new Vector<>();
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == EMPTY) {
                moves.add(i);
            }
        }
        return moves;
    }

    @Override
    public int move(Board board) {
        Vector<Integer> moves = validMoves(board);
        return moves.get(new Random().nextInt(moves.size()));
    }

    @Override
    public void learn(Player winner) {
        // no reinforcement learning
    }
}
