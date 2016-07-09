package utilities;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

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
