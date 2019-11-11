package tk.borroot.controller;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.player.Player;
import tk.borroot.player.PlayerHuman;
import tk.borroot.player.PlayerMenace;
import tk.borroot.player.PlayerMinmax;

import java.util.Scanner;

import static tk.borroot.logic.Symbol.*;

/**
 * This class handles the overall game logic.
 *
 * @author Bram Pulles
 */
public class GameController {

    private Scanner input = new Scanner(System.in);

    public GameController() {
        init();
    }

    /**
     * Initialize the game by asking for the amount of rounds and the type
     * of players. This function will loop until the specified amount of games
     * has been played.
     */
    private void init() {
        final int ROUNDS = rounds();
        Player[] players = {player(1), player(2)};

        for (int i = 0; i < ROUNDS || ROUNDS <= -1; i++) {
            // Every new round another player will start.
            Player winner = play(players, players[i % players.length]);
            System.out.println((winner == null) ? "It is a tie!" : "Player " + winner + " wins!");
        }
    }

    /**
     * Play one round.
     *
     * @param players an array with 2 players
     * @return the player who has one or null if it is a tie.
     */
    private Player play(Player[] players, Player onturn) {
        Board board = new Board();
        System.out.println(board);

        // Start the game loop and continue until a win or tie.
        do {
            // Ask for a move until the player entered a valid move.
            int move;
            do {
                move = onturn.move(board);
            } while (board.get(move) != EMPTY);
            board.set(move, onturn.getSymbol());

            System.out.println(board);
            onturn = nextTurn(onturn, players);
        } while (!Logic.won(board) && !board.isFull());

        // Return the winner of this round.
        if (Logic.won(board)) {
            // here we need to restore the turn
            return nextTurn(onturn, players);
        } else {
            return null;
        }
    }

    /**
     * Get the next turn.
     *
     * @param onturn  the player who is currently on turn
     * @param players an array with 2 players
     * @return the player whose is on turn
     */
    private Player nextTurn(Player onturn, Player[] players) {
        return (onturn.equals(players[0])) ? players[1] : players[0];
    }

    /**
     * Ask for how many rounds there are supposed to be played.
     *
     * @return a positive amount of rounds or -1 for inf amount
     */
    private int rounds() {
        System.out.print("Amount of rounds (-1 for inf): ");
        return input.nextInt();
    }

    /**
     * Ask for the type of player 1 or 2.
     *
     * @param num either 1 or 2 representing player 1 and 2
     * @return a player object
     */
    private Player player(final int num) {
        System.out.print("Type for player " + num + ":\n (0) Human\n (1) Menace\n (2) Minmax\n> ");
        int choice = input.nextInt();

        Player player;
        switch (choice) {
            case 0:
                player = new PlayerHuman();
                break;
            case 1:
                player = new PlayerMenace();
                break;
            case 2:
                player = new PlayerMinmax();
                break;
            default:
                System.out.println("That is an invalid player, please choose again.");
                return player(num);
        }

        player.setSymbol((num == 1) ? CROSS : CIRCLE);
        return player;
    }

}
