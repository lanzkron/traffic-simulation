import java.awt.*;
import java.util.Observable;

/**
 * 
 * 
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 */
public class SimDocument extends Observable  {

	public BasePanel panels[] = new BasePanel[8];
	public  BasePanel currPanel;
	
	long stepCounter = 0;
	int simSpeed = 200;
	public Params pr = new Params();

	boolean running = false;
	RoadParams rp = new RoadParams();
	Road road = new Road();
	
	SimRunner simThread;

	public void setCurrPanel(int i)  {
		if (i<0 || i>=8) return;
		currPanel = panels[i];
		setChanged();
		notifyObservers("Change Listener !!");
	}
	
	/**
	 * Update all registered views.
	 */
	public void updateAll()  {
		setChanged();
		notifyObservers();
	}

	/**
	 * Update all registered views.  Use obj as a argument.
	 * @param to be used as an argument to the notification.
	 */
	public void updateAll(Object obj)  {
		setChanged();
		notifyObservers(obj);
	}

	public void addVehicle(Vehicle v)  {
		road.addVehicle(v);
		updateAll();
	}
	
	public void removeVehicle(Vehicle v)  {
		try {
			v.remove();
		}
		catch (NullPointerException npe) {}
		setChanged();
		notifyObservers();
	}
	
//	public void addObstacle(int from, int to, float res)  {
//		rp.addObstacle(new Obstacle(from, to, res));
//		setChanged();
//		notifyObservers();
//	}
	
	/**
	 * Construct a new SimDocument.
	 */		
	public SimDocument()  {
		// create a thread and initialize it to suspended.
		simThread = new SimRunner();
		simThread.start();
		simThread.suspend();
		// create all GUI panels.
		panels[0] = new NewPanel(this);
		panels[1] = new ParamsPanel(this);
		panels[2] = new CarPanel(this);
		panels[3] = new ObstaclePanel(this);
		panels[4] = new TrafficLightPanel(this);
		panels[5] = new RadarPanel(this);
		panels[6] = new GlobalDataPanel(this);
		panels[7] = new AutoAddPanel(this);
		// set curr GUI panel
		currPanel = panels[0];
	}
	
	class SimRunner extends Thread  {
		public void run()  {
			long sleep, before, now = System.currentTimeMillis();
			while (true)  {
				doStep();
				try {
					before = now;
					now = System.currentTimeMillis();
					sleep = simSpeed - (now - before);
					if( 0 < sleep )
						Thread.sleep(sleep);
				}
				catch(Exception e) {}
			}
		}
	}
	
	public void startSimulation()  {
		running = true;
		simThread.resume();
	}
	
	public void pauseSimulation()  {
		running = false;
		simThread.suspend();
	}
	
//	public void run()   {
//	}
	
	public void doStep()  {
		stepCounter ++;
		road.doStep();
		int num = road.getNumCars();
		double avg = road.getAvgVel();
		updateAll();
	}

	/**
	 * Reset document data, and restart a new simulation.
	 * @param number of cars to start with
	 * @param which initial speed setter to use
	 * @param how to initially distribute the cars (either the distance between
	 * them, or, if negative, equally distributed.
	 */
	public void newDocument(int cars, SpeedSetter setter, int distribution)  {
		road.clear();
		road.speedSetter = new AvgSpeedSetter();		
		float place, dist = 1;

		if (distribution <= 0)		// equadistance
			dist = (cars <= 0) ? 0 : 1000 / cars;
		else						// constant
			dist = distribution;
			
		// add cars to the road, at the requested speed and positions.
		for (int i=0; i<cars; i++)  {
			place = i * dist;
			while (place > 1000)
				place -= 1000;
			Vehicle c = new Car(place, 0, pr);
			setter.setSpeed(c);			
			road.addVehicle(c);
		}

		for (int i=0; i<panels.length; i++) {
			panels[i].clearData();
		}
	}	
}
