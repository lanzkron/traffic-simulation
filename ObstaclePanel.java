import java.awt.*;
import java.awt.event.*;

/**
 * GUI panel for selecting, adding and removing obstacles to the road.
 * The panel allows you to add new obstacles at any chosen resistance, to
 * change the resistance of existing obstacles, and to remove them.
 *  
 * The panel also serves as a MouseListener for the SimPanel whent is the 
 * active panel in the ControlPanel
 * 
 * @version 1.2
 * @author Misha Sklarz
 * @author Motti Lanzkron
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class ObstaclePanel extends BasePanel  {

	/** Button for setting selected obstacles resistance */
	private Button setObs = new Button("Set Obstacle Settings");
	/** Options selector */
	private Choice opts = new Choice();
	/** Input field for new resistance */
	private Parameter res;
	/** Currently selected obstacle */
	private Obstacle selected;
	/** Starting point of drag operation.  -1 if not currently dragging. */
	private double dragPos = -1;
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public ObstaclePanel(SimDocument d)  {
		super(d);
		
		// initialize options
		opts.addItem("Select Obstacle");
		opts.addItem("Add Obstacle");
		opts.addItem("Remove Obstacle");

		res = new Parameter("Resistance", 0.5F);
		setObs.setEnabled(false);
		
		// add Components to panel
		add(new Label("Options:"));
		add(opts);
		add(res);
		add(setObs);
		
		// set action for setObs button
		setObs.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent ae)  {
					// set the selected obstacle resistance parameter
					// to the one given in the res Paramater
					selected.setResistance(res.getValue());
				}
			});
	}

	/**
	 * Causes an obstacle to be selected and all the GUI to be 
	 * updated appropriately
	 * 
	 * @param the obstacle to be selected (may be null !!!)
	 */
	private void select(Obstacle o)  {
		selected = o;
		if (selected != null)  {
			// select - set buttons to enabled
			setObs.setEnabled(true);
			res.setValue(selected.getResistance());
		}
		else {
			// unselect - disable buttons.
			setObs.setEnabled(false);
			res.setValue(0);
		}
	}

	/**
	 * Override of mousePressed() function in BasePanel.
	 * Called when the mouse is predded in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mousePressed(MouseEvent me)  {
		// Check if click ocurred on the road.
		if (!isInRange(me, 190, 220))  {
			dragPos = -1;
			me.consume();
			return;
		}
		// if so, and if the INSERT option is active, 
		// start a drag operation.
		if (INSERT == opts.getSelectedIndex())  {
			dragPos = toAngle(me.getPoint()) * 500 / Math.PI ;		
		}
	}
	
	/**
	 * Override of mouseReleased() function in BasePanel.
	 * Called when the mouse is released in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mouseReleased(MouseEvent me)  {
		// Check if click ocurred on the road.
		if (!isInRange(me, 190, 220))  {
			dragPos = -1;
			me.consume();
			return;
		}
		// if so, and if a drag operation is being performed, create a new
		// obstacle, and add it to the road.
		if (dragPos > 0)  {
			double pos = toAngle(me.getPoint()) * 500 / Math.PI ;
			Obstacle ob = new Obstacle((int)dragPos, (int)pos, res.getValue());
			dragPos = -1;
			try {
				doc.rp.obstacles.addObstacle(ob);
				select(ob);
				doc.updateAll();
			}
			catch (OverLappingObstacleException oloe)  {
				// can't add obstacle - due to overlap with some other obstacle.
				// ignore, and continue.
			}
		}
	}

	/**
	 * Override of mouseClicked() function in BasePanel.
	 * Called when the mouse is clicked in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mouseClicked(MouseEvent me)  {
		// Check if click ocurred on the road.
		if (!isInRange(me, 190, 220))  {
			me.consume();
			return;
		}
		// If so, get obstacle clicked at.
		double pos = toAngle(me.getPoint()) * 500 / Math.PI ;
		Obstacle ob = doc.rp.obstacles.getObstacleAt((float)pos);
		switch (opts.getSelectedIndex())  {
		// If options is set to DELETE, remove obstacle from road.
		case DELETE:
			doc.rp.obstacles.removeObstacle(ob);
			if (ob == selected)
				select(null);
			break;
		// If options is set to SELECT, remove obstacle from road.			
		case SELECT:
			select(ob);
			break;
		}
		// Register changes with all views.
		doc.updateAll();
	}
	
	/**
	 * Overrides the specialDraw() in BasePanel.  Draws the outline around
	 * the currently selected obstacle.
	 * 
	 * @param Graphics object to draw on
	 */
	public void specialDraw(Graphics g) {
		// save original color
		Color c = g.getColor();
		g.setColor(Color.red);
		double sin, cos;
		if (selected != null)  {
			// get the angle where the obstacle begins, and how largw it is.
			int from = (int)(360 * selected.getFrom() / RoadParams.roadLength);
			int ang  = (int)(360 * (selected.getTo()-selected.getFrom()) / RoadParams.roadLength);
			
			// draw the inner arc of the selection cursor
			g.drawArc(-220, -220, 440, 440, from, ang);
			g.drawArc(-219, -219, 438, 438, from, ang);
			
			// draw the outer arc of the selection cursor
			g.drawArc(-191, -191, 382, 382, from, ang);
			g.drawArc(-190, -190, 380, 380, from, ang);
			
			// draw first vertical line of the cursor
			sin = -Math.sin(((double)from)*Math.PI/180.0);
			cos = Math.cos(((double)from)*Math.PI/180.0);
			g.drawLine((int)(cos*190), (int)(sin*190), (int)(cos*220), (int)(sin*220));
			sin = -Math.sin((((double)from)+0.25)*Math.PI/180.0);
			cos = Math.cos((((double)from)+0.25)*Math.PI/180.0);
			g.drawLine((int)(cos*190), (int)(sin*190), (int)(cos*220), (int)(sin*220));

			// draw second vertical line of the cursor
			sin = -Math.sin(((double)from+ang)*Math.PI/180.0);
			cos = Math.cos(((double)from+ang)*Math.PI/180.0);
			g.drawLine((int)(cos*190), (int)(sin*190), (int)(cos*220), (int)(sin*220));
			sin = -Math.sin((((double)from+ang)-0.25)*Math.PI/180.0);
			cos = Math.cos((((double)from+ang)-0.25)*Math.PI/180.0);
			g.drawLine((int)(cos*190), (int)(sin*190), (int)(cos*220), (int)(sin*220));
			
		}
		// revert to original color
		g.setColor(c);
	}
}
