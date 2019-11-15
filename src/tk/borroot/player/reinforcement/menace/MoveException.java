package tk.borroot.player.reinforcement.menace;

/**
 * This exception is thrown when no move can be made.
 *
 * @author Bram Pulles
 */
public class MoveException extends Exception {

	MoveException(String message) {
		super(message);
	}
}
