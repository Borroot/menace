package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;

import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static tk.borroot.logic.Symbol.*;

public class PlayerMinmax extends Player {

    private Vector<Integer> validMoves (Board board) {
        Vector<Integer> moves = new Vector<>();
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == EMPTY) {
                moves.add(i);
            }
        }
        return moves;
    }

    private int heuristic (Board board) {
        if (Logic.won(board) != null) {
            return (Logic.won(board) == this.getSymbol()) ? 1 : -1;
        } else {
            return 0;
        }
    }

    private int minmax (Board board, boolean maxplayer) {
        // base case
        if (board.isFull() || Logic.won(board) != null) {
            return heuristic(board);
        }
        // recursive cases
        Vector<Integer> moves = validMoves(board);
        if (maxplayer) {
            int value = -1;
            for (Integer move : moves) {
                board.set(move, this.getSymbol());
                value = max(value, minmax(board, false));
                board.set(move, EMPTY);
            }
            return value;
        } else { // min player
            int value = 1;
            for (Integer move : moves) {
                board.set(move, ((this.getSymbol() == CROSS) ? CIRCLE : CROSS));
                value = min(value, minmax(board, true));
                board.set(move, EMPTY);
            }
            return value;
        }
    }

    @Override
    public int move(final Board board) {
        int max = Integer.MIN_VALUE;
        int move = -1;
        // try all the possible moves and take the best one
        Vector<Integer> moves = validMoves(board);
        for (int i = 0; i < moves.size(); i++) {
            board.set(moves.get(i), this.getSymbol());
            int value = minmax(board.clone(), true);
            board.set(moves.get(i), EMPTY);

            if (value > max) {
                max = value;
                move = moves.get(i);
            }
        }
        return move;
    }
}
