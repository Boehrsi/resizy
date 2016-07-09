package core;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaces.ResizeStrategy;

public class ImageResizer extends BaseImageResizer implements ResizeStrategy {

	public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData) {

		Thread convertThread = new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				for (int i = 0; i < inputFileList.size(); i++) {
					String outputFile = inputFileList.get(i);
					outputFile = outputPath + File.separator
							+ outputFile.substring(outputFile.lastIndexOf("\\") + 1, outputFile.lastIndexOf("."))
							+ outputModifier + outputFile.substring(outputFile.lastIndexOf("."), outputFile.length());
					try {
						resizeImage(inputFileList.get(i), calcWidth, calcHeight, outputFile, outputfileType,
								saveMetaData);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(null, lang.getErr3(), lang.getErr3t(), JOptionPane.ERROR_MESSAGE);
						convertButton.setEnabled(true);
					}
					uiSynchronization.updateProgress();
				}

				convertButton.setEnabled(true);
				long stopTime = System.currentTimeMillis();
				long elapsedTime = stopTime - startTime;
				System.out.println(elapsedTime);
				System.out.println("ready");
			}
		}, "Thread for convert");
		convertThread.start();
	}
}