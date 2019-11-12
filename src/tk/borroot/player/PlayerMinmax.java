package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.*;
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
     * Give a value to the current state of the board from the perspective of player A.
     *
     * @param board which is given a value by the heuristic of winning
     * @return 1 if this player won, -1 if the other player won and 0 if its a tie
     */
    private int heuristic(Board board) {
        if (Logic.won(board) != null) {
            return (Logic.won(board) == this.getSymbol())? 1 : -1;
        } else {
            return 0;
        }
    }

    /**
     * The negamax algorithm.
     *
     * @param board the board to be valued
     * @param color 1 for the max player and -1 for the min player
     * @return the value of the board for the player
     */
    private int negamax(Board board, int color) {
        // base case
        if (board.isFull() || Logic.won(board) != null) {
			System.out.println("WON BY " + Logic.won(board));
			System.out.println(board);
			System.out.println("Base case for color " + color + ":\t" + (color * heuristic(board)));
            return color * heuristic(board);
        }
        // recursive cases
        Vector<Integer> moves = validMoves(board);
		int value = Integer.MIN_VALUE;
		for (Integer move : moves) {
			board.set(move, this.getSymbol());
			value = max(value, -negamax(board, -color));
			board.set(move, EMPTY);
		}
		return value;
    }

    @Override
    public int move(Board board) {
        int max = Integer.MIN_VALUE;
        Vector<Integer> bestmoves = new Vector<>();

        // try all the possible moves and take the best one
        Vector<Integer> moves = validMoves(board);
        for (Integer move : moves) {
            board.set(move, this.getSymbol());
            int value = negamax(board, -1);
			System.out.println("Value for move " + move + " is " + value);
            board.set(move, EMPTY);

            // update the best moves
            if (value > max) {
                max = value;
                bestmoves.clear();
                bestmoves.add(move);
            } else if (value == max) {
                bestmoves.add(move);
            }
        }
        return bestmoves.get(new Random().nextInt(bestmoves.size()));
    }
}
