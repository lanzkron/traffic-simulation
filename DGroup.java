import java.util.Vector;
import java.awt.Graphics;
/**
 * A collection of <code>Drawing</code>, all operations on this class are preformed 
 * on all the objects it contains. Order of insertion is maintained in drawing so 
 * that shapes added later overlap earlier shapes rather than vice versa.
 * <br>
 * Inspired by the <b>Composite</b> pattern in "Design Patterns, Gamma et. al".
 */
public class DGroup implements Drawing {
	// The container is subject to change, it's currently a Vector because 
	// I'm preferring simplicity over efficiency and there shouldn't be too many
	// objects in each container.
	private Vector vec;
	
	public DGroup() {
		vec = new Vector();
	}
	
	public DGroup(int n) {
		vec = new Vector(n);
	}
	
	/**
	 * Insert a new <code>Drawing</code> into the collection (maintain order).
	 * @param the <code>Drawing</code> to be added.
	 */
	void add(Drawing d) {
		vec.addElement(d);
	}
	
	/**
	 * Iterate over all <code>Drawing</code>s and move them.
	 */
	public void move(int dx, int dy) {
		for(int i=0; i < vec.size(); ++i) 
			((Drawing)vec.elementAt(i)).move(dx,dy);
	}
	
	/**
	 * Iterate over all <code>Drawing</code>s and rotate them.
	 */
	public void rotate(float cos, float sin) {
		for(int i=0; i < vec.size(); ++i) 
			((Drawing)vec.elementAt(i)).rotate(cos, sin);
	}
	
	/**
	 * Iterate over all <code>Drawing</code>s and resize them.
	 */
	public void resize(float fx, float fy) {
		for(int i=0; i< vec.size(); ++i)
			((Drawing)vec.elementAt(i)).resize(fx, fy);
	}
	
	/**
	 * Iterate over all <code>Drawing</code>s and draw them in order. Order is
	 * relevant because the overlapping must be maintained.
	 */
	public void draw(Graphics g) {
		for(int i=0; i < vec.size(); ++i) 
			((Drawing)vec.elementAt(i)).draw(g);
	}
}