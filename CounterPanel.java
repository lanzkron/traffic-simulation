import java.awt.*;
import java.awt.event.*;

/**
 * A digital number output.  
 * Shows a digital display of a number value
 *
 * @version 1.2
 * @author Misha Sklarz
 * @author Motti Lanzkron
 * @see Canvas
 */ 
public class CounterPanel extends Canvas  {
	/** Current number showing */
	private int num;
	/** Number of digits to show */
	private int digs;
	/** Image representations of the digits */
	private Image digits[];
	/** the dimension of a single image bitmap in the above array */
	private Dimension dim;
		
	/**
	 * Construct a new CounterPanel display
	 * 
	 * @param number of digits to set space for.
	 * @param number to start with (usually 0).
	 * @param an array of digit bitmaps.
	 */ 
	public CounterPanel(int digit, int number, Image img[])  {
		digits = img;
		// set dimension of a single digit bitmap
		dim = new Dimension(digits[0].getWidth(null), digits[0].getHeight(null));
		num = number;
		digs = digit;
	}		
	
	/**
	 * Paint digits on the canvas. 
	 * Overrides <code>Canvas</code>'s paint() function.  This function
	 * is triggered automatically by the framework when the area taken up by
	 * the component is invalidated
	 * 
	 * @param a <code>Graphics</code> instance to draw on.
	 */
	public void paint(Graphics g)  {
		for (int i=(digs-1), j=1; i>=0; i--, j*=10)  {
			g.drawImage(digits[(num/j) % 10], i*(dim.width-1), 0, this);
		}
	}
	
	/**
	 * Return the preferred size of the component.
	 * Overrides <code>Canvas</code>'s getPreferredSize() function.
	 * This function is called automatically by the framework while laying
	 * the component inside a <code>Containter</code> object.
	 */
	public Dimension getPreferredSize()  {
		// calculate the size of the component :
		return new Dimension(digs*dim.width, dim.height);
	}	

	/**
	 * Add one to the value displayed on the display, and cause the 
	 * component to be invalidated so that the change will be shown.
	 */
	public void increment()  {
		num++;
		// repaint as little as possible - only the digits that might
		// have changed in this operation.
		int dig = (int)(Math.log(num) / Math.log(10.0)) + 1;
		repaint(digs-dig*dim.width, 0, dig*dim.width,  dim.height);
	}

	/**
	 * Take one from the value displayed on the display, and cause the 
	 * component to be invalidated so that the change will be shown.
	 */
	public void decrement()  {
		num--;
		// repaint as little as possible - only the digits that might
		// have changed in this operation.
		int dig = (int)(Math.log(num) / Math.log(10.0)) + 1;
		repaint(digs-dig*dim.width, 0, dig*dim.width,  dim.height);
	}
	
	/**
	 * Set the value displayed on the display, and cause the 
	 * component to be invalidated so that the change will be shown.
	 */
	public void setNumber(int n)  {
		num = n;
		// repaint as little as possible - only the digits that might
		// have changed in this operation.
		int dig = (int)(Math.log(num) / Math.log(10.0)) + 1;
		repaint(digs-dig*dim.width, 0, dig*dim.width,  dim.height);
	}
}
