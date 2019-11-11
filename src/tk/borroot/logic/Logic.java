package tk.borroot.logic;

import tk.borroot.player.Player;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * This class handles all the game logic, so for example determining if the game is won.
 *
 * @author Bram Pulles
 */
public class Logic {

    /**
     * Check if the board is won.
     *
     * @return if the board is won by a player.
     */
    public static boolean won(Board board) {
        // Check all horizontal lines.
        for (int i = 0; i < board.N; i += board.N) {
            if (board.get(i) != EMPTY && board.get(i) == board.get(i + 1) && board.get(i + 1) == board.get(i + 2)) {
                return true;
            }
        }
        // Check all vertical lines.
        for (int i = 0; i < board.N; i++) {
            if (board.get(i) != EMPTY && board.get(i) == board.get(i + board.N) && board.get(i + board.N) == board.get(i + board.N + board.N)) {
                return true;
            }
        }
        // Check diagonal lines.
        if (board.get(0) != EMPTY && board.get(0) == board.get(4) && board.get(4) == board.get(8)) {
            return true;
        }
        if (board.get(2) != EMPTY && board.get(2) == board.get(4) && board.get(4) == board.get(6)) {
            return true;
        }
        // There is no win.
        return false;
    }
}
