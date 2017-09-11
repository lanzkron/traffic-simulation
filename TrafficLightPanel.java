import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * GUI panel for selecting, adding and removing Traffic Lights on the road.
 * The panel allows you to add new lights at any chosen point on the road, 
 * and to remove lights from the road.  
 *  
 * The panel also serves as a MouseListener for the SimPanel whent is the 
 * active panel in the ControlPanel
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
class TrafficLightPanel extends BasePanel  {
	/** Option selector	 */
	private Choice opts = new Choice();
	/** Length of time to be red */
	private Parameter red = new Parameter("Red", 10);
	/** Length of time to be orange */
	private Parameter orange = new Parameter("Orange", 2);
	/** Length of time to be green */
	private Parameter green = new Parameter("Green", 10);
	/** Button for setting selected traffic light's parameters. */
	private Button setLight = new Button("Set selected light params");
	/** Currently selected traffic light */
	private TrafficLight selected = null;
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public TrafficLightPanel(SimDocument d)  {
		super(d);

		// initialize options
		opts.addItem("Select Traffic Lights");
		opts.addItem("Add Traffic Lights");
		opts.addItem("Remove Traffic Lights");

		// set action for setLight button
		setLight.addActionListener(
			new ActionListener()  {
				public void actionPerformed(ActionEvent ae)  {
					try {
						// set selected light to settings specified in the 
						// red, green and orange parameter fields
						selected.red = (int)(red.getValue()/RoadParams.dT);
						selected.green = (int)(green.getValue()/RoadParams.dT + selected.red);
						selected.orange = (int)(orange.getValue()/RoadParams.dT + selected.green);
					}
					catch (ClassCastException cce)  {}
				}
			});
								
		// add components to the traffic light panel
		add(new Label("Options:"));
		add(opts);
		add(red);
		add(orange);
		add(green);
		add(setLight);

		// initialize selection to null
		select(null);
	}
	
	/**
	 * Causes a radar to be selected and all the GUI to be 
	 * updated appropriately
	 * 
	 * @param the car to be selected (may be null !!!)
	 */		
	public void select(TrafficLight tl)  {
		selected = tl;
		if (selected != null)  {
			// select - set buttons to enabled
			red.setValue(tl.red*RoadParams.dT);
			green.setValue((tl.green-tl.red)*RoadParams.dT);
			orange.setValue((tl.orange-tl.green)*RoadParams.dT);
			setLight.setEnabled(true);
		}
		else {
			// unselect - disable buttons.
			setLight.setEnabled(false);
		}
	}
	
	/**
	 * Override of mouseClicked() function in BasePanel.
	 * Called when the mouse is clicked in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mouseClicked(MouseEvent me)  {
		Class tlClass = new TrafficLight(0, 0, 0, 0).getClass();
		TrafficLight tl;
		// Check if click ocurred on the road.
		if (!isInRange(me, 220, 240))  {
			me.consume();
			return;
		}
		// calculate position of mouse click.
		double pos = toAngle(me.getPoint()) * 500 / Math.PI ;
		int state = opts.getSelectedIndex();
		switch (state)  {
		// If options is set to SELECT, attempt to select light at poition
		case SELECT: 
			try {
				tl = (TrafficLight)doc.road.getVehicleAt((float)pos, tlClass);
				select(tl);
			}
			catch (ClassCastException cce)  {
				select(null);
			}
			break;

		// If options is set to INSERT, add new light to road
		case INSERT: 
			int rr = (int)(red.getValue()/RoadParams.dT);
			int oo = (int)(orange.getValue()/RoadParams.dT);
			int gg = (int)(green.getValue()/RoadParams.dT);
			// initialize traffic light
			tl = new TrafficLight((float)pos, rr, gg, oo);
			doc.addVehicle(tl);
			select(tl);
			break;

		// If options is set to DELETE, remove traffic light from road
		case DELETE:
			try {
				tl = (TrafficLight)doc.road.remVehicleAt((float)pos, tlClass);
				if (selected == tl)  {
					select(null);
				}
			}
			catch (ClassCastException cce) {
			}
			break;
		}
		// Tell all views about changes.
		doc.updateAll();
	}

	/**
	 * Overrides the specialDraw() in BasePanel.  Draws the circle around
	 * the currently selected radar.
	 * 
	 * @param Graphics object to draw on
	 */
	public void specialDraw(Graphics g) {
		float cos, sin, rad;
		int x, y;
		// keep color
		Color c = g.getColor();
		g.setColor(Color.red);
		if (selected != null) {
			// calculate coordinates of the selected traffic light			
			rad = (float)(Math.PI * 2.0 * (selected.getRealPos()-4)/1000);
			cos = (float)Math.cos(rad);
			sin = (float)Math.sin(rad);
			x = (int)(232 *  cos);
			y = -(int)(232 * sin);
			// draw a circle around selected radar
			g.drawOval(x-14, y-14, 28, 28);
			g.drawOval(x-15, y-15, 30, 30);
		}
		// reset to old color
		g.setColor(c);
	}
	
}


