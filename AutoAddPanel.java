import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class AutoAddPanel extends BasePanel implements Observer {
	Choice opts = new Choice();
	Parameter freq = new Parameter("Frequency", 10);
	Button set = new Button("Activate");
	int dTime;
	long time = 0;
	
	public AutoAddPanel(SimDocument d)  {
		super(d);
		doc.addObserver(this);
		
		dTime = (int)(freq.getValue()/RoadParams.dT);
		opts.add("No automatic operation");
		opts.add("Automatic add Cars");
		opts.add("Automatic remove Cars");
		
		set.addActionListener(
			new ActionListener()  {							  
				public void actionPerformed(ActionEvent ae)  {
					dTime = (int)(freq.getValue() / RoadParams.dT);
				}
			});
		add(new Label("Options"));
		add(opts);
		add(freq);
		add(new Label());
		add(set);		
	}	
	
	public void update(Observable o, Object arg)  {
		
		if (time == doc.stepCounter)
			return;
		time = doc.stepCounter;
		if ((doc.stepCounter % dTime) == 0 )  {
			if (opts.getSelectedIndex() == 1)  {
				Car c = new Car(0, 0, doc.pr);
				doc.road.addVehicle(c);
				c.setVel(c.getNext().getVel());
//				doc.road.speedSetter.setSpeed(c);
			}
			else if (opts.getSelectedIndex() == 2)  {
				doc.road.removeFirstVehicle();
			}
			doc.updateAll();
		}
	}				   
}
