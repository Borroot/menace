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
        System.out.println(board);
        System.out.print("Please enter your move player " + this.getSymbol() + ": ");
        try {
            int choice = input.nextInt();

            if (choice >= 0 && choice < 9) {
                return choice;
            } else {
                input.nextLine();
                return this.move(board);
            }
        } catch (Exception e) {
            input.nextLine();
            return this.move(board);
        }
    }
}
