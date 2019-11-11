package tk.borroot.controller;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.player.*;

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

        int[] results = {0, 0, 0}; // wins player 1, wins player 2, ties
        for (int i = 0; i < ROUNDS || ROUNDS <= -1; i++) {
            // Every new round another player will start.
            Player winner = play(players, players[i % players.length]);

            // Process the result.
            if (winner == null) {
                results[2]++;
            } else {
                results[(winner == players[0])? 0 : 1]++;
            }
        }
        System.out.println("Player 1 won " + results[0] + " time(s), Player 2 won " + results[1] + " time(s) and there were " + results[2] + " tie(s).");
    }

    /**
     * Play one round.
     *
     * @param players an array with 2 players
     * @return the player who has one or null if it is a tie.
     */
    private Player play(Player[] players, Player onturn) {
        Board board = new Board();

        // Start the game loop and continue until a win or tie.
        do {
            // Ask for a move until the player entered a valid move.
            int move;
            do {
                move = onturn.move(board);
            } while (board.get(move) != EMPTY);

            // Make the move and change the turn.
            board.set(move, onturn.getSymbol());
            onturn = nextTurn(onturn, players);
        } while (!board.isFull() && Logic.won(board) == null);

        // Return the winner of this round.
        if (Logic.won(board) != null) {
            return (Logic.won(board) == players[0].getSymbol()) ? players[0] : players[1];
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
        try {
            return input.nextInt();
        } catch (Exception e) {
            input.nextLine();
            return rounds();
        }
    }

    /**
     * Ask for the type of player 1 or 2.
     *
     * @param num either 1 or 2 representing player 1 and 2
     * @return a player object
     */
    private Player player(final int num) {
        System.out.print("Type for player " + num + ":\n (0) Human\n (1) Menace\n (2) Minmax\n (3) Qlearning\n> ");
        int choice;
        try {
            choice = input.nextInt();
        } catch (Exception e) {
            input.nextLine();
            return player(num);
        }

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
            case 3:
                player = new PlayerQlearning();
                break;
            default:
                return player(num);
        }

        player.setSymbol((num == 1) ? CROSS : CIRCLE);
        return player;
    }
}
