/**
 * Supplies all the <code>Vehicle</code>s in the simulation with the parameters that
 * are shared by all <code>Vehicle</code>s.
 */
public class RoadParams {
	/** Constant length of the road in meters, set to 1000.	 */
	public static final float roadLength = 1000;
	/** Constant logical time between physical steps in the simulation, set to 0.5. */
	public static final float dT  = 0.5f;
	/** dT squared, to prevent repeated multiplication */
	public static final float dT2 = dT*dT;
	/** Maximal speed limit	in meters per second. */
	protected float limit;

	public ObstacleCollection obstacles;

	/**
	 * Default constructor, sets this value
	 * <br>limit = 25 (90 kph)
	 */
	public RoadParams() {
		limit = 25;
		obstacles = new ObstacleCollection();
	}
	/**
	 * @param rl the length of the road.
	 * @param t the logical time gap between physical time quantm.
	 * @param lim the speed limit.
	 */
	public RoadParams(float lim) {
		setSpeedLimit(lim);
	}

	/**
	 * @param rl the length of the road.
	 * @param t the logical time gap between physical time quantm.
	 * @param lim the speed limit.
	 */
	public void setSpeedLimit(float lim) {
		limit = lim;
	}

	/**	Inform the function caller as to the quality of the road in a certain location. obstacles and
	 * bad driving conditions are implemented (and indicated) by lower speed limits.
	 * @param pos the function caller's current location.
	 * @return the appropriate speed limit.
	 */
	public float getSpeedLimit(float pos) {
		return limit * obstacles.getResistance(pos);
	}

	/** @return default speed limit.	 */
	public float getTopSpeedLimit()	{	return limit;	}


	public boolean addObstacle(Obstacle ob) {
		try {
			obstacles.addObstacle(ob);
		}
		catch (OverLappingObstacleException olo) {
			return false;
		}
		return true;
	}
	
	public boolean removeObstacle(Obstacle ob) {
		return obstacles.removeObstacle(ob);
	}


}
