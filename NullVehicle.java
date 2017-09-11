/**
 * This delaminate lanes of <code>Vehicle</code>s instead of the natural <code>null</code>.
 * It allows the rest of the <code>Vehicle</code>s to preform most operations on their next
 * and prev members without checking that they are not null by implementing the <code>Vehicle</code>
 * interface without doing much.
 *
 * Inspired by the <b>Singleton</b> pattern in "Design Patterns, Gamma et. al".
 * This pattern was used to enable the use of the "==" operator instead of an equals
 * function call to simplify the code.
 */
public class NullVehicle implements Vehicle {
	/**
	 * Only possible instance.
	 */
	public static NullVehicle singelton = new NullVehicle();

	/**
	 * prevent outside creation.
	 */
	private NullVehicle(){}

	/**
	 * @return -1 as an indication that this is a <code>nullVehicle</code>.
	 */
	public float getPos() {	return -1;	}

	/**
	 * @exception always throws an exception the indicate this is a <code>NullVehicle</code>.
	 */
	public	void	setPos(float p) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	/**
	 * @return -1 as an indication that this is a <code>nullVehicle</code>.
	 */
	public float	getVelocity() { return -1;	}

	/**
	 * @exception always throws an exception the indicate this is a <code>nullVehicle</code>.
	 */
	public void	setVelocity(float v) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	/**
	 * @return an invalid <code>DriveData</code>.
	 */
	public void drive() {	
	}

	/**
	 * Potentially infinite list of <code>nullVehicle</code>.
	 * @return the <code>nullVehicle</code>.
	 */
	public Vehicle	getNext() {	return singelton;	}

	/** Does nothing	 */
	public void	setNext(Vehicle v) {}

	/**
	 * Potentially infinite list of <code>nullVehicle</code>.
	 * @return the <code>nullVehicle</code>.
	 */
	public Vehicle	getPrevious() {	return singelton;	}

	/** Does nothing	 */
	public void	setPrevious(Vehicle v) {}

	/**
	 * @return returns null
	 */
	public Drawing	draw( ) { return null;	}

	/** Does nothing	 */
	public void remove() {}

	/**
	 * @return -1 as an indication that this is a <code>nullVehicle</code>.
	 */
	public float getLocation() {	return -1;	}
}
