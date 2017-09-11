/**
 * 
 */
public class SinSpeedSetter implements SpeedSetter {
//	private int count;
	private int cycles;
	private float max;
	private static final double pi500 = Math.PI / 500;
	
	public SinSpeedSetter(int cycles, float max_speed) {
		this.cycles = cycles;
		max = max_speed;
	}
	
	public void setSpeed(Vehicle v) {
		v.setVel((float)(Math.sin(cycles*pi500*v.getRealPos())*0.5 + 0.5) * max);
	}
	
}