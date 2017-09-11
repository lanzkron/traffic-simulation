import java.awt.Graphics;

/**
 * Single <code>Vehicle</code> abstract superclass for transport simulation.
 * All measurements  are in meters per second.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */
public abstract class Vehicle {
	public Vehicle next;
	public Vehicle prev;
	
	public float pos;
	public float vel;
	public float acc;
	
	/**
	 * @return the position of the <code>Vehicle</code>.
	 * <b>For use in driving.
	 * <b><u>Note</u>:</b> This may not be the actual position if the 
	 * <code>Vehicle</code> is something fancy like a <code>TrafficLight</code>.
	 * @see getRealPos
	 */	
	public float getPos() {
		return pos;
	}

	/**
	 * Reset the <code>Vehicle</code>'s position.
	 * @param position to be set.
	 * @exception if an illegal position is given or resetting not allowed.
	 */
	public void setPos(float p) throws IllegalArgumentException  {
		if (p < 0 || p > 1000) 
			throw new IllegalArgumentException();
		pos = p;
	}

	/**
	 * @return the correct position of the <code>Vehicle</code>, regardless of 
	 * the <code>Vehicle</code>'s implementation.
	 * @see getPos
	 */
	public float getRealPos()  {
		return pos;
	}
		
	/**
	 * @return the velocity of the <code>Vehicle</code>.
	 * <b><u>Note</u>:</b> This may not be the actual velocity if the 
	 * <code>Vehicle</code> is something fancy like a <code>TrafficLight</code>.
	 * @see getRealVel
	 */
	public float getVel()  {
		return vel;
	}

	/**
	 * Reset the <code>Vehicle</code>'s velocity.
	 * @param Speed to be set.
	 * @exception if an illegal position is given or resetting not allowed.
	 */
	public void setVel(float v) throws IllegalArgumentException  {
		if (v < 0)
			throw new IllegalArgumentException();
		vel = v;
	}

	/**
	 * @return the correct velocity of the <code>Vehicle</code>, regardless of 
	 * the <code>Vehicle</code>'s implementation.
	 * @see getPos
	 */
	public float getRealVel()  {
		return vel;
	}

	
	/**
	 * @return the acceleration of the <code>Vehicle</code>.
	 */
	public float getAcc()  {
		return acc;
	}

	/** 
 	 * Advance over one time quanta.
 	 * @return information about the <code>Vehicle</code> after progress.
	 */
	public abstract  Vehicle drive();

	/**
	 * @return the <code>Vehicle</code> currently directly ahead of this <code>Vehicle</code>.
	 */
	public Vehicle getNext() {
		return next;
	}

	/**
	 * @param the <code>Vehicle</code> to be placed directly ahead of this <code>Vehicle</code>.
	 */
	public void setNext(Vehicle v)  {
		next = v;
		next.setPrev(this);
	}

	/**
	 * @return the <code>Vehicle</code> currently directly behind this <code>Vehicle</code>.
	 */
	public Vehicle getPrev()  {
		return prev;
	}

	/**
	 * @param the <code>Vehicle</code> to be placed directly behind this <code>Vehicle</code>.
	 */
	public void setPrev(Vehicle v)  {
		prev = v;
	}

	/**
	 * present graphical representation of this <code>Vehicle</code> 
	 * as a <code>Drawing</Code>.
	 * @see <code>Drawing</Code>.
	 */
	public abstract Drawing draw( );
	
	/**
	 *  remove the <code>Vehicle</code> from the road.
	 */
	public void remove()  {
		prev.setNext(next);
		next = prev = null;		
	}

	/**
	 *  swap the <code>Vehicle</code>'s position with the one immediately  behind it.
	 */
	public void swap()  {
		Vehicle pre = getPrev();
		Vehicle prepre = pre.getPrev();
		Vehicle nxt = getNext();
		
		prepre.setNext(this);
		this.setNext(pre);
		pre.setNext(nxt);
	}
}