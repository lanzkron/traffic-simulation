/**
 * This delaminate lanes of <code>Vehicle</code>s instead of the natural <code>null</code>.
 * It allows the rest of the <code>Vehicle</code>s to preform most operations on their next
 * and prev members without checking that they are not null by implementing the <code>Vehicle</code>
 * interface without doing much.
 */
public class FrontEnd extends Vehicle {
	/** First <code>Vehicle</code> in the lane. */
	private Vehicle prev;
	
	public FrontEnd(Vehicle p) {
		prev = p;	
	}
	
	/** Keep 100 meters in front of the first <code>Vehicle</code>.  */
	public float getPos() {
		return prev.getPos() + 100;
	}
	
	/** Does nothing. */
	public void setPos(float p) throws IllegalArgumentException { }
	
	/** Same velocity as the <code>Vehicle</code> behind it. */
	public float getVelocity() {
		return prev.getVel();
	}
	
	/** Does nothing. */
	public void setVelocity(float p) throws IllegalArgumentException {	}
	
	/** @return */
	public Vehicle drive() {
		return null;
	}
	
	/** @return null. */
	public Vehicle	getNext() {
		return null;
	}
	
	/** Does nothing. */
	public void	setNext(Vehicle v) { }
	
	public Vehicle	getPrevious() {
		return prev;
	}

	/** Does nothing. */
	public void	setPrevious(Vehicle v) { }

	/** @return an empty <code>DGroup</code>, effectively draw nothing. */
	public Drawing	draw( ) {
		return new DGroup();
	}
	
	/** Does nothing. */
	public void remove() { }
	
	/** @return <code>-1</code> as an indication that it's not a valid <code>Vehicle</code>. */
	public float getLocation() {
		return -1;
	}
			
}