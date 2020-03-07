package resize;

import utilities.ImageUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Multi threaded ImageResizer.
 *
 * @author Boehrsi
 * @version 1.0
 */

public class MultiThreadImageResizer extends BaseImageResizer {

    @Override
    public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
                                ArrayList<String> inputFileList, String outputFileType, boolean saveMetaData) {
        Thread convertThread = new Thread(() -> {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (String outputFile : inputFileList) {
                String outputFilePath = ImageUtility.generatePath(outputModifier, outputPath, outputFile);
                executor.execute(() -> {
                    try {
                        resizeImage(outputFile, calcWidth, calcHeight, outputFilePath, outputFileType, saveMetaData);
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(null, language.getErrorText(3), language.getErrorTitle(3),
                                JOptionPane.ERROR_MESSAGE);
                        executor.shutdownNow();
                    }
                    uiSynchronization.updateProgress();
                });
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                uiSynchronization.finishProgress();
            } catch (InterruptedException ignore) {
            }
        }, "Conversion thread");
        convertThread.start();
    }

}
