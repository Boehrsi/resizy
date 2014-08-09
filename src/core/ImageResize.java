package core;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageResize {

	private static int[] calcResize(int inputWidthInt, int inputHeightInt,
			int outputWidthInt, int outputHeightInt) {
		double inputWidth = inputWidthInt;
		double inputHeight = inputHeightInt;
		double outputWidth = outputWidthInt;
		double outputHeight = outputHeightInt;
		int[] outputSize = new int[2];
		if (outputWidth == 0) {
			outputSize[0] = (int) Math.round((inputWidth / 100)
					* (outputHeight / inputHeight * 100));
			outputSize[1] = outputHeightInt;
		} else if (outputHeight == 0) {
			outputSize[0] = outputWidthInt;
			outputSize[1] = (int) Math.round((inputHeight / 100)
					* (outputWidth / inputWidth * 100));
		} else {
			outputSize[0] = outputWidthInt;
			outputSize[1] = outputHeightInt;
		}
		return outputSize;
	}

	private static BufferedImage readImg(String file) {
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return originalImage;
	}

	public static void resizeImageWithHint(String originalFile,
			int outputWidth, int outputHeight, String outputFile,
			String outputFileType, boolean copyLastMod) {
		FileTime lastModDate = null;
		BufferedImage inputImage = readImg(originalFile);
		int[] sizes = calcResize(inputImage.getWidth(), inputImage.getHeight(),
				outputWidth, outputHeight);
		outputWidth = sizes[0];
		outputHeight = sizes[1];

		int imgType = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
				: inputImage.getType();

		BufferedImage outputImage = new BufferedImage(outputWidth,
				outputHeight, imgType);
		Graphics2D g = outputImage.createGraphics();
		HashMap<Key, Object> hints = new HashMap<Key, Object>();
		hints.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		hints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(hints);
		g.setComposite(AlphaComposite.Src);
		g.drawImage(inputImage, 0, 0, outputWidth, outputHeight, null);
		g.dispose();

		/*
		 * No output folder is set, use input folder.
		 */
		if (!outputFile.contains(":\\")) {
			outputFile = originalFile.substring(0,
					originalFile.lastIndexOf("\\"))
					+ outputFile;
		}

		/*
		 * No output type is set, use input type.
		 */
		if (outputFileType.equals("")) {
			outputFileType = outputFile
					.substring(outputFile.lastIndexOf(".") + 1);
		}
		/*
		 * Use costum set output type.
		 */
		else {
			outputFile = outputFile.substring(0,
					outputFile.lastIndexOf(".") + 1) + outputFileType;
		}

		if (copyLastMod) {
			File tempFile = new File(originalFile);
			try {
				lastModDate = Files.getLastModifiedTime(tempFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writeImg(outputImage, outputFile, outputFileType, lastModDate);

	}

	private static void writeImg(BufferedImage outputImage, String outputFile,
			String type, FileTime lastModDate) {
		try {
			File filePath = new File(outputFile);
			ImageIO.write(outputImage, type, filePath);
			try {
				Files.setLastModifiedTime(filePath.toPath(), lastModDate);
			} catch (NullPointerException npe) {
			}
		} catch (IOException e) {
		}
	}
}