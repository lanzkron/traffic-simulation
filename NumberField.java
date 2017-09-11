import java.awt.*;
import java.awt.event.*;

/**
 * A TextField variant that allows only leagal number values to be entered.
 * The component checks it's input at run time and disallows illeagal 
 * keystrokes.
 * 
 * @version 1.2
 * @author Misha Sklarz
 * @author Motti Lanzkron
 * @see TextField 
 */
public class NumberField extends TextField  {

	/**
	 * Construct a new NumberField instance, with a startig value of val
	 * 
	 * @param Starting Value
	 */
	public NumberField (float val)  {
		super(""+val);
		// add key listener to "swallow" illeagal key strokes.
		addKeyListener(
			new KeyAdapter()  {
				// catch key pressing event
				public void keyPressed(KeyEvent ke)  {
					int t = Character.getType(ke.getKeyChar());
					// allow control key strokes (enter, home, etc.)
					if (t == Character.CONTROL)  {
						return;
					}
					// check if key stroke leads to leagal number format
					try {
						Float.valueOf(getText() + ke.getKeyChar());
					}
					// if not, consume the event...
					catch (NumberFormatException nfe)  {
						ke.consume();
						return;
					}
				}
			});
	}

	/**
	 * Return the number value of the NumberField. 
	 * 
	 * @return float value of instance.
	 * @exception thrown of for some reason the format is illeagal
	 */
	public float getFloatValue() throws NumberFormatException {
		return Float.valueOf(getText()).floatValue();
	}
}
