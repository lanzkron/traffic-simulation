import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * GUI panel for selecting, adding and removing Cars on the road.
 * The panel allows you to add new cars at any chosen point on the road, 
 * and to remove cars from the road.  Also, vital statistics for the
 * currently selected car are also shown.
 *  
 * The panel also serves as a MouseListener for the SimPanel whent is the 
 * active panel in the ControlPanel
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class CarPanel extends BasePanel implements Observer {
	/** Option selector */
	private Choice opts = new Choice();
	/** Position indicator	 */
	private Parameter pos = new Parameter("Position", 0);
	/** Velocity indicator	 */
	private Parameter vel = new Parameter("Velocity", 0);
	/** Acceleration indicator	 */
	private Parameter acc = new Parameter("Acceleration", 0);
	/** Button for charting selected car's data	 */
	private Button chartButton  = new Button("Chart this Data...");
	/** currently selected car	 */
	private Car selected;

	private DataSet velData = new DataSet("Speed", "",  Color.red, 1000, 0, 30, false);
	private DataSet accData = new DataSet("Acceleration", "", Color.orange, 1000, -15, 15, false);
	private DataSet timeData = new DataSet("Time", "", Color.green, 1000, 0, 100, false);
	private DataSet dXData = new DataSet("delta X", "", Color.cyan, 1000, 0, 500, false);
	private DataSet dVData = new DataSet("delta V", "", Color.magenta, 1000, -20, 20, false);

	private ChartFrame chart = new ChartFrame(SimApplet.main, "Selected Car Data Chart", timeData, velData);
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public CarPanel(SimDocument d)  {
		super(d);
		doc.addObserver(this);
		
		// initialize options
		opts.addItem("Select Cars");
		opts.addItem("Add Cars");
		opts.addItem("Remove Cars");
		
		// set all params to be read only
		pos.setEnabled(false);
		vel.setEnabled(false);
		acc.setEnabled(false);
		

		chartButton.addActionListener(  
			new ActionListener()  {								
				public void actionPerformed(ActionEvent ae)  {
					chart.setVisible(true);
				}
			});

		// add all components to the panel
		add(new Label("Options:"));
		add(opts);
		add(new Label("Selected car data:"));
		add(pos);
		add(vel);
		add(acc);
		add(chartButton);
		
		// set selection to initially be null
		select(null);
		chart.addDataSet(accData);
		chart.addDataSet(dXData);
		chart.addDataSet(dVData);
		chart.setVisible(false);		
	}
	
	/**
	 * Reset all Data Set's.  Called when a new simulation is about to begin.
	 */
	public void clearData() {
		accData.clearData();
		velData.clearData();
		timeData.clearData();
	}
	
	/**
	 * Causes a car to be selected and all the GUI to be 
	 * updated appropriately
	 * 
	 * @param the car to be selected (may be null !!!)
	 */	
	private void select(Car c)  {
		selected = c;
		if (selected != null)  {
			// select - set buttons to enabled
			chartButton.setEnabled(true);
		}
		else {
			// unselect - disable buttons.
			chartButton.setEnabled(false);
		}
		// Reset selected car's data...
		clearData();
	}
	
	/**
	 * Implementation of update(), as per contract with the Observer implemetation
	 * This function gets called when the document changes, and updates the 
	 * currently selected car's data.
	 */
	public void update(Observable o, Object arg)  {
		if (selected != null)  {
			// set selected car's data.
			pos.setValue(selected.getRealPos());
			vel.setValue(selected.getRealVel());
			acc.setValue(selected.getAcc());
			if ((doc.stepCounter % 5) == 0)  {
				accData.addPoint(selected.getAcc());
				velData.addPoint(selected.getVel());
				timeData.addPoint(doc.stepCounter, (int)doc.stepCounter-200, (int)doc.stepCounter);
				dXData.addPoint(selected.getDeltaX());
				dVData.addPoint(selected.getDeltaV());
				if (chart.isVisible())  {
					chart.drawChart();
				}
			}		
		}
		else  {
			// no selected car - use default settings.
			pos.setValue(0);
			vel.setValue(0);
			acc.setValue(0);
		}
	}	

	/**
	 * Override of mouseClicked() function in BasePanel.
	 * Called when the mouse is clicked in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mouseClicked(MouseEvent me)  {
		Class cClass = new Car(0, 0, null).getClass();
		Car c;
		// Check if click ocurred on the road.
		if (!isInRange(me, 190, 220))  {
			me.consume();
			return;
		}
		// calculate position of mouse click.
		double pos = toAngle(me.getPoint()) * 500 / Math.PI ;
		int state = opts.getSelectedIndex();
		switch (state)  {
		// If options is set to SELECT, attempt to select car at poition
		case SELECT:
			try {
				c = (Car)doc.road.getVehicleAt((float)pos, cClass);
				select(c);
			}
			catch (ClassCastException cce)  {
			}
			break;

		// If options is set to INSERT, add new car to road
		case INSERT:
			c = new Car((float)pos, 0, doc.pr);
			doc.addVehicle(c);
			doc.road.speedSetter.setSpeed(c);
			select(c);
			break;

		// If options is set to DELETE, remove car from road
		case DELETE:
			try {
				// call remVehicleAt(), with the class type of Cars.
				c = (Car)doc.road.remVehicleAt((float)pos, cClass);
				if (selected == c)
					select(null);
			}
			catch (ClassCastException cce)  {
			}
			break;
		}
		// Register changes with all views.
		doc.updateAll();
	}	


	/**
	 * Overrides the specialDraw() in BasePanel.  Draws the circle around
	 * the currently selected car.
	 * 
	 * @param Graphics object to draw on
	 */
	public void specialDraw(Graphics g) {
		float cos, sin, rad;
		int x, y;
		// save original color
		Color c = g.getColor();
		g.setColor(Color.blue);
		if (selected != null) {
			// calculate coordinates of the selected car
			rad = (float)(Math.PI * 2.0 * (selected.getRealPos())/RoadParams.roadLength);
			cos = (float)Math.cos(rad);
			sin = (float)Math.sin(rad);
			x = (int)(205 *  cos);
			y = -(int)(205 * sin);
			// draw a circle around selected car
			g.drawOval(x-14, y-14, 28, 28);
			g.drawOval(x-15, y-15, 30, 30);
		}
		// revert to original color
		g.setColor(c);
	}	
}

