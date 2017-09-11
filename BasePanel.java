import java.awt.*;
import java.awt.event.*;

/**
 * Serves as the common superclass to all the option panels. 
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class BasePanel extends Panel implements MouseListener  {
	/** The active document, to which the Panel is associated */
	protected SimDocument doc;
	protected static final int SELECT = 0;
	protected static final int INSERT = 1;
	protected static final int DELETE = 2;	

	/** 
	 * Construction.  Do some layout common to all panels.
	 */
	public BasePanel(SimDocument d)  {
		doc = d;
		setLayout(new GridLayout(8, 1, 8, 8));
	}

	/**
	 * Empty implemetation, to be overridden be subclasses.
	 */
	public void specialDraw(Graphics g) {}
	
	/**
	 * Convert cartesian point to the polar angle
	 * @param point to be converted
	 * @return angle
	 */
	protected double toAngle(Point p)  {
		p.x = p.x - 250;
		p.y = 250 - p.y;
		double ret = Math.atan2(p.y, p.x);
		return (ret > 0) ? ret : ret + 2 * Math.PI;
	}

	/**
	 * Convert cartesian point to the polar radius
	 * @param point to be converted
	 * @return radius
	 */
	protected double toRadius(Point p)  {
		p.x = p.x - 250;
		p.y = 250 - p.y;
		return Math.sqrt(p.x*p.x+p.y*p.y);
	}

	public void clearData()  {
	}
	
	/**
	 * Checks if the MouseEvent object represents an envent that ocurred on 
	 * the strip between radius min and max.
	 * 
	 * @param MouseEvent object representing this event
	 * @param Inner radius.
	 * @param Outer radius.
	 * @return true if event is on road.  false otherwise.
	 */
	protected boolean isInRange(MouseEvent me, int min, int max)  {
		double rad = toRadius(me.getPoint());
		return (min <= rad && rad <= max) ? true : false ;
	}

	// Empty implementation of mouse event function - as per contract
	// with the MouseListener interface.
	public void mouseClicked(MouseEvent me)		{}
	public void mousePressed(MouseEvent me)		{}
	public void mouseReleased(MouseEvent me)	{}
	public void mouseEntered(MouseEvent me)		{}
	public void mouseExited(MouseEvent me)		{}
}

