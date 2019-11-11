package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;

import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static tk.borroot.logic.Symbol.*;

/**
 * This player plays by the minmax algorithm without a limit on the depth,
 * thus making a perfect player.
 *
 * @author Bram Pulles
 */
public class PlayerMinmax extends Player {

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

    /**
     * Give a value to the current state of the board.
     *
     * @param board which is given a value by the heuristic of winning
     * @return 1 if this player won, -1 if the other player won and 0 if its a tie
     */
    private int heuristic(Board board) {
        if (Logic.won(board) != null) {
            return (Logic.won(board) == this.getSymbol()) ? 1 : -1;
        } else {
            return 0;
        }
    }

    /**
     * The minimax algorithm.
     *
     * @param board     the board to be valued
     * @param maxplayer if true otherwise minimizing player
     * @return the value of the board for the player
     */
    private int minmax(Board board, boolean maxplayer) {
        // base case
        if (board.isFull() || Logic.won(board) != null) {
            return heuristic(board);
        }
        // recursive cases
        Vector<Integer> moves = validMoves(board);
        if (maxplayer) {
            int value = Integer.MIN_VALUE;
            for (Integer move : moves) {
                board.set(move, this.getSymbol());
                value = max(value, minmax(board, false));
                board.set(move, EMPTY);
            }
            return value;
        } else { // min player
            int value = Integer.MAX_VALUE;
            for (Integer move : moves) {
                board.set(move, ((this.getSymbol() == CROSS) ? CIRCLE : CROSS));
                value = min(value, minmax(board, true));
                board.set(move, EMPTY);
            }
            return value;
        }
    }

    @Override
    public int move(Board board) {
        int max = Integer.MIN_VALUE;
        int bestmove = -1;
        // try all the possible moves and take the best one
        Vector<Integer> moves = validMoves(board);
        for (Integer move : moves) {
            board.set(move, this.getSymbol());
            int value = minmax(board, false);
            board.set(move, EMPTY);

            if (value > max) {
                max = value;
                bestmove = move;
            }
        }
        return bestmove;
    }
}
