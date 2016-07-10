package core;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import utilities.ImageUtility;

/**
 * 
 * Multi threaded ImageResizer.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class MultiThreadImageResizer extends BaseImageResizer {

	@Override
	public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData) {
		Thread convertThread = new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(10);
				for (int i = 0; i < inputFileList.size(); i++) {
					String outputFile = inputFileList.get(i);
					String outputFilePath = ImageUtility.generatePath(outputModifier, outputPath, outputFile);
					executor.execute(() -> {
						try {
							resizeImage(outputFile, calcWidth, calcHeight, outputFilePath, outputfileType,
									saveMetaData);
						} catch (IllegalArgumentException iae) {
							JOptionPane.showMessageDialog(null, lang.getErr3(), lang.getErr3t(),
									JOptionPane.ERROR_MESSAGE);
							executor.shutdownNow();
							convertButton.setEnabled(true);
						}
						uiSynchronization.updateProgress();
					});
				}
				executor.shutdown();
				try {
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
					uiSynchronization.finishProgress();
					convertButton.setEnabled(true);
				} catch (InterruptedException ignore) {
				}
			}
		}, "Thread for convert");
		convertThread.start();
	}

}
