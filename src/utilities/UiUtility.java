package utilities;

import javax.swing.JTextPane;

/**
 * 
 * Utility class for UI operations.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class UiUtility {

	private UiUtility() {
		// Utility class
	}

	public static void alterPane(JTextPane pane, String text) {
		pane.setText(text);
	}

}
