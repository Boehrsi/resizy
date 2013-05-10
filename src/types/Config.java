package types;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties configFile;
	private String outputDir, outputMod, preset, lang;

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
			try {
				width = Integer.parseInt(configFile.getProperty("width"));
				height = Integer.parseInt(configFile.getProperty("height"));
			} catch (NumberFormatException nfe) {

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getHeight() {
		return String.valueOf(height);
	}

	public String getLang() {
		return lang;
	}

	public String getLanguage() {
		return lang;

	}

	public String getOutputDir() {
		return outputDir;
	}

	public String getOutputMod() {
		return outputMod;
	}

	public String getPreset() {
		return preset;
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

	public void setPreset(String preset) {
		this.preset = preset;
		configFile.setProperty("preset", preset);
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
