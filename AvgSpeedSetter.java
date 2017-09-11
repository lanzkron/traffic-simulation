/**
 * Set a <code>Vehicle</code>'s speed to the average of those in front and behind it.

 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see SpeedSetter
 */
public class AvgSpeedSetter implements SpeedSetter {
	public void setSpeed(Vehicle v) {
		v.setVel( (v.getNext().getVel() +
						v.getPrev().getVel()) /2);
	}
}
