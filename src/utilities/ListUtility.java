package utilities;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

/**
 * 
 * Utility class for list operations.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class ListUtility {

	private ListUtility() {
		// Utility class
	}

	public static ArrayList<String> defaultModeltoArrayList(DefaultListModel<String> model) {
		ArrayList<String> list = new ArrayList<>();
		list.addAll(Collections.list(model.elements()));
		return list;
	}

}
