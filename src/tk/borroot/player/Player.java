package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Symbol;

import static tk.borroot.logic.Symbol.CROSS;

public abstract class Player {

    private Symbol symbol;

    public Player () {
    }

    /**
     * This function will be asked to players when they can make a move.
     * @param board
     * @return a value in [0..8] which corresponds to the move
     */
    public abstract int move (Board board);

    /**
     * Set the symbol of this player.
     * @param symbol
     */
    public void setSymbol (Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the symbol of this player.
     * @return players symbol
     */
    public Symbol getSymbol () {
        return symbol;
    }

    @Override
    public String toString () {
        return (symbol == CROSS)? "X" : "O";
    }
}
