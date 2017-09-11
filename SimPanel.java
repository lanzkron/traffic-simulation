import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SimPanel extends Canvas implements Observer  {

	private static final float pi2 = (float)Math.PI * 2;
	private static final Color bgColor = new Color(0, 127, 0);
	private Image img = null;
	private SimDocument doc;
	private BasePanel currListener;
	
	public SimPanel(SimDocument d)  {
		doc = d;
		d.addObserver(this);
		setBackground(bgColor);

		addMouseListener(doc.currPanel);
		currListener = doc.currPanel;
//		addMouseMotionListener( new ObstcleMotionListener(doc));						
		/*
			new MouseAdapter()  {
				private double toAngle(Point p)  {
					p.x = p.x - 250;
					p.y = 250 - p.y;
					return Math.atan2(p.y, p.x);
				}

				private double toRadius(Point p)  {
					p.x = p.x - 250;
					p.y = 250 - p.y;
					return Math.sqrt(p.x*p.x+p.y+p.y);
				}
				
				public void mouseClicked(MouseEvent me)  {
					double rad = toRadius(me.getPoint());
					if (! (190 <= rad && rad <= 220))  {
						me.consume();
					}
				}
			}); */
			
	}

	public void paint(Graphics g)  {
		drawPosition();
	}
	
	public void update(Observable o, Object arg)  {		
		if (arg != null)  {
			removeMouseListener(currListener);
			addMouseListener(doc.currPanel);		
			currListener = doc.currPanel;
		}
		drawPosition();
	}
	
	private void drawPosition()  {
		int x, y;
		long roadLength = 1000;

		if (img == null)  {
			img = createImage(500, 500);
		}		
		Graphics g = img.getGraphics();
		g.setColor(bgColor);
		g.fillRect(0, 0, 500, 500);
		g.translate(250, 250);
		g.setColor(Color.lightGray);
		g.fillOval(-220, -220, 440, 440);
				
		doc.rp.obstacles.drawObstacles(g);
		
		g.setColor(bgColor);
		g.fillOval(-190, -190, 380, 380);

		doc.currPanel.specialDraw(g);
		doc.road.drawVehicles(g);

		Graphics h = getGraphics();
		if ( h != null )
			h.drawImage(img, 0, 0, this);
	}		
	
	public Dimension getPreferredSize()  {
		return new Dimension(500, 500);
	}
}
