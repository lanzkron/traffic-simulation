import java.awt.*;
import java.util.Vector;

/**
 * All the <code>Obstacle</code>s on the road.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */
public class ObstacleCollection {
	/** Holds the <code>Obstacle</code>s	 */
	private Vector vec = new Vector();
	
	private static final float pi2 = (float)Math.PI * 2;

	/** Add an <code>Obstacle</code> to the collection.
	 * @param the <code>Obstacle</code> to be added.
	 * @exception <code>OverLappingObstacle</code> overlapping <code>Obstacle</code> 
	 * are not allowed
	 */
	public void addObstacle(Obstacle ob) throws OverLappingObstacleException {
		for (int i=0; i< vec.size(); ++i)
			if (((Obstacle)vec.elementAt(i)).overlaps(ob) )
			   throw new OverLappingObstacleException();

		vec.addElement(ob);
	}

	/**
	 * Search for an <code>Obstacle</code> that contains the specified position and 
	 * returns the resistance at that point (default is <code>1</code>.
	 * 
	 * @param position to look at.
	 * @return if pos is in an <code>Obstacle</code> return the resistance, otherwise 
	 * <code>1</code>
	 */
	public float getResistance(float pos) {
		int i = vec.indexOf(new Obstacle((int)pos,(int)pos+1,0));
		if (i < 0) // No obstacle
			return 1; // No resistance
		return ((Obstacle)vec.elementAt(i)).getResistance();
	}

	/** 
	 * @param position to look at.
	 * @return the obstacle at the position.  null if none found.
	 */
	public Obstacle getObstacleAt(float pos) {
		int i = vec.indexOf(new Obstacle((int)pos,(int)pos+1,0));
		if(i < 0) // No obstacle
			return null; 
		return ((Obstacle)vec.elementAt(i));
	}	
	
	/** 
	 * Removes the occurrence of the argument from this Collection.
	 * 
	 * @param obstacle to be removed.
	 * @return <code>true</code> if the argument was a component of this 
	 * collection; <code>false</code> otherwise.
	 */
	public boolean removeObstacle(Obstacle ob) {
		return vec.removeElement(ob);
	}

	/**
	 * Draw a graphical representation of the obstacles in the collection.
	 * (Actually draws a wedge shape the right size.  a smaller circle is inserted
	 * at a later stage, to give the desired effect.
	 * Drawing the selection cursor around an obstacle is done in ObstaclePanel
	 * 
	 * @param Graphics object to draw on.
	 * @see ObstaclePanel
	 */
	public void drawObstacles(Graphics g)  {
		for (int i=0; i<vec.size(); i++)  {
			Obstacle o = (Obstacle)vec.elementAt(i);
			float gray = 0.6F - (1 - o.getResistance())*0.4F;
			g.setColor(new Color(gray, gray, gray));
			int from = (int)(360 * o.getFrom() / RoadParams.roadLength);
			int ang  = (int)(360 * (o.getTo()-o.getFrom()) / RoadParams.roadLength);
			g.fillArc(-220, -220, 440, 440, from, ang);
		}
	}		
}