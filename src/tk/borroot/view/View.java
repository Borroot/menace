package tk.borroot.view;

import java.util.Scanner;

/**
 * This class can be used to get user input, thus providing a view for the player.
 *
 * @author Bram Pulles
 */
public class View {

	private static Scanner input = new Scanner(System.in);

	/**
	 * This class is not supposed to be instantiated.
	 */
	private View() {
	}

	/**
	 * Ask for an integer from the player with the given question.
	 *
	 * @param question to be asked to the player
	 * @return the integer chosen by the player
	 */
	public static int askInt(String question) {
		System.out.print(question);
		try {
			return input.nextInt();
		} catch (Exception e) {
			input.nextLine();
			return askInt(question);
		}
	}

	/**
	 * Ask for float from the player with the given question.
	 *
	 * @param question to be asked to the player
	 * @return the float chosen by the player
	 */
	public static float askFloat(String question) {
		System.out.print(question);
		try {
			return input.nextFloat();
		} catch (Exception e) {
			input.nextLine();
			return askFloat(question);
		}
	}
}
