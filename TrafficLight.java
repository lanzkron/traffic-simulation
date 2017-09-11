import java.awt.Color;

/**
 * The name says it all... 
 * <br>
 * A traffic light is "a kind of" a <code>Vehicle</code> because the mathematical 
 * model supports <code>Vehicle</code>s interacting with other <code>Vehicle</code>s.
 * <br>
 * Therefore traffic lights can be added into the simulation without changing the 
 * logic of the program although this might not be the most intuitive way to think 
 * of traffic lights.
 */
public class TrafficLight extends Vehicle {
	// Duration of each colour of the traffic light in simulation time quanta.
	// Every TrafficLight starts out as red and orange is between green and red.
	protected int		red,		// [0,red)			red light
						green,		// [red,green)		green light
						orange;		// [green,orange)	orange light
	
	// which "colour" are you in now
	protected int		time;

	/**
	 * Construct a new TrafficLight object.
	 * @param initial position
	 * @param duration of red light
	 * @param duration of green light
	 * @param duration of orange light
	 */
	public TrafficLight(float p, int rr, int gg, int oo) {
		pos		= p;
		red		= rr;
		green	= red + gg;
		orange	= green + oo;
	}
	
	/**
	 * This (and getVelocity) is where the action takes place, the returned value 
	 * depends on which "colour" the <code>TrafficLight</code> is currently in.
	 * If it's green it "pretends" to be the <code>Vehicle</code> in front of it, 
	 * if it's red it gives correct information and if it's orange it checks whether 
	 * the <code>Vehicle</code> behind it will still pass it before it turns red and 
	 * answers appropriately.
	 * 
	 * @return what the car behind should think the position is.
	 */
	public float getPos() {
		if( time < red )			// red light
			return pos;
		else if( time < green )		// green light
			return next.getPos();
		else  {						// orange light
			 // If he won't reach me before I turn red
			if( (pos - prev.getRealPos()) > (prev.getRealVel()*(orange-time)) )
				return pos;
			else
				return next.getPos();
		}
	}

	/**
	 * This (and getPos) is where the action takes place, the returned value 
	 * depends on which "colour" the <code>TrafficLight</code> is currently in.
	 * If it's green it "pretends" to be the <code>Vehicle</code> in front of it, 
	 * if it's red it gives correct information and if it's orange it checks whether 
	 * the <code>Vehicle</code> behind it will still pass it before it turns red and 
	 * answers appropriately.
	 * 
	 * @return what the car behind should think the position is.
	 */
	public float	getVelocity() {
		if( time < red ) // red light
			return 0;
		else if( time < green ) // green light
			return next.getVel();
		else{// orange light
			if( (pos - prev.getRealPos()) > (prev.getRealVel()*(orange-time)) ) // If he won't reach me before I turn red
				return 0;
			else
				return next.getVel();
		}	
	}

	/**
	 * The main purpose of the function is to increment the time so that the lights
	 * will change naturally. In addition some house keeping is done, specifically 
	 * "placing" the <code>TrafficLight</code> between the appropriate 
	 * <code>Vehicle</code>s as some may have passed it.
	 * 
	 * @return next Vehicle to be driven.
	 */
	public Vehicle drive() {
		// increment mod, after orange comes red.		
		if(0 != orange)
			time = (time+1)%orange; 

		// save next car (for returning)
		Vehicle nxt = next;
		// let all cars that need to overtake
		while (pos < prev.getRealPos()) 
			swap();

		return nxt;
	}
	
	/**
	 * Draw a representation that indicates what colour light is now on.  
	 */
	public Drawing	draw( ) {
		
		DGroup ret = new DGroup();
		
		// body outline
		ret.add(new DPoly(new int[]{-10, -4, -4, 8, 8, -4, -4, -10},	
						  new int[] {4, 4, 0, 0, 12, 12, 8, 8}, 
						  8, Color.black));

		// white stop line
		/*ret.add( new DPoly(new int[]{-15, -15, -40, -40}, 
						   new int[]{4, 8, 8, 4},
						   4, Color.white) );/**/
		
		// seelct appropriate color
		Color col;
		if( time < red)
			col = Color.red;
		else if( time < green)
			col = Color.green;
		else
			col = Color.orange;
	
		// light
		ret.add(new DCircle(2, 6, 3, col));	
	
		// Move it off the road to the right		
		ret.move(28, 0); 
		
		return ret;
	}
}