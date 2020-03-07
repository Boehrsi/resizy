package utilities;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;

import static utilities.ConstantUtility.Strings.EMPTY;

/**
 * Utility class for image operations and checks.
 *
 * @author Boehrsi
 * @version 1.0
 */

@UtilityClass
public class ImageUtility {

    public static final String OUTPUT_FILE_PATTERN = "###";

    private static final HashMap<Key, Object> hints = new HashMap<>();

    static {
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public static BufferedImage readImage(String file) {
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File(file));
        } catch (IOException e) {
            return null;
        }
        return originalImage;
    }

    public static boolean writeImage(BufferedImage outputImage, String outputFile, String type, FileTime lastModDate) {
        try {
            File filePath = new File(outputFile);
            ImageIO.write(outputImage, type, filePath);
            Files.setLastModifiedTime(filePath.toPath(), lastModDate);
            return true;
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

    public static int[] calcResize(int inputWidthInt, int inputHeightInt, int outputWidthInt, int outputHeightInt) {
        int[] outputSize = new int[2];
        if ((double) outputWidthInt == 0) {
            outputSize[0] = (int) Math.round(((double) inputWidthInt / 100) * ((double) outputHeightInt / (double) inputHeightInt * 100));
            outputSize[1] = outputHeightInt;
        } else if ((double) outputHeightInt == 0) {
            outputSize[0] = outputWidthInt;
            outputSize[1] = (int) Math.round(((double) inputHeightInt / 100) * ((double) outputWidthInt / (double) inputWidthInt * 100));
        } else {
            outputSize[0] = outputWidthInt;
            outputSize[1] = outputHeightInt;
        }
        return outputSize;
    }

    public static boolean isGivenSizeValid(String sizeValue) {
        return !sizeValue.equals(EMPTY) && !sizeValue.equals("0");
    }

    public static String generatePath(String outputModifier, String outputPath, String outputFile) {
        return outputPath + File.separator
                + outputFile.substring(outputFile.lastIndexOf("\\") + 1, outputFile.lastIndexOf(".")) + outputModifier
                + outputFile.substring(outputFile.lastIndexOf("."));
    }

    public static String generatePathWithIndex(String outputPath, String outputFileNameWithPattern, int index) {
        if (!outputFileNameWithPattern.contains(OUTPUT_FILE_PATTERN)) {
            return null;
        }
        String outputFile = outputFileNameWithPattern.replace(OUTPUT_FILE_PATTERN, String.valueOf(index));
        return outputPath + File.separator + outputFile;
    }

    public static HashMap<Key, Object> getImageHints() {
        return hints;
    }

}
