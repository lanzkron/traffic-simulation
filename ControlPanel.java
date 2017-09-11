import java.awt.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;
		
/**
 * Main GUI Panel.  Containter to all the other GUI components.
 * Composed of two main parts:
 * <ol>
 * <li>Upper:  a start/stop button, and a selector for the bottom part.</li>
 * <li>Lower:  all the rest of the GUI panels.</li>
 * </ol>
 * The panel implements the <Code>Observer</Code> interface, and thus can update
 * it's step counter each time the document does.
 * 
 * @version 1.2
 * @author Misha Sklarz
 * @author Motti Lanzkron
 * @see Observer, Observable, SimDocument
 */
public class ControlPanel extends Panel implements Observer  {
	/** reference to active document */
	private SimDocument doc;
	
	/** Top panel
	 * @see main class desciption. */
	private Panel top = new Panel();
	/** Bottom panel
	 * @see main class desciption. */
	private Panel bottom = new Panel();
	/** Simulation step counter */
	private CounterPanel stepCount;
	/** Button to start and stop the simulation */
	private Button start = new Button("Start/Stop");
	/** Selector for selecting the other GUI panels */
	private Choice selector = new Choice();
	
	/**
	 * Construction.  Set out the various components.
	 * 
	 * @param Reference to the active document.
	 * @param Array of digit bitmaps for the digital number display.
	 */
	public ControlPanel(SimDocument d, Image digs[])  {
		// link to doc, and register with the document as an observer.
		doc = d;
		d.addObserver(this);

		// divide the panel into two parts, bottom and top.
		setLayout(new GridLayout(2, 1, 8, 8));

		// set top panel:
		top.setLayout(new GridLayout(6, 1, 8, 8));

		// attach an ActionListener to the stop start button
		start.addActionListener(
			new ActionListener()  {
				public void actionPerformed(ActionEvent e) {
					// toggle the simulation on and off.
					if (doc.running) 
						doc.pauseSimulation();
					else
						doc.startSimulation();
				}
			});
		// add names of all GUI panels to the selector
		selector.add("New Simulation");
		selector.add("Parameters");
		selector.add("Cars");
		selector.add("Obstacles");
		selector.add("Traffic Lights");
		selector.add("Radars");
		selector.add("Global Data");
		selector.add("Auto Add/Remove Cars");
		// set the selectors behaiviour when being selected from.
		selector.addItemListener(
			new ItemListener()  {
				public void itemStateChanged(ItemEvent ie)  {
					// get string chosen, and show the appropriate GUI panel
					String choice = ie.getItem().toString();
					((CardLayout)bottom.getLayout()).show(bottom, choice);
					// also, register the change with the document.
					doc.setCurrPanel(selector.getSelectedIndex());
				}
			});
		
		stepCount = new CounterPanel(8, 0, digs);
		// add all components to the top panel
		top.add(stepCount);
		top.add(new Label());
		top.add(start);				
		top.add(new Label());
		top.add(new Label());
		top.add(selector);
		
		// set bottom panel
		bottom.setLayout(new CardLayout());
		// the bottom panel uses CardLayout in order to contain all
		// the GUI panels, and show only one of them at a time.
		bottom.add(doc.panels[0], "New Simulation");
		bottom.add(doc.panels[1], "Parameters");
		bottom.add(doc.panels[2], "Cars");
		bottom.add(doc.panels[3], "Obstacles");
		bottom.add(doc.panels[4], "Traffic Lights");
		bottom.add(doc.panels[5], "Radars");
		bottom.add(doc.panels[6], "Global Data");
		bottom.add(doc.panels[7], "Auto Add/Remove Cars");
		
		// finally, add both panels to the ControlPanel
		add(top);
		add(bottom);
	}
	
	/**
	 * Return the preferred size of the component.
	 * Overrides <code>Panel</code>'s getPreferredSize() function.
	 * This function is called automatically by the framework while laying
	 * the component inside a <code>Containter</code> object.
	 */	
	public Dimension getPreferredSize()  {
		return new Dimension(200, 500);
	}
	
	/**
	 * Overrides <code>Panel</code>'s getInsets() function.
	 * return the distance to be used as a border around the component.
	 */
	public Insets getInsets()  {
		return new Insets(8, 8, 8, 8);
	}
	
	/**
	 * Implementation of the update() function, which is part of the 
	 * Observer interface.  Called each time the document is changed.
	 */
	public void update(Observable o, Object arg)  {
		// set the counter to the new step count, and update.
		stepCount.setNumber((int)(doc.stepCounter*RoadParams.dT));
		stepCount.repaint();
	}
	
}
