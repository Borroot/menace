package tk.borroot.player.perfect;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.player.Player;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static tk.borroot.logic.Symbol.*;

/**
 * This player plays by the minimax algorithm without a limit on the depth,
 * thus making a perfect player. It also makes use of alpha beta pruning.
 *
 * @author Bram Pulles
 */
public class PlayerMinimax extends Player {

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
			return (Logic.won(board) == this.getSymbol()) ? 1 : -1;
		} else {
			return 0;
		}
	}

	/**
	 * The minimax algorithm with alpha beta pruning.
	 *
	 * @param board     the board to be valued
	 * @param maxplayer if true otherwise minimizing player
	 * @return the value of the board for the player
	 */
	private int minimaxAlphaBeta(Board board, int alpha, int beta, boolean maxplayer) {
		if (board.isFull() || Logic.won(board) != null) {
			return heuristic(board);
		}

		Vector<Integer> moves = validMoves(board);
		if (maxplayer) {
			int value = Integer.MIN_VALUE;
			for (Integer move : moves) {
				// Make the move and do the depth first search.
				board.set(move, this.getSymbol());
				value = max(value, minimaxAlphaBeta(board, alpha, beta, false));
				board.set(move, EMPTY);

				// Do some alpha beta pruning.
				alpha = max(alpha, value);
				if (alpha >= beta) {
					break;
				}
			}
			return value;
		} else { // min player
			int value = Integer.MAX_VALUE;
			for (Integer move : moves) {
				// Make the move and do the depth first search.
				board.set(move, ((this.getSymbol() == CROSS) ? CIRCLE : CROSS));
				value = min(value, minimaxAlphaBeta(board, alpha, beta, true));
				board.set(move, EMPTY);

				// Do some alpha beta pruning.
				beta = min(beta, value);
				if (alpha >= beta) {
					break;
				}
			}
			return value;
		}
	}

	@Override
	public int move(Board board) {
		int max = Integer.MIN_VALUE;
		Vector<Integer> bestmoves = new Vector<>();

		// Try all the possible moves and take the best one.
		Vector<Integer> moves = validMoves(board);
		for (Integer move : moves) {
			// Make the move and get the value of how good the move is.
			board.set(move, this.getSymbol());
			int value = minimaxAlphaBeta(board, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			board.set(move, EMPTY);

			// Update the best moves.
			if (value > max) {
				max = value;
				bestmoves.clear();
				bestmoves.add(move);
			} else if (value == max) {
				bestmoves.add(move);
			}
		}

		// Choose a random move from all the equally best moves.
		return bestmoves.get(new Random().nextInt(bestmoves.size()));
	}

	@Override
	public void learn(Player winner) {
		// no reinforcement learning
	}
}