package core;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

import javax.swing.JButton;

import interfaces.UiSynchronization;
import utilities.ImageUtility;

/**
 * 
 * Base class for extension for ImageResizer implementations.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public abstract class BaseImageResizer {
	Config config;
	Language lang;
	JButton convertButton;
	UiSynchronization uiSynchronization;

	public void setup(Config config, Language lang, JButton convertButton, UiSynchronization uiSynchronization) {
		this.config = config;
		this.lang = lang;
		this.convertButton = convertButton;
		this.uiSynchronization = uiSynchronization;
	}

	protected void resizeImage(String originalFile, int outputWidth, int outputHeight, String outputFile,
			String outputFileType, boolean copyLastMod) {
		FileTime lastModDate = null;
		BufferedImage inputImage = ImageUtility.readImage(originalFile);
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

		if (outputFileType.equals("")) {
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
				e.printStackTrace();
			}
		}
		ImageUtility.writeImage(outputImage, outputFile, outputFileType, lastModDate);
	}

	public abstract void resizeImageList(int calcWidth, int calcHeight, String outputModifier, String outputPath,
			ArrayList<String> inputFileList, String outputfileType, boolean saveMetaData);

}
