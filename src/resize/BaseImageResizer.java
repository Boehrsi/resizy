package resize;

import languages.Language;
import main.MainLogic;
import utilities.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

/**
 * Base class for extension for ImageResizer implementations.
 *
 * @author Boehrsi
 * @version 1.0
 */

public abstract class BaseImageResizer {
    Language language;
    boolean overwrite;
    MainLogic.UiSynchronization uiSynchronization;

    public void setup(Language lang, boolean overwrite, MainLogic.UiSynchronization uiSynchronization) {
        this.language = lang;
        this.overwrite = overwrite;
        this.uiSynchronization = uiSynchronization;
    }

    void resizeImage(String originalFile, int outputWidth, int outputHeight, String outputFile, String outputFileType, boolean copyLastMod) {
        FileTime lastModDate = null;
        BufferedImage inputImage = ImageUtility.readImage(originalFile);
        if (inputImage == null) {
            JOptionPane.showMessageDialog(null, language.getErrorText(5), language.getErrorTitle(5), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int[] sizes = ImageUtility.calcResize(inputImage.getWidth(), inputImage.getHeight(), outputWidth, outputHeight);
        outputWidth = sizes[0];
        outputHeight = sizes[1];

        int imgType = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();

        BufferedImage outputImage = new BufferedImage(outputWidth, outputHeight, imgType);
        Graphics2D g = outputImage.createGraphics();

        g.setRenderingHints(ImageUtility.getImageHints());
        g.setComposite(AlphaComposite.Src);
        g.drawImage(inputImage, 0, 0, outputWidth, outputHeight, null);
        g.dispose();

        if (!outputFile.contains(":\\")) {
            // No output folder is set, use input folder.
            outputFile = originalFile.substring(0, originalFile.lastIndexOf("\\")) + outputFile;
        }

        if (outputFileType.equals(language.get(Language.KEEP_DEFAULT_FILE_TYPE))) {
            // No output type is set, use input type.
            outputFileType = outputFile.substring(outputFile.lastIndexOf(".") + 1);
        } else {
            // Use costume set output type.
            outputFile = outputFile.substring(0, outputFile.lastIndexOf(".") + 1) + outputFileType;
        }

        if (copyLastMod) {
            File tempFile = new File(originalFile);
            try {
                lastModDate = Files.getLastModifiedTime(tempFile.toPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, language.getErrorText(7), language.getErrorTitle(7), JOptionPane.ERROR_MESSAGE);
            }
        }
        boolean writeResult = ImageUtility.writeImage(outputImage, outputFile, outputFileType, lastModDate, overwrite);
        if (!writeResult) {
            JOptionPane.showMessageDialog(null, language.getErrorText(6), language.getErrorTitle(6), JOptionPane.ERROR_MESSAGE);
        }
    }

    public abstract void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath, ArrayList<String> inputFileList, String outputFileType, boolean saveMetaData);

}
