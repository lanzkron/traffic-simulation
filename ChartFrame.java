import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChartFrame extends Dialog  {
//	private int xMin = 0, xMax = 100;
//	private int yMin = 0, yMax = 100;
	private int xTick = 10, yTick = 10;
//	private int rMin = 0, rMax = 100;
			
	private Rectangle rect;
	private double xFact, yFact, rFact;

	public Hashtable data = new Hashtable(10);
	public DataSet xData;
	public DataSet yData;
	public DataSet rData;
		
	PlotArea plot; //= new PlotArea();
	XAxisArea xAxis; //= new XAxisArea();
	YAxisArea yAxis; //= new YAxisArea();
	RAxisArea rAxis; //= new RAxisArea();
	TitleArea title; //= new TitleArea("Chart Title");
	
	class TitleArea extends Panel  {
		String title;
		public TitleArea(String s) {
			title = s;
		}
		public Dimension getPreferredSize()  {
			return new Dimension(1, 40);
		}		

		public void paint(Graphics g)  {
			g.clearRect(0, 0, rect.width + 60, 30);
			
			Font r = new Font("Arial", Font.BOLD, 14);
			g.setFont(r);
			FontMetrics fm = g.getFontMetrics();			
			g.drawString(title, (rect.width+60-fm.stringWidth(title))/2, 20);

			Font p = new Font("Arial", Font.PLAIN, 10);
			g.setFont(p);
			fm = g.getFontMetrics();		
			g.setColor(yData.color);
			g.drawString(yData.name, 5, 28);
			g.setColor(rData.color);
			g.drawString(rData.name, rect.width + 55 - fm.stringWidth(rData.name), 28);
		}
	}
		
	class PlotArea extends Canvas  {

		public void paint(Graphics g)  {
			drawPlot(g);
		}
		
		protected void drawPlot(Graphics g) {
			g.setPaintMode();
		    g.clearRect(0, 0, rect.width, rect.height);
						
			int yTemp, xTemp, i;
			
			g.setColor(Color.lightGray);
			int yT = (yData.max - yData.min) / yTick;
			int xT = (xData.max - xData.min) / xTick;
			for (i=yData.min+yT; i<yData.max; i+=yT)  {
				yTemp = (int)(rect.height - yFact * (i - yData.min));
				g.drawLine(0, yTemp, rect.width, yTemp);
			} 
			for (i=xData.min+xT; i<xData.max; i+=xT)  {
				xTemp = (int)(xFact * (i - xData.min));
				g.drawLine(xTemp, 0, xTemp, rect.height);
			} 
			g.setColor(Color.black);
			g.drawRect(0, 0, rect.width-1, rect.height-1);
			g.drawRect(1, 1, rect.width-3, rect.height-3); 			

			g.setColor(yData.color);			
			int x1 = (int)(xFact * (xData.data[0] - xData.min));
			int y1 = rect.height - (int)(yFact * (yData.data[0] - yData.min));
			g.fillRect(x1-2, y1-2, 5, 5);
			int x2, y2;
			for (i=1; i<xData.size; i++) {
				x2 = (int)(xFact * (xData.data[i] - xData.min));
				y2 = rect.height - (int)(yFact * (yData.data[i] - yData.min));
				g.fillRect(x2-2, y2-2, 5, 5);
				if (yData.connect || xData.connect)  {
					g.drawLine(x1, y1, x2, y2);
					g.drawLine(x1, y1-1, x2, y2-1);
					g.drawLine(x1+1, y1, x2+1, y2);
				}
				x1 = x2;
				y1 = y2;
			}
			g.setColor(rData.color);			
			x1 = (int)(xFact * (xData.data[0] - xData.min));
			y1 = rect.height - (int)(rFact * (rData.data[0] - rData.min));
			g.fillRect(x1-2, y1-2, 5, 5);
			for (i=1; i<xData.size; i++) {
				x2 = (int)(xFact * (xData.data[i] - xData.min));
				y2 = rect.height - (int)(rFact * (rData.data[i] - rData.min));
				g.fillRect(x2-2, y2-2, 5, 5);
				if (rData.connect || xData.connect)  {
					g.drawLine(x1, y1, x2, y2);
					g.drawLine(x1, y1-1, x2, y2-1);
					g.drawLine(x1+1, y1, x2+1, y2);
				}
				x1 = x2;
				y1 = y2;
			}
		}
	}

	class YAxisArea extends Canvas  {
		public PopupMenu menu = new PopupMenu();
		
		public YAxisArea()   {
			Font r = new Font("Arial", Font.PLAIN, 10);
			setFont(r);
			add(menu);
			menu.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent ae)  {
						DataSet d = (DataSet)data.get(ae.getActionCommand());
						yData = d;
						drawChart();
					}
				});
			addMouseListener(
				new MouseAdapter()  {
					public void mouseClicked(MouseEvent e)  {
						menu.show((Component)e.getSource(), e.getX(), e.getY());
					}
				});
		}
				
		public void paint(Graphics g)  {
			FontMetrics fm = g.getFontMetrics();
		    g.clearRect(0, 0, 30, rect.height);
			int yTemp;
			int yT = (yData.max - yData.min) / yTick;
			for (int i=yData.min+yT; i<yData.max; i+=yT)  {
				yTemp = (int)(rect.height - yFact * (i - yData.min)) + 5;
				g.drawString(""+i, 30 - (fm.stringWidth(""+i)+5), yTemp);
			} 
			g.drawString(""+yData.min, 30 - (fm.stringWidth(""+yData.min)+5), rect.height);
			g.drawString(""+yData.max, 30 - (fm.stringWidth(""+yData.max)+5), 8);
		}

		public Dimension getPreferredSize()  {
			return new Dimension(30, 1);
		}
	}

	class RAxisArea extends Canvas  {
		public PopupMenu menu = new PopupMenu();
		
		public RAxisArea()   {
			Font r = new Font("Arial", Font.PLAIN, 10);
			setFont(r);
			add(menu);
			menu.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent ae)  {
						DataSet d = (DataSet)data.get(ae.getActionCommand());
						rData = d;
						drawChart();
					}
				});
			addMouseListener(
				new MouseAdapter()  {
					public void mouseClicked(MouseEvent e)  {
						menu.show((Component)e.getSource(), e.getX(), e.getY());
					}
				});
		}
		
		public void paint(Graphics g)  {
		    g.clearRect(0, 0, 30, rect.height);
			FontMetrics fm = g.getFontMetrics();
			int rTemp;
			int rT = (rData.max - rData.min) / yTick;
			for (int i=rData.min+rT; i<rData.max; i+=rT)  {
				rTemp = (int)(rect.height - rFact * (i - rData.min)) + 5;
				g.drawString(""+i, 5, rTemp);
			} 
			g.drawString(""+rData.min, 5, rect.height);
			g.drawString(""+rData.max, 5, 8);
		}

		public Dimension getPreferredSize()  {
			return new Dimension(30, 1);
		}
	}
		
	class XAxisArea extends Canvas  {
		public PopupMenu menu = new PopupMenu();

		public XAxisArea()   {
			Font r = new Font("Arial", Font.PLAIN, 10);
			setFont(r);
			add(menu);
			menu.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent ae)  {
						DataSet d = (DataSet)data.get(ae.getActionCommand());
						xData = d;
						drawChart();
					}
				});
			addMouseListener(
				new MouseAdapter()  {
					public void mouseClicked(MouseEvent e)  {
						menu.show((Component)e.getSource(), e.getX(), e.getY());
					}
				});
		}
		
		public void paint(Graphics g)  {
			FontMetrics fm = g.getFontMetrics();
		    g.clearRect(0, 0, rect.width+60, 40);

			int xTemp;
			int xT = (xData.max - xData.min) / xTick;
			for (int i=xData.min+xT; i<xData.max; i+=xT)  {
				xTemp = (int)(xFact * (i - xData.min)) + 30 - fm.stringWidth(""+i) / 2;
				g.drawString(""+i, xTemp, 12);
			} 
			g.drawString(""+xData.min, 30, 12);
			g.drawString(""+xData.max, rect.width+30-fm.stringWidth(""+xData.max), 12);
			
			String s = xData.name;
			g.drawString(s, (rect.width+60-fm.stringWidth(s))/2, 25);
		}

		public Dimension getPreferredSize()  {
			return new Dimension(1, 30);
		}
	}

	public void paint(Graphics g)  {
		setScalingFactors();
		paintComponents(g);
	}
	
	public void drawChart() {
		Graphics g = getGraphics();
		update(g);
	}

	public void setDataSource(DataSet x, DataSet y, DataSet r)  {
		xData = x;
		yData = y;
		rData = r;
	}
	
	public void setScalingFactors()  {
		yFact = ((double)rect.height / (yData.max - yData.min));
		xFact = ((double)rect.width  / (xData.max - xData.min));
		rFact = ((double)rect.height / (rData.max - rData.min));
	}
	
	public void addDataSet(DataSet t)  {
		if (data.contains(t))
			return;
		data.put(t.name, t);
		yAxis.menu.add(t.name);
		rAxis.menu.add(t.name);
		xAxis.menu.add(t.name);
	}
	
	public ChartFrame(Frame f, String name, DataSet x, DataSet y)  {
		super(f, name, false);
		setBackground(Color.white);
		data.put(x.name, x);
		data.put(y.name, y);
		rect = new Rectangle(400, 300);
		setDataSource(x, y, y);
		setSize(rect.width, rect.height);

		plot = new PlotArea();
		xAxis = new XAxisArea();
		yAxis = new YAxisArea();
		rAxis = new RAxisArea();
		title = new TitleArea(name);

		yAxis.menu.add(x.name);
		yAxis.menu.add(y.name);
		rAxis.menu.add(x.name);
		rAxis.menu.add(y.name);
		xAxis.menu.add(x.name);
		xAxis.menu.add(y.name);

		
		addWindowListener(
			new WindowAdapter()  {
			    public void windowClosing(WindowEvent we)  {
					((Dialog)we.getSource()).setVisible(false);
				}
			});
		addComponentListener(
			new ComponentAdapter()  {
				public void componentResized(ComponentEvent e) 	{
					rect =  plot.getBounds();
					setScalingFactors();
				}
			});
		setLayout(new BorderLayout(0, 0));
		add(plot, BorderLayout.CENTER);
		add(yAxis, BorderLayout.WEST);
		add(xAxis, BorderLayout.SOUTH);
		add(rAxis, BorderLayout.EAST);
		add(title, BorderLayout.NORTH);		
	}	
}
	

class DataSet  {
	public double data[];
	public String name;
	public String scale;
	public Color color;
	public int min, max;
	public int size;
	public int pointer;
	public boolean connect;
	
	public DataSet(String n, String sc, Color c, int s, int m, int M, boolean con) {
		name = n;
		scale = sc;
		color = c;
		min = m;
		max = M;
		size = s;
		data = new double[size];
		pointer = 0;
		connect = con;
	}
	
	public void addPoint(double val)  {
		if (val < min || max < val)
			return;
		data[pointer] = val;
		pointer = (pointer + 1) % size;
	}

	public void addPoint(double val, int min, int max)  {
		data[pointer] = val;
		pointer = (pointer + 1) % size;
		this.min = min;
		this.max = max;
	}
	
	public void clearData()  {
		pointer = 0;
		for (int i=0; i<size; i++)  {
			data[i] = 0;
		}
	}
}

