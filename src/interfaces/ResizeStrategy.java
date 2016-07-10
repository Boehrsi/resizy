package interfaces;

import java.util.ArrayList;

/**
 * 
 * Interface for different resize strategies.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public interface ResizeStrategy {

	void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData);

}
