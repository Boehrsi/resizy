package utilities;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

import lombok.experimental.UtilityClass;

/**
 * 
 * Utility class for list operations.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

@UtilityClass
public class ListUtility {

	public static ArrayList<String> defaultModeltoArrayList(DefaultListModel<String> model) {
		ArrayList<String> list = new ArrayList<>();
		list.addAll(Collections.list(model.elements()));
		return list;
	}

}
