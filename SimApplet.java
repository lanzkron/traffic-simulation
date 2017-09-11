import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.applet.*;

/**
 * The main class of the simulation.  Implements the Applet interface, and
 * thus allows the simulation to be embedded as an applet in any browser.
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see Applet
 */ 
public class SimApplet extends Applet
{	
	/** reference to active document */
	private SimDocument simDoc;
	/** the main simulation panel */
	private SimPanel simPanel;
	/** the controls panel */
	private ControlPanel controlPanel;
	
	public static Frame main = new Frame("Traffic Simulation");
	/**
	 * Override of the init() function in Applet.  Called when the applet is
	 * first loaded in the browser.
	 */
	public void init()  {
		// define the document object
		simDoc = new SimDocument();

		// load digit bitmaps, to be used in the ocntrol panel.
		Image digits[] = new Image[10];
		MediaTracker md = new MediaTracker(this);
		try {
			for (int i=0; i<10; i++)  {
				digits[i] = getImage(new URL(getCodeBase(), ""+i+"odb.gif"));
				md.addImage(digits[i], i);
			}
			// wait till all images finish loading
			md.waitForAll();
		} catch (Exception e) { }
		
		simPanel = new SimPanel(simDoc);
		controlPanel = new ControlPanel(simDoc, digits);

		main.setLayout(new BorderLayout());
		main.setBackground(Color.lightGray);
		main.setResizable(false);
		// set the visual components in the applet.
//		setLayout(new BorderLayout());
		main.add(controlPanel, BorderLayout.WEST);
		main.add(simPanel, BorderLayout.CENTER);

		main.pack();
		main.setVisible(true);
		main.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent we)  {
					((Frame)we.getSource()).dispose();
				}
			});
		// start the new document.
		simDoc.newDocument(20, new SinSpeedSetter(4, 20), 0);		
	}						
}
