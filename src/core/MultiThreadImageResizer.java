package core;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import interfaces.ResizeStrategy;

public class MultiThreadImageResizer extends BaseImageResizer implements ResizeStrategy {

	@Override
	public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData) {
		Thread convertThread = new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(10);
				for (int i = 0; i < inputFileList.size(); i++) {
					String outputFile = inputFileList.get(i);
					String outputFilePath = outputPath + File.separator
							+ outputFile.substring(outputFile.lastIndexOf("\\") + 1, outputFile.lastIndexOf("."))
							+ outputModifier + outputFile.substring(outputFile.lastIndexOf("."), outputFile.length());
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
				convertButton.setEnabled(true);
				executor.shutdown();
				try {
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
					uiSynchronization.finishProgress();
				} catch (InterruptedException ignore) {
				}
			}
		}, "Thread for convert");
		convertThread.start();
	}
}
