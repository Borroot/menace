package tk.borroot.player.stupid;

import tk.borroot.logic.Board;
import tk.borroot.player.Player;

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

    @Override
    public void learn(Player winner) {
        // no reinforcement learning but print the winner
        System.out.println("Player " + winner + " won!");
    }
}
