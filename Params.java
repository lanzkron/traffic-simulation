/**
 * Parameters specific for a <code>Vehicle</code> or a group of <code>Vehicle</code>s.
 *
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see NumberField 
 */
public class Params {
	/** Sensitivity	to changes in the flow of traffic. */
	private float sens	= 3;
	/** Minimal distance between two <code>Vehicle</code>s in meters (includes their length).	*/
	private float minD	= 10;
	/** Safety time distance between two <code>Vehicle</code>s in seconds.	*/
	private float safty	= 2;
	/** Adherence to the speed limit.	*/
	private float ob	= 2;
	
	/** @return sensitivity	 */
	public float	getSensitivity()		{	return sens;	}
	/** @return minimal distance which includes the <code>Vehicle</code>'s length. */
	public float	getMinDistance()		{	return minD;	}
	/** @return safety time	 */
	public float	getSaftyTime()			{	return safty;	}
	/** @return how much importance this <code>Vehicle</code> gives to the speed limit */
	public float	getObedience()			{	return ob;		}
	
	/**
	 * Change the sensitivity.
	 * @param new sensitivity.
	 * @exception <code>IllegalArgumentException</code> if the parameter is illegal (must be greater than zero).
	 */
	void setSensitivity(float f)	throws IllegalArgumentException {	
		if(f <= 0)
			throw new IllegalArgumentException();
		sens = f;
	}

	/**
	 * Change the minimal distance.
	 * @param new minimal distance.
	 * @exception <code>IllegalArgumentException</code> if the parameter is illegal (must be greater than zero).
	 */
	void setMinDistance(float f) throws IllegalArgumentException {	
		if(f <= 0)
			throw new IllegalArgumentException();
		minD = f;
	}
	
	/**
	 * Change the safety time.
	 * @param new safety time.
	 * @exception <code>IllegalArgumentException</code> if the parameter is illegal (must be greater than zero).
	 */
	void setSaftyTime(float f) throws IllegalArgumentException {
		if(f <= 0 )
			throw new IllegalArgumentException();
		safty = f;
	}

	/**
	 * Change the obedience level.
	 * @param new obedience level.
	 * @exception <code>IllegalArgumentException</code> if the parameter is illegal.
	 */
	void setObedience(float f) throws IllegalArgumentException {
		ob = f;
	}
}
