package tk.borroot.player;

import tk.borroot.logic.Board;

import java.util.Scanner;

public class PlayerHuman extends Player {

    private final Scanner input = new Scanner(System.in);

    public PlayerHuman () {
        super();
    }

    @Override
    public int move(Board board) {
        System.out.print("Please enter your move: ");
        return input.nextInt();
    }
}
