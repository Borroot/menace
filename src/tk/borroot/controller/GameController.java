package tk.borroot.controller;

import tk.borroot.logic.Board;
import tk.borroot.logic.Logic;
import tk.borroot.player.Player;
import tk.borroot.player.perfect.PlayerMinimax;
import tk.borroot.player.perfect.PlayerNegamax;
import tk.borroot.player.reinforcement.PlayerQlearning;
import tk.borroot.player.reinforcement.menace.DiedException;
import tk.borroot.player.reinforcement.menace.MoveException;
import tk.borroot.player.reinforcement.menace.PlayerMenace;
import tk.borroot.player.stupid.PlayerHuman;
import tk.borroot.player.stupid.PlayerRandom;

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
		final int ROUNDS = ask("Amount of rounds (-1 for inf): ");
		final boolean ALTERNATE = 1 == ask("Do you want to alternate turns? (0) for no, (1) for yes: ");
		Player[] players = {player(0), player(1)};

		int[] results = {0, 0, 0}; // wins player 1, wins player 2, ties
		for (int i = 0; i < ROUNDS || ROUNDS <= -1; i++) {
			// Every new round another player will start, if alternate is true.
			Player winner = null;
			try {
				winner = play(players, (ALTERNATE) ? players[i % players.length] : players[0]);
			} catch (DiedException e) {
				System.out.println(e.getMessage()); // Menace died
				break;
			}

			// Let the players learn!
			for (Player player : players) {
				player.learn(winner);
			}

			// Process the result.
			if (winner == null) {
				results[2]++;
			} else {
				results[(winner == players[0]) ? 0 : 1]++;
			}
		}
		System.out.println("Player 1 won " + results[0] + " time(s), Player 2 won " + results[1] + " time(s) and there were " + results[2] + " tie(s).");
	}

	/**
	 * Play one round, when a player plays -1 this is a forfeit.
	 *
	 * @param players an array with 2 players
	 * @param onturn  the player whose turn it is
	 * @return the player who has one or null if it is a tie.
	 * @throws DiedException when one of the players died
	 */
	private Player play(Player[] players, Player onturn) throws DiedException {
		Board board = new Board();

		// Start the game loop and continue until a win or tie.
		do {
			// Ask for a move until the player entered a valid move.
			int move;
			do {
				try {
					move = onturn.move(board);
				} catch (MoveException e) {
					// No move could be made so the player will forfeit.
					return nextTurn(onturn, players);
				}
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
	 * Ask for an integer from the player with the given question.
	 *
	 * @param question to be asked to the player
	 * @return the integer chosen by the player
	 */
	private int ask(String question) {
		System.out.print(question);
		try {
			return input.nextInt();
		} catch (Exception e) {
			input.nextLine();
			return ask(question);
		}
	}

	/**
	 * Ask for the type of player 1 or 2.
	 *
	 * @param num either 1 or 2 representing player 1 and 2
	 * @return a player object
	 */
	private Player player(final int num) {
		int choice = ask("Type for player " + num + 1 + ":\n (0) Human    (1) Random\n (2) Minimax  (3) Negamax\n (4) Menace   (5) Qlearning\n> ");

		Player player;
		switch (choice) {
			case 0:
				player = new PlayerHuman();
				break;
			case 1:
				player = new PlayerRandom();
				break;
			case 2:
				player = new PlayerMinimax();
				break;
			case 3:
				player = new PlayerNegamax();
				break;
			case 4:
				player = new PlayerMenace();
				break;
			case 5:
				player = new PlayerQlearning();
				break;
			default:
				return player(num);
		}

		player.setSymbol((num == 0) ? CROSS : CIRCLE);
		return player;
	}
}
