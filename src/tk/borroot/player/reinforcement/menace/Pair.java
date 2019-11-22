package tk.borroot.player.reinforcement.menace;

/**
 * Create a pair, so one object with two objects inside.
 *
 * @param <X> object type 1
 * @param <Y> object type 2
 * @author Bram Pulles
 */
public class Pair<X, Y> {

	public X x;
	public Y y;

	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}
