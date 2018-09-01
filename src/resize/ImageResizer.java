package resize;

import utilities.ImageUtility;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Simple single threaded ImageResizer.
 *
 * @author Boehrsi
 * @version 1.0
 */

public class ImageResizer extends BaseImageResizer {

    @Override
    public void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
                                ArrayList<String> inputFileList, String outputFileType, boolean saveMetaData) {

        Thread convertThread = new Thread(() -> {
            for (int i = 0; i < inputFileList.size(); i++) {
                String outputFile = inputFileList.get(i);
                outputFile = ImageUtility.generatePath(outputModifier, outputPath, outputFile);
                try {
                    resizeImage(inputFileList.get(i), calcWidth, calcHeight, outputFile, outputFileType,
                            saveMetaData);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(null, language.getErrorText(3), language.getErrorTitle(3), JOptionPane.ERROR_MESSAGE);
                    convertButton.setEnabled(true);
                }
                uiSynchronization.updateProgress();
            }
            uiSynchronization.finishProgress();
            convertButton.setEnabled(true);
        }, "Conversion thread");
        convertThread.start();
    }
}