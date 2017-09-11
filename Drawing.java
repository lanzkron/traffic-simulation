import java.awt.*;

/**
 * Graphical representation of elements in the simulation.
 * <br>
 * As the location on the road and on the screen aren't directly connected the GUI 
 * can position the <code>Drawing</code> in the correct place by using the 
 * interface's functions.
 * <br>
 * All the functions move the <code>Drawing</code> around the (0,0) point, allowing 
 * for orientation change while maintaining visual perspective. 
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */ 
public interface Drawing {
	/**
	 * Move the datum point from (0,0) to (dx,dy).
	 * @param dx amount to move on <code>x</code> axis.
	 * @param dy amount to move on <code>y</code> axis.
	 */
	void move(int dx, int dy);
	/**
	 * Rotate around the datum using the rotation matrix.
	 * <tt>
	 * <br>( cos(theta) sin(theta) )
	 * <br>(-sin(theta) cos(theta) )
	 * </tt>
	 * @param <code>cos(theta)</code> where theta is the angle to rotate in radians.
	 * @param <code>sin(theta)</code> where theta is the angle to rotate in radians.
	 */
	void rotate(float cos, float sin);
	/**
	 *	Stretch on the <code>x</code> and <code>y</code> axes using the stretch matrix.
	 *	<tt>
	 *	<br>( fx 0  &nbsp;)
	 * 	<br>( 0&nbsp;  fy )
	 *	</tt>
	 *	@param fx factor by with to stretch on the <code>x</code> axis (1 does nothing).
	 *	@param fy factor by with to stretch on the <code>y</code> axis (1 does nothing).
	 */
	void resize(float fx, float fy);
	/**
	 * Preform the actual drawing.
	 * @param g the graphics context. 
	 */
	void draw(Graphics g);	
}
