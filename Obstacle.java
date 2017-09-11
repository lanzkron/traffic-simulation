/**
 * A class representing an section of the road with special driving conditions.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */
public class Obstacle {
	/** Beginning and end of the section [from, to)	 */
	private int from, to;
	/** Speed limit in this section is limit*resist	 */
	private float resist;
	
	/**
	 * @param starting point (if less than t otherwise they are swapped)
	 * @param end point (if greater than f otherwise they are swapped)
	 * @param resistance
	 */
	public Obstacle(int f, int t, float r) {
		if ( f < t ) {
			from = f;
			to = t;
		}
		else  {
			from = t;
			to = f;
		}		
		resist = r;
	}
	
	/** @return whether the parameter is in the [from, to) interval. */
	public boolean contains(float p) {
		return (p >= from && p < to);
	}
	
	/** @return resistance	 */
	public float getResistance() {
		return resist;
	}
	
	/** @param new resistance */
	public void setResistance(float res)  {
		resist = res;
	}
	
	/** 
	 * checks to see whether the intervals defined by 
	 * both <code>Obstacle</code>s overlap	
	 */
	public boolean overlaps(Obstacle ob) {
		// Assume from is always less than to.
		return (ob.to > from && ob.from < to);
	}
	
	/**
	 * <b><u>Warning</u>:</b> The relationship defined by equals isn't transitive!
	 */
	public boolean equals(Object ob) {
		if( ob instanceof Obstacle )
			return overlaps((Obstacle)ob); // food for thought....
		
		return false;
	}

	/** @return where the obstacle starts */
	public int getFrom()  {
		return from;
	}
	
	/** @return where the obstacle gets to */
	public int getTo()  {
		return to;
	}
}

