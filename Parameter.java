import java.awt.*;

/**
 * A visual representation of an abstract parameter.
 * Includes the name of the parameter, and a numerical text field
 * for setting and reading the parameters value.
 *
 * @version 1.2
 * @author Motti Lanzkron
 * @author Misha Sklarz
 * @see NumberField 
 */
public class Parameter extends Panel  {
	/** number editing field for setting the parameter. */
	NumberField field;
	
	/**
	 * Construction
	 * 
	 * @param to be associated with the parameter
	 * @param to be set to the parameter at startup
	 */
	public Parameter(String name, float value)  {
		setLayout(new GridLayout(1, 2));		
		add(new Label(name), BorderLayout.WEST);
		field = new NumberField(value);
		add(field, BorderLayout.CENTER);
	}	
	
	/**
	 * Set the parameter value to a new value.  Also cause invalidation, 
	 * so that the canges will be shown on the moniter.
	 * 
	 * @param new value to set to the parameter
	 */
	public void setValue(float val)  {
		field.setText(""+val);
		field.repaint();
	}
	
	/**
	 * Get the current value associated with the parameter.
	 * 
	 * @return current value of the paramter
	 */
	public float getValue()  {
		return field.getFloatValue();
	}
}
