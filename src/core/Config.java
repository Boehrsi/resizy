package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.Data;

/**
 * 
 * This class provides basic functionality for configuration load / store. The
 * object created by this class can be used inside the program for accessing and
 * editing configuration files.
 * 
 * @author Boehrsi
 * @version 1.1
 * 
 */

@Data
public class Config {
	private static Properties configFile;
	private String outputDir, outputMod, preset, lang, version, types, overwrite, useMultithreading;
	int width, height;
	FileOutputStream out;
	FileInputStream in;

	public Config() {
		try {
			in = new FileInputStream("cfg/config.ini");
			configFile = new Properties();
			configFile.load(in);
			in.close();
			outputDir = configFile.getProperty("outputDir");
			outputMod = configFile.getProperty("outputMod");
			preset = configFile.getProperty("preset");
			lang = configFile.getProperty("lang");
			version = configFile.getProperty("version");
			types = configFile.getProperty("types");
			overwrite = configFile.getProperty("overwrite");
			useMultithreading = configFile.getProperty("useMultithreading");
			try {
				width = Integer.parseInt(configFile.getProperty("width"));
				height = Integer.parseInt(configFile.getProperty("height"));
			} catch (NumberFormatException ignore) {
			}
		} catch (IOException ignore) {
		}
	}

	public String getHeight() {
		return String.valueOf(height);
	}

	public String getWidth() {
		return String.valueOf(width);
	}

	public void setHeight(String height) {
		this.height = Integer.parseInt(height);
		configFile.setProperty("height", height);
		store();
	}

	public void setLanguage(String lang) {
		this.lang = lang;
		configFile.setProperty("lang", lang);
		store();
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
		configFile.setProperty("outputDir", outputDir);
		store();
	}

	public void setOutputMod(String outputMod) {
		this.outputMod = outputMod;
		configFile.setProperty("outputMod", outputMod);
		store();
	}

	public void setOverwrite(String overwrite) {
		this.overwrite = overwrite;
		configFile.setProperty("overwrite", overwrite);
		store();
	}

	public void setPreset(String preset) {
		this.preset = preset;
		configFile.setProperty("preset", preset);
		store();
	}

	public void setUseMultithreading(String useMultithreading) {
		this.useMultithreading = useMultithreading;
		configFile.setProperty("useMultithreading", useMultithreading);
		store();
	}
	
	public void setWidth(String width) {
		this.width = Integer.parseInt(width);
		configFile.setProperty("width", width);
		store();
	}

	private void store() {
		try {
			out = new FileOutputStream("cfg/config.ini");
			configFile.store(out, null);
			out.close();
		} catch (Exception ignore) {
		}
	}

}
