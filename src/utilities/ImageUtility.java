package utilities;

import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * 
 * Utility class for image operations and checks.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class ImageUtility {

	static final HashMap<Key, Object> hints = new HashMap<Key, Object>();

	static {
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private ImageUtility() {
		// Utility class
	}

	public static BufferedImage readImage(String file) {
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return originalImage;
	}

	public static void writeImage(BufferedImage outputImage, String outputFile, String type, FileTime lastModDate) {
		try {
			File filePath = new File(outputFile);
			ImageIO.write(outputImage, type, filePath);
			try {
				Files.setLastModifiedTime(filePath.toPath(), lastModDate);
			} catch (NullPointerException ignore) {
			}
		} catch (IOException e) {
		}
	}

	public static int[] calcResize(int inputWidthInt, int inputHeightInt, int outputWidthInt, int outputHeightInt) {
		double inputWidth = inputWidthInt;
		double inputHeight = inputHeightInt;
		double outputWidth = outputWidthInt;
		double outputHeight = outputHeightInt;
		int[] outputSize = new int[2];
		if (outputWidth == 0) {
			outputSize[0] = (int) Math.round((inputWidth / 100) * (outputHeight / inputHeight * 100));
			outputSize[1] = outputHeightInt;
		} else if (outputHeight == 0) {
			outputSize[0] = outputWidthInt;
			outputSize[1] = (int) Math.round((inputHeight / 100) * (outputWidth / inputWidth * 100));
		} else {
			outputSize[0] = outputWidthInt;
			outputSize[1] = outputHeightInt;
		}
		return outputSize;
	}

	public static boolean isGivenSizeValid(String sizeValue) {
		return !sizeValue.equals(ConstantUtility.EMPTY) && !sizeValue.equals("0");
	}

	public static String generatePath(String outputModifier, String outputPath, String outputFile) {
		return outputPath + File.separator
				+ outputFile.substring(outputFile.lastIndexOf("\\") + 1, outputFile.lastIndexOf(".")) + outputModifier
				+ outputFile.substring(outputFile.lastIndexOf("."), outputFile.length());
	}

	public static HashMap<Key, Object> getImageHints() {
		return hints;
	}

}
