package core;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaces.ResizeStrategy;
import utilities.ImageUtility;

/**
 * 
 * Simple single threaded ImageResizer.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class ImageResizer extends BaseImageResizer implements ResizeStrategy {

	public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData) {

		Thread convertThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < inputFileList.size(); i++) {
					String outputFile = inputFileList.get(i);
					outputFile = ImageUtility.generatePath(outputModifier, outputPath, outputFile);
					try {
						resizeImage(inputFileList.get(i), calcWidth, calcHeight, outputFile, outputfileType,
								saveMetaData);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(null, lang.getErr3(), lang.getErr3t(), JOptionPane.ERROR_MESSAGE);
						convertButton.setEnabled(true);
					}
					uiSynchronization.updateProgress();
				}
				uiSynchronization.finishProgress();
				convertButton.setEnabled(true);
			}
		}, "Thread for convert");
		convertThread.start();
	}
}