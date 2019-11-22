package tk.borroot.player.reinforcement.qlearning;

/**
 * Create a triplet, so one object with three objects inside.
 *
 * @param <X> object type 1
 * @param <Y> object type 2
 * @param <Z> object type 3
 * @author Bram Pulles
 */
public class Triplet<X, Y, Z> {

	public X x;
	public Y y;
	public Z z;

	Triplet(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
