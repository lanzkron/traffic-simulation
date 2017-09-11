import java.awt.*;

/**
 * 
 * 
 */
public class Road  {
	/** First <code>Vehicle</code> on the road.	 */
	private FirstCar first;
	/** Number of <code>Vehicle</code>s.	 */
//	private int vCount;
	/** Strategy to set new <code>Vehicle</code>'s initial speed.	*/
	public SpeedSetter speedSetter;
	private static final float pi2 = (float)Math.PI * 2;
	private Params pr = new Params();

	private double avgVel;
	private int    numCars;
	
	public Road()  {
		first = new FirstCar();
		first.setNext(first);
		avgVel = 0;
		numCars = 0;
	}
	
	public void removeFirstVehicle()  {
		Vehicle del = first.next;
		while (del != first && ! (del instanceof Car))  {
			del = del.next;
		}
		if (del != first)  {
			del.remove();
			numCars --;		}
	}
	/**
	 * Set the positions of all the <code>Vehicle</code>s.
	 * @param the distance between every two <code>Vehicle</code>s.
	 * @param amount of randomization to insert into the distances.
	 * If the distance is too great to fit onto the <code>Road</code> then 
	 * the maximal possible equal distance is picked.
	 */
/*	public void setPositions(float dst, float variance) {
//		if ((dist * vCount) >	1000 )
//			dist = 1000 / vCount;

		float dist = 1000 / vCount;
		if (variance > dist/2 )
			variance = dist/2;
		
		Vehicle v = first.getNext();
		for (int i=0; v!=first; i++, v = v.getNext() ) {
			v.setPos(dist * i);// + (variance * (float)(Math.random()-0.5)));
			speedSetter.setSpeed(v);
		}
		first.setPos(first.getNext().getPos());
		first.setVel(first.getNext().getVel());
	}
*/	
	public void addVehicle(Vehicle v) {
		Vehicle e = first.getNext();		while (e.getRealPos() < v.getRealPos() && e != first)  {			e = e.getNext();		}		e.getPrev().setNext(v);		v.setNext(e);		if (v instanceof Car)  {			numCars ++;		}	}
	
	public void doStep() {
		int numCars = 0;
		double sumVel = 0;
		Vehicle v = first.getNext();
		for (; v != first; v = v.drive());
		first.drive();
	}

	public double getAvgVel()  {
		double sumVel = 0.0;
		Vehicle v = first.getNext();
		for (; v != first; v = v.getNext())  {
			if (v instanceof Car )  {
				sumVel += v.getVel();
			}
		}		
		return sumVel / numCars;
	}
	
	public int getNumCars()  {
		return numCars;
	}
	
	public void drawVehicles(Graphics g)  {
		float cos, sin, rad;
		int x, y;
		Vehicle v = first;
		do { // (; v!=first; v = v.getNext())  {
			rad = pi2 * v.getRealPos()/RoadParams.roadLength;
			cos = (float)Math.cos(rad);
			sin = (float)Math.sin(rad);
			x = (int)(205 *  cos);
			y = -(int)(205 * sin);
			Drawing d = v.draw();
			d.rotate(cos, sin);
			d.move(x, y);
			d.draw(g);
			v = v.getNext();
		}  while (v != first);
	}

    public void clear() {
		first.setNext(first);
		numCars = 0;
    }
	
	public Vehicle getVehicleAt(double pos, Class c)  {
		Vehicle v = first.getNext();		for (; v != first; v = v.getNext())  {			if ((Math.abs(pos - v.getRealPos()) < 10) &&				c.isInstance(v))
				return v;		}		return null;
	}
	
	public Vehicle remVehicleAt(double pos, Class c)  {
		if (numCars <= 1 || numCars >= 50)			return null;
					Vehicle v = getVehicleAt(pos, c);		if (v != null)  {			v.remove();
			if (v instanceof Car) 				numCars --;
			return v;		}		return null;
	}}	
