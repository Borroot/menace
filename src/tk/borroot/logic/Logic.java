package tk.borroot.logic;

import tk.borroot.player.Player;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * This class handles all the game logic, so for example determining if the game is won.
 *
 * @author Bram Pulles
 */
public class Logic {

    private static final int N = Board.N;

    /**
     * Check if the board is won.
     *
     * @return the symbol which won or null if nobody won.
     */
    public static Symbol won(Board board) {
        // Check all horizontal lines.
        for (int i = 0; i < N; i += N) {
            if (board.get(i) != EMPTY && board.get(i) == board.get(i + 1) && board.get(i + 1) == board.get(i + 2)) {
                return board.get(i);
            }
        }
        // Check all vertical lines.
        for (int i = 0; i < N; i++) {
            if (board.get(i) != EMPTY && board.get(i) == board.get(i + N) && board.get(i + N) == board.get(i + N + N)) {
                return board.get(i);
            }
        }
        // Check diagonal lines.
        if (board.get(0) != EMPTY && board.get(0) == board.get(4) && board.get(4) == board.get(8)) {
            return board.get(0);
        }
        if (board.get(2) != EMPTY && board.get(2) == board.get(4) && board.get(4) == board.get(6)) {
            return board.get(2);
        }
        // There is no win.
        return null;
    }
}
