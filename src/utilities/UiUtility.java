package utilities;

import javax.swing.JTextPane;

import lombok.experimental.UtilityClass;

/**
 * 
 * Utility class for UI operations.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

@UtilityClass
public class UiUtility {
	
	public static void alterPane(JTextPane pane, String text) {
		pane.setText(text);
	}

}
