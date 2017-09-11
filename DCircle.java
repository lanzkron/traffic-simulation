import java.awt.*;

/** 
 * Simple Circle with colour.	 
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */
public class DCircle implements Drawing {
	private Point p;
	private int r;
	private Color c;
	
	public DCircle(Point pp, int rr, Color cc) {
		p = pp;
		r = rr;
		c = cc;
	}
	
	public DCircle(int x, int y, int rr, Color cc) {
		this(new Point(x,y), rr, cc);
	}
	
	/** 
	 * Wrapper for <code>java.awt.Point.translate(int, int)</code>	 
	 */
	public void move(int dx, int dy) {
		p.translate(dx,dy);
	}
	
	/** 
	 * This function is necessary because the circle should be positioned 
	 * differently on the plane (i.e. rotate around the center).
	 */
	public void rotate(float cos, float sin) {
		// Again x is updated only after the computation is completed
		int x = (int)(cos*p.x  + sin*p.y);
		p.y = (int)( -sin*p.x + cos*p.y);
		p.x = x;
	}
	/** 
	 * The position of the centre of the circle is changed appropriately and 
	 * the radius is resized by the average of both the parameters.
	 */
	public void resize(float fx, float fy) {
		p.x *= fx;
		p.y *= fy;
		r *=  (fx+fy)/2;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(p.x - r, p.y - r, 2*r, 2*r);
	}
	
}