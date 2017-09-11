import java.awt.Color;

/**
 * Representation of a police radar.  The radar is used to collect local data
 * from any point on the simulation road.  The radar <b>does not</b> cause cars to
 * slow down...  There is a limit to the intricacies a simulation can go into ;-> 
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see Vehicle
 */
public class Radar extends Vehicle  {

	private int numCars = 0;
	private float sumVelocites = 0;

	/**
	 * Construct a new Radar object
	 * @param initial position
	 */
	public Radar(float pos)  {
		setPos(pos);
	}
	
	/**
	 * Override of the default vehicle getPos() function.  Returns the position
	 * of the car in front, and thus makes itself nonexistant as an entity on
	 * the road.
	 * @return position of next vehicle
	 */
	public float getPos()  {
		return next.getPos();
	}
	
	/**
	 * Override of the default vehicle getVel() function.  Returns the speed
	 * of the car in front, and thus makes itself nonexistant as an entity on
	 * the road.
	 * @return velocity of next vehicle
	 */
	public float getVel()  {
		return next.getVel();
	}

	public void reset()  {
		numCars = 0;
		sumVelocites = 0;	
	}
	
	public float getNumCars()  {
		return numCars;
	}
	
	public float getAvgVel()  {
		return (numCars == 0) ? 0 : sumVelocites / numCars ;
	}
	
	/**
	 * A degenerate implementation of <code>Vehicle</Code>'s drive() function.
	 * Allows all cars that need to overtake to swap places in the list.
	 * @return next car to be driven.
	 */
	public Vehicle drive()  {
		// save next car (for returning)
		Vehicle nxt = next;
		// let all cars that need to overtake.
		while (pos < prev.getRealPos()) { 	
			numCars ++;
			sumVelocites += prev.getRealVel();
			swap();
		}
		return nxt;
	}

	/**
	 * Supply graphical representation for display.  (I got told that it looks
	 * like a camel more than a police radar...  I was very offended.)
	 * @see <code>Drawing</code>
	 */
	public Drawing	draw( )  {
		DGroup ret = new DGroup();

		// dorgalim (legs)
		ret.add(new DPoly(new int[]{0, 8, 8, 0, 0, 8, 8, 0},
						  new int[]{6, 4, -4, -6, -4, -2, 2, 4},
						  8, Color.lightGray));

		// camera body.
		ret.add(new DPoly(new int[]{8, 16, 16, 8},
						  new int[]{6, 6, -6, -6},
						  4, Color.black));

		// move the drawing off the road.
		ret.move(20, 0);
		
		return ret;
	}
}
