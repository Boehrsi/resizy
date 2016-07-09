package interfaces;

import java.util.ArrayList;

public interface ResizeStrategy {

	void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData);

}
