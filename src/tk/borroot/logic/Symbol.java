package tk.borroot.logic;

/**
 * This enumeration represents the possible values for the cells on the board.
 * @author Bram Pulles
 */
public enum Symbol {
    EMPTY, CROSS, CIRCLE;

    @Override
    public String toString() {
        String symbol;
        switch (this) {
            case CROSS: symbol = "X"; break;
            case CIRCLE: symbol = "O"; break;
            default: symbol = " ";
        }
        return symbol;
    }
}
