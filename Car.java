import java.io.*;
import java.awt.*;
import java.lang.Exception;

/**
 * Representation of a car on the simulation road.
 * This is the most commonly used subclasses of the abstract <code>Vehicle</Code>
 * class.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see Vehicle
 */
public class Car extends Vehicle {
	protected Params par;
	protected static RoadParams road = new RoadParams();

	/**
	 * @param the <code>Vehicle</code> in front of this <code>Car</code>.
	 * @param the <code>Params</code> for this individual <code>Car</code>.
	 */
	public Car(float p, float v, Params pr) {
		par = pr;
		pos = p;
		vel = v;
	}
	
	/**
	 * Compute the acceleration appropriate for the present situation.
	 */
//	private void setAcc() {
//	}

	/**
	 * Advance car over one time quanta.  This function is the central part
	 * that causes the simulation to tick.
	 * For full details - see the manual.
	 * 
	 * @return next Vehicle to be driven.
	 */
	public Vehicle drive()	{
		// this is the main part of the simulation: this is where the cars
		// "drive" themselves.  for full details see manual
		
		float dX = next.getPos() - pos;  // distance to next car
		float dV = next.getVel() - vel;  // difference in speeds
		float dT = RoadParams.dT;
		if (dX < 0) 
			dX += road.roadLength;
		
		// accelerate to close in on to within the permitted distance from 
		// the next car.
		acc = vel*par.getSaftyTime() + par.getMinDistance() ; // goal distance
		acc /= dX;
		acc = par.getSensitivity()*(1 - acc);

		// account for speed difference (if car above is slowing down, plese
		// slow down too...)
		if( dV < 0 ) {
			acc -= dV*dV/(2*(dX - par.getMinDistance()));
		}

		// keep to within speed limit.
		if( vel > road.getSpeedLimit(pos) ) {
			acc -= par.getObedience()*(vel - road.getSpeedLimit(pos));
		}

		// update position value, using the acceleration calculated above.
		float tmp = dT*vel + RoadParams.dT2*acc/2;
		if ( tmp > 0 )
			pos += tmp;

		// update velocity value, using the acceleration calculated above.
		vel += dT*acc;
		if( vel < 0 )
			vel = 0;

		// deal with road wraparound.
		if (pos > RoadParams.roadLength)  {
			pos -= RoadParams.roadLength;
		}		

		// make sure not to overtake car ahead of you.  this could have some dire
		// consequences...  this only applies to car object.  Other Vehicle objects,
		// such as traffic lights, are meant to be "overtaken"
		if (pos >= next.getRealPos() && next instanceof Car) { 
			pos = next.getRealPos()-1;
		}		

		return next;		
	}

	public float getDeltaX()  {
		Vehicle v = next;
		while (! (v instanceof Car))  {
			v = v.getNext();
		}
		float ret = v.getRealPos() - pos;
		while (ret < 0)
			ret += RoadParams.roadLength;
		
		return ret;
	}
	
	public float getDeltaV()  {
		Vehicle v = next;
		while (! (v instanceof Car))  {
			v = v.getNext();
		}
		return v.getRealVel() - vel;
	}

	/**
	 *	Supply graphical representation for display.
	 *	@see <code>Drawing</code>
	 */
	public Drawing draw() {
		DGroup ret = new DGroup();

		float topSpeed = road.getTopSpeedLimit();
		float dv = vel/topSpeed;

		// Colour representation goes from blue (stopped) to red (top speed or higher) 
		// continuously , using Colors constructor that takes floats between zero 
		// and one.
		if(dv > 1)	dv = 1;
		
		// Car Body
		ret.add(new DPoly(new int[] {-4, 4, 4, -4},
						  new int[] {0, 0, 12, 12},
						  4, new Color(dv, 0, 1-dv))
				);
		// The colour of the roof of the car indicates whether it's speeding 
		// (black = yes, cyan = no)
		Color col = (vel > topSpeed)? Color.black : Color.cyan;

		// Roof
		ret.add(new DPoly(new int[]{ -2, 2, 2, -2},
						  new int[]{  4, 4, 8, 8},
						  4, col)
				);
		// Head lights
		ret.add(new DPoly(new int[]{ -3, 0, 3, 6, -6},
						  new int[]{ 0, -4, 0, -9, -7},
						  5, Color.yellow)
				);
		// Brake lights
		if( acc < 0 ) {
			ret.add(new DCircle(-2, 13, 2, Color.orange));
			ret.add(new DCircle(2, 13, 2, Color.orange));
		}
		return ret;
	}
}
