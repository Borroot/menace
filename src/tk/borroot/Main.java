package tk.borroot;

import tk.borroot.logic.Board;
import tk.borroot.logic.Symbol;

public class Main {

    public static void main(String[] args) {
	    Board board = new Board();
	    board.set(1,1, Symbol.CROSS);
	    board.set(0,0, Symbol.CIRCLE);
		System.out.println(board);
	}
}
