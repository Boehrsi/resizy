package resize;

import utilities.ImageUtility;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Single threaded ImageResizer.
 *
 * @author Boehrsi
 * @version 1.0
 */

public class ImageResizer extends BaseImageResizer {

    @Override
    public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
                                ArrayList<String> inputFileList, String outputFileType, boolean saveMetaData) {

        Thread convertThread = new Thread(() -> {
            for (String outputFile : inputFileList) {
                outputFile = ImageUtility.generatePath(outputModifier, outputPath, outputFile);
                try {
                    resizeImage(outputFile, calcWidth, calcHeight, outputFile, outputFileType,
                            saveMetaData);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(null, language.getErrorText(3), language.getErrorTitle(3), JOptionPane.ERROR_MESSAGE);
                }
                uiSynchronization.updateProgress();
            }
            uiSynchronization.finishProgress();
        }, "Conversion thread");
        convertThread.start();
    }
}