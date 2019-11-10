package tk.borroot.player;

import tk.borroot.logic.Board;
import tk.borroot.logic.Symbol;

public abstract class Player {

    private Symbol symbol;

    public Player () {
    }

    /**
     * This function will be asked to players when they can make a move.
     * @param board
     * @return a value in [0..8] which corresponds to the move
     */
    public abstract int move(Board board);

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
