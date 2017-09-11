/**
 * Set the same speed for all <code>Vehicles</code>.
 */
public class ConstSpeedSetter implements SpeedSetter {
	/** Constant speed to which all <code>Vehicle</code>s are set.	*/
	private float speed;
	
	/** speed defaults to zero. */
	public ConstSpeedSetter() {} 
	/**	@param speed to set to. */
	public ConstSpeedSetter(float f) {
		speed = f;
	}

	public void setSpeed(Vehicle v) {
		v.setVel( speed );
	}
}