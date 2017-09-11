import java.awt.*;

/**
 * A special Vehicle to head the Vehicle linked list.  This car keeps the position 0
 * and thus solves some wrap around problems.  It also "remembers" the data of the car
 * ahead of it, and supplise it to the <code>Vehicle</code> behind 
 * so that all the <code>while</code>s in a loop get data about the same time quanta.
 * 
 * <br>
 * <b>Note:</b> This is not a "real" car, only a place holder at the zero point.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see Vehicle
 */
public class FirstCar extends Vehicle {
	
	/** 
	 * Construct FirstCar
	 * */
	public FirstCar() {
	}
	
	/** 
	 * @return "real" zero velocity.
	 */
	public float getRealVel() {
		return 0;
	}
		
	/** 
	 * @return "real" zero position. 
	 */	
	public float getRealPos() {
		return 0;
	}

	/**
	 * Remember the next Vehicles data, and then set him as next in the list.
	 */
	public void setNext(Vehicle v)  {
		vel = v.getVel();
		pos = v.getPos();
		super.setNext(v);
	}
	
	/**
	 * A degenerate implementation of <code>Vehicle</Code>'s drive() function.
	 * Allows all cars that need to overtake to swap places in the list.
	 * keeps the next car's data, for pasing on the the Vehicle behind...
	 * @return next car to be driven.
	 */
	public Vehicle drive()	{
		// save next car for returning.
		Vehicle nxt = next;
		// let all cars that need to overtake.
		while (prev.getRealPos() < next.getPos()) {
			swap();
		}
		// keep data from next car.		
		pos = next.getPos();
		vel = next.getVel();
		
		return nxt;
	}

	/**
	 * Supply graphical representation for display. 
	 * @see <code>Drawing</code>
	 */
	public Drawing draw() {
		DGroup ret = new DGroup();
		// white line
		ret.add(new DPoly(new int[] {-15, 15, 15, -15},
						  new int[] {  2,  2, -2,  -2},
						  4, Color.white));
		return ret;
	}  
}
