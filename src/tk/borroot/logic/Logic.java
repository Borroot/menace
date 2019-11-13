package tk.borroot.logic;

import static tk.borroot.logic.Symbol.EMPTY;

/**
 * This class handles all the game logic, so for example determining if the game is won.
 *
 * @author Bram Pulles
 */
public class Logic {

    private static final int N = Board.N;

    /**
     * This class is not supposed to be instantiated.
     */
    private Logic () {
    }

    /**
     * Check if the board is won.
     *
     * @param board to be checked if in a winning state
     * @return the symbol which won or null if nobody won
     */
    public static Symbol won(Board board) {
        // Check all horizontal lines.
        for (int i = 0, base = 0; i < N; i++, base += N) {
            if (board.get(base) != EMPTY && board.get(base) == board.get(base + 1) && board.get(base + 1) == board.get(base + 2)) {
                return board.get(base);
            }
        }
        // Check all vertical lines.
        for (int i = 0; i < N; i++) {
            if (board.get(i) != EMPTY && board.get(i) == board.get(i + N) && board.get(i + N) == board.get(i + 2 * N)) {
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
