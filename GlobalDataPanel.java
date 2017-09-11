import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GlobalDataPanel extends BasePanel implements Observer {

	private Button chartButton = new Button("View Chart");
	private Parameter avgVel = new Parameter("Average Speed", 0);
	private Parameter density = new Parameter("Density", 0);
	private Parameter flux = new Parameter("Flux", 0);
	
	private DataSet fluxData = new DataSet("Flux", " * 10e2", Color.blue, 1000, 0, 50, false);
	private DataSet avgVelData = new DataSet("Avg Speed", "",  Color.red, 1000, 0, 30, false);
	private DataSet densityData = new DataSet("Density", " * 10e3", Color.orange, 1000, 0, 50, false);
	private DataSet timeData = new DataSet("Time", "", Color.green, 1000, 0, 100, false);

	private ChartFrame chart = new ChartFrame(SimApplet.main, "Global Data Chart", densityData, fluxData);
	
	public GlobalDataPanel(SimDocument d)  {
		super(d);
		doc.addObserver(this);
		
		avgVel.setEnabled(false);
		density.setEnabled(false);
		flux.setEnabled(false);
		chartButton.addActionListener(  
			new ActionListener()  {								
				public void actionPerformed(ActionEvent ae)  {
					chart.setVisible(true);
				}
			});
		add(avgVel);
		add(density);
		add(flux);
		add(new Label(""));
			
		add(chartButton);
		chart.addDataSet(avgVelData);
		chart.addDataSet(timeData);
		chart.setVisible(false);
	}

	public void clearData() {
		fluxData.clearData();
		avgVelData.clearData();
		timeData.clearData();
		densityData.clearData();
	}
	
	public void update(Observable ob, Object arg)  {
		float avg = (float)doc.road.getAvgVel();
		int num = doc.road.getNumCars();
		avgVel.setValue(avg);
		density.setValue(1000*num/RoadParams.roadLength); // e3
		flux.setValue(num*avg/RoadParams.roadLength);
		if ((doc.stepCounter % 10) == 0)  {
			avgVelData.addPoint(avg);
			densityData.addPoint(1000*num/RoadParams.roadLength); // e3
			fluxData.addPoint(100*num*avg/RoadParams.roadLength); // e2
			timeData.addPoint(doc.stepCounter, (int)doc.stepCounter-200, (int)doc.stepCounter);
			if (chart.isVisible())  {
				chart.drawChart();
			}
		}
	}
}
