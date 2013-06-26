package types;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * This class provides basic functionality for configuration load / store. The
 * object created form this class can be used inside the program for accessing
 * and editing configuration files.
 * 
 * @author Boehrsi
 * @version 0.1
 * 
 * 
 */
public class Config {
	private static Properties configFile;
	private String outputDir, outputMod, preset, lang, version;

	int width, height;

	FileOutputStream out;
	FileInputStream in;

	/**
	 * Default constructor - loading necessary configuration values form
	 * cfg/config.ini file.
	 */
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
			try {
				width = Integer.parseInt(configFile.getProperty("width"));
				height = Integer.parseInt(configFile.getProperty("height"));
			} catch (NumberFormatException nfe) {

			}
		} catch (IOException e) {
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Height value form configuration file
	 */
	public String getHeight() {
		return String.valueOf(height);
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Language value form configuration file
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Output direcotry value form configuration file
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Output modifier value form configuration file
	 */
	public String getOutputMod() {
		return outputMod;
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Presets value form configuration file
	 */
	public String getPreset() {
		return preset;
	}

	/**
	 * Returns values form configuration file
	 * 
	 * @return Width value form configuration file
	 */
	public String getWidth() {
		return String.valueOf(width);
	}

	/**
	 * Sets the height and store it in the configuration.
	 * 
	 * @param height
	 *            Height value for storing.
	 */
	public void setHeight(String height) {
		this.height = Integer.parseInt(height);
		configFile.setProperty("height", height);
		store();
	}

	/**
	 * Sets the language and store it in the configuration.
	 * 
	 * @param lang
	 *            Language value for storing.
	 */
	public void setLanguage(String lang) {
		this.lang = lang;
		configFile.setProperty("lang", lang);
		store();
	}

	/**
	 * Sets the output directory and store it in the configuration.
	 * 
	 * @param outputDir
	 *            Output directory value for storing.
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
		configFile.setProperty("outputDir", outputDir);
		store();
	}

	/**
	 * Sets the output modifier and store it in the configuration.
	 * 
	 * @param outputMod
	 *            Output modifier value for storing.
	 */
	public void setOutputMod(String outputMod) {
		this.outputMod = outputMod;
		configFile.setProperty("outputMod", outputMod);
		store();
	}

	/**
	 * Sets the preset values for sizes and store it in the configuration.
	 * 
	 * @param preset
	 *            Preset value for storing.
	 */
	public void setPreset(String preset) {
		this.preset = preset;
		configFile.setProperty("preset", preset);
		store();
	}

	/**
	 * Sets the width and store it in the configuration.
	 * 
	 * @param width
	 *            Width value for storing.
	 */
	public void setWidth(String width) {
		this.width = Integer.parseInt(width);
		configFile.setProperty("width", width);
		store();
	}

	/**
	 * Store configuration values in cfg/config.ini.
	 */
	private void store() {
		try {
			out = new FileOutputStream("cfg/config.ini");
			configFile.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

}
