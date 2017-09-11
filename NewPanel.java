import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * GUI panel for setting the parameters for a new simulation, and for
 * starting one.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class NewPanel extends BasePanel  {
	
	private Parameter numCars = new Parameter("# of Cars", 20);
	private Parameter maxSpeed = new Parameter("Max Speed", 20);
	private Parameter cycles = new Parameter("# of Cycles", 4);
	private Parameter dist = new Parameter("Distance", 20);
	private Choice velSetter = new Choice();									  
	private Choice posSetter = new Choice();
								  
	Button start = new Button("Start new Simulation");
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public NewPanel(SimDocument d)  {
		super(d);

		// add options to speed setter chooser.
		velSetter.addItem("Sin Setter");
		velSetter.addItem("Rand Setter");
		velSetter.addItem("Const Setter");

		velSetter.addItemListener(
			new ItemListener()  {
				public void itemStateChanged( ItemEvent e ) {
					// make cycles invisible if the curr choice isn't "sim setter"
					// for the others, it doesn't make sense.
					cycles.setVisible(velSetter.getSelectedIndex() == 0);
				}
			});
		
		// add options to position setter chooser
		posSetter.addItem("Equal Setter");
		posSetter.addItem("Const Setter");
		posSetter.addItemListener(
			new ItemListener()  {
				public void itemStateChanged( ItemEvent e ) {
					// make distance invisible if the curr choice isn't "Const"
					// for the others, it doesn't make sense.
					dist.setVisible(posSetter.getSelectedIndex() == 1);
				}
			});
		// start off invisible ("Equal Setter" is chosen by default...)
		dist.setVisible(false);
		
		// this is where the action is...  Starting a new Simulation
		start.addActionListener(
			new ActionListener()  {
				public void actionPerformed(ActionEvent ae)  {
					// pause the sim if it's running.
					if (doc.running)
						doc.pauseSimulation();
					int num = (int)numCars.getValue();
					SpeedSetter s;
					// choose the speed setter according to the currently 
					// selected choice.  use the appropriate parameters.
					switch (velSetter.getSelectedIndex())  {
					case 0: 
						s = new SinSpeedSetter((int)cycles.getValue(), maxSpeed.getValue());
						break;
					case 1:
						s = new RandSpeedSetter(maxSpeed.getValue());
						break;
					case 2:
						s = new ConstSpeedSetter(maxSpeed.getValue());
						break;
					default:
						return;
					}				
					// calculate the distribution
					int posSet = posSetter.getSelectedIndex() * (int)dist.getValue();
					// start the new simulation, and update all dependant views.
					doc.newDocument(num, s, posSet);
					doc.updateAll();
				}
			});

		// add all the components to the panel
		add(new Label("New sim options:"));
		add(numCars);
		Panel pan = new Panel();
		pan.setLayout(new GridLayout(1, 2));
		pan.add(new Label("Speed:"));
		pan.add(velSetter);
		add(pan);
		add(maxSpeed);
		add(cycles);
		pan = new Panel();
		pan.setLayout(new GridLayout(1, 2));
		pan.add(new Label("Position:"));
		pan.add(posSetter);
		add(pan);
		add(dist);
		
		add(start);
	}
}