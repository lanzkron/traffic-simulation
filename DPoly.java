import java.awt.*;
/** 
 * Wrapper for java.awt.Polygon with added colour.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see java.awt.Polygon
 * @see Drawing
 */
public class DPoly extends Polygon implements Drawing {
	private Color c;
	
	public DPoly(int x[], int y[], int n, Color cc){
		super(x, y, n);
		c = cc;
	}
	
	/** 
	 * Wrapper for <code>java.awt.Polygon.translate(int,int)</code>	 
	 */
	public void move(int dx, int dy) {
		translate(dx, dy);
	}
	
	public void rotate(float cos, float sin) {		
		for(int i=0; i< npoints; ++i) {
			// Using the rotation matrix (see Drawing.rotate), we need the original values of
			// x and y so the new x value is saved until the original is no longer needed.
			int x = (int)(cos*xpoints[i] + sin*ypoints[i]);
			ypoints[i] = (int)( -sin*xpoints[i] + cos*ypoints[i]);
			xpoints[i] = x;
		}
	}
	
	public void resize(float fx, float fy) {
		for(int i=0; i< npoints; ++i) {
			xpoints[i] *= fx;
			ypoints[i] *= fy;
		}
	}
	
	public void draw(Graphics g){
		g.setColor(c);
		g.fillPolygon(this);
	}

}