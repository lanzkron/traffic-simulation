import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * GUI panel for selecting, adding and removing Radars on the road.
 * The panel allows you to add new radars at any chosen point on the road, 
 * and to remove radars from the road.  Also available are the data measured
 * by the radar.
 *  
 * The panel also serves as a MouseListener for the SimPanel whent is the 
 * active panel in the ControlPanel
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class RadarPanel extends BasePanel implements Observer {
	/** Option selector */
	private Choice opts = new Choice();
	/** Currently selected radar */
	private Radar selected = null;
	/** Button for requesting chart to radar's measured data */
	private Button chartButton = new Button("Chart this data...");
	
	private DataSet fluxData = new DataSet("Flux", " * 10e2", Color.blue, 1000, 0, 50, false);
	private DataSet avgVelData = new DataSet("Avg Speed", "",  Color.red, 1000, 0, 30, false);
	private DataSet densityData = new DataSet("Density", " * 10e3", Color.orange, 1000, 0, 50, false);
	private DataSet timeData = new DataSet("Time", "", Color.green, 1000, 0, 100, false);
	private ChartFrame chart = new ChartFrame(SimApplet.main, "Point Data Chart", densityData, fluxData);
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public RadarPanel(SimDocument d)  {
		super(d);
		doc.addObserver(this);
		
		// initialize options
		opts.addItem("Select Radar");
		opts.addItem("Add Radar");
		opts.addItem("Remove Radar");

		chartButton.setEnabled(false);
		chartButton.addActionListener(  
			new ActionListener()  {								
				public void actionPerformed(ActionEvent ae)  {
					chart.setVisible(true);
				}
			});
		
		
		// add components to the panel
		add(new Label("Options:"));
		add(opts);
		add(new Label());
		add(chartButton);
		
		chart.addDataSet(timeData);
		chart.addDataSet(avgVelData);
	}
	
	/**
	 * Reset all Data Set's.  Called when a new simulation is about to begin.
	 */
	public void clearData() {
		avgVelData.clearData();
		timeData.clearData();
		densityData.clearData();
		fluxData.clearData();
	}
	
	
	/**
	 * Causes a radar to be selected and all the GUI to be 
	 * updated appropriately
	 * 
	 * @param the car to be selected (may be null !!!)
	 */		
	private void select(Radar r)  {
		selected = r;
		if (selected != null)  {
			// select - set buttons to enabled
			chartButton.setEnabled(true);
		}
		else {
			// unselect - disable buttons.
			chartButton.setEnabled(false);
		}
		clearData();
	}

	/**
	 * Override of mouseClicked() function in BasePanel.
	 * Called when the mouse is clicked in the SimPanel.
	 * 
	 * @param MouseEvent object representing this event
	 */
	public void mouseClicked(MouseEvent me)  {
		Class rClass = new Radar(0).getClass();
		Radar r;
		// Check if click ocurred on the road.
		if (!isInRange(me, 220, 240))  {
			me.consume();
			return;
		}
		// calculate position of mouse click.
		double pos = toAngle(me.getPoint()) * 500 / Math.PI ;
		switch (opts.getSelectedIndex())  {
		// If options is set to SELECT, attempt to select radar at poition
		case SELECT: 
			try {
				r = (Radar)doc.road.getVehicleAt((float)pos, rClass);
				select(r);
			}			
			catch (ClassCastException cce)  {
			}
			break;
			
		// If options is set to INSERT, add new radar to road
		case INSERT: 
			r = new Radar((float)pos);
			doc.addVehicle(r);
			select(r);
			break;
			
		// If options is set to DELETE, remove radar from road
		case DELETE:
			try {
				r = (Radar)doc.road.getVehicleAt((float)pos, rClass);
				doc.removeVehicle(r);
				if (selected == r)  {
					select(null);
				}
			}
			catch (ClassCastException cce) {
			}
			break;
		}
		// Register changes with all views.
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
		// save original color
		Color c = g.getColor();
		g.setColor(Color.orange);
		if (selected != null) {
			// calculate coordinates of the selected car
			rad = (float)(Math.PI * 2.0 * selected.getRealPos()/RoadParams.roadLength);
			cos = (float)Math.cos(rad);
			sin = (float)Math.sin(rad);
			x = (int)(232 *  cos);
			y = -(int)(232 * sin);
			// draw a circle around selected radar
			g.drawOval(x-14, y-14, 28, 28);
			g.drawOval(x-15, y-15, 30, 30);
		}
		// revert to original color
		g.setColor(c);
	}
	
	public void update(Observable o, Object arg)  {
		if (selected != null && (doc.stepCounter % 20) == 0)  {
			// set selected radar's data.
			fluxData.addPoint(100 * selected.getNumCars() / (20*RoadParams.dT)); // e2
			avgVelData.addPoint(selected.getAvgVel());
			densityData.addPoint( 1000 * selected.getNumCars() / (20.0 * RoadParams.dT * selected.getAvgVel())); // e3
			timeData.addPoint(doc.stepCounter, (int)doc.stepCounter-200, (int)doc.stepCounter);
			selected.reset();
			if (chart.isVisible())  {
				chart.drawChart();
			}
		}
	}	
}