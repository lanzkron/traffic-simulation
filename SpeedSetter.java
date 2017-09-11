/**
 *	Strategy for setting the initial speed of <code>Vehicle</code>s.
 *	See the Strategy design pattern in Gamma et all.
 */
public interface SpeedSetter {
	/** @param v the <code>Vehicle</code> that has its velocity set. */
	public void setSpeed(Vehicle v);
}

