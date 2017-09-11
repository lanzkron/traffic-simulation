/** Set the speed to a random value between zero and the speed limit. */
public class RandSpeedSetter implements SpeedSetter {
	/** Upper limit on speed */
	private float max;
	
	/** @param m the speed limit. */
	public RandSpeedSetter(float m) { 
		max = m;
	}
	
	public void setSpeed(Vehicle v) {
		v.setVel((float)Math.random()*max);
	}
}
