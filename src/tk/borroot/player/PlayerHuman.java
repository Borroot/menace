package tk.borroot.player;

import tk.borroot.logic.Board;

import java.util.Scanner;

/**
 * This class represents a human player.
 *
 * @author Bram Pulles
 */
public class PlayerHuman extends Player {

    private final Scanner input = new Scanner(System.in);

    public PlayerHuman() {
        super();
    }

    @Override
    public int move(Board board) {
        System.out.print("Please enter your move player " + this.getSymbol() + ": ");
        try {
            return input.nextInt();
        } catch (Exception e) {
            input.nextLine();
            return this.move(board);
        }
    }
}
