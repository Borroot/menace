package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Symbol;

import static tk.borroot.logic.Symbol.CROSS;

/**
 * This class represents a player with a symbol.
 * All other players implement this class and the move function.
 *
 * @author Bram Pulles
 */
public abstract class Player {

    private Symbol symbol;

    public Player() {
    }

    /**
     * This function will be asked to players when they can make a move.
     *
     * @param board
     * @return a value in [0..8] which corresponds to the move
     */
    public abstract int move(final Board board);

    /**
     * Set the symbol of this player.
     *
     * @param symbol
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the symbol of this player.
     *
     * @return players symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
