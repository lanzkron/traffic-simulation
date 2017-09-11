import java.awt.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;

/**
 * GUI panel for setting simulation parameters.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see BasePanel, MouseListener, SimPanel, ControlPanel
 */
public class ParamsPanel extends BasePanel   {

	/** button for setting parameter */
	private Button set = new Button("Set Parameters");
	private Parameter sensitivity, minDist, safetyTime, obedience;
	private Parameter maxSpeed;
	
	
	/**
	 * Construction:  Set up the components within the panel, and set their
	 * initial values.
	 * 
	 * @params document to be associated with this panel
	 */
	public ParamsPanel(SimDocument d)  {
		super(d);
		
		// initialize parameter fields
		sensitivity = new Parameter("Sensitivity", doc.pr.getSensitivity());
		minDist = new Parameter("Minimal Distance", doc.pr.getMinDistance()-10);
		safetyTime = new Parameter("Safety Time", doc.pr.getSaftyTime());
		obedience = new Parameter("Obedience", doc.pr.getObedience());
		maxSpeed = new Parameter("Speed Limit", doc.rp.getTopSpeedLimit());
		
		// add all elemets to the panel
		setLayout(new GridLayout(8, 1, 8, 8));
		add(sensitivity);
		add(minDist);
		add(safetyTime);
		add(obedience);
		add(maxSpeed);
		add(set);
		
		// set buttons action
		set.addActionListener(
			new ActionListener()  {
				public void actionPerformed(ActionEvent ae)  {
					// set parameters in document.
					doc.pr.setSensitivity(sensitivity.getValue());
					doc.pr.setMinDistance(minDist.getValue()+10);
					doc.pr.setSaftyTime(safetyTime.getValue());
					doc.pr.setObedience(obedience.getValue());
					doc.rp.limit = maxSpeed.getValue();
				}
			});
		
	}		
}
