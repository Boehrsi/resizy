package config;

import lombok.Data;
import utilities.ConstantUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class provides basic functionality for configuration load / store. The
 * object created by this class can be used inside the program for accessing and
 * editing configuration files.
 *
 * @author Boehrsi
 * @version 2.0
 */

@Data
public class Config {

    public static final String TARGET_DIRECTORY = "outputDir";
    public static final String RENAME_TARGET_DIRECTORY = "renameTargetDirectory";
    public static final String RENAME_PATTERN = "renamePattern";
    public static final String OUTPUT_MODIFIER = "outputModifier";
    public static final String PRESET = "preset";
    public static final String LANGUAGE = "language";
    public static final String TYPES = "types";
    public static final String OVERWRITE_WARNING = "overwriteWarning";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String FILE_TYPE_POSITION = "fileTypePosition";
    public static final String MULTI_THREADING = "useMultithreading";
    public static final String COPY_LAST_MODIFIED_DATE = "copyLastModifiedDate";

    private static Properties configProperties;

    public Config() {
        try {
            FileInputStream in = new FileInputStream(ConstantUtility.Paths.CONFIG);
            configProperties = new Properties();
            configProperties.load(in);
            in.close();
        } catch (IOException ignore) {
            throw new IllegalStateException("Config file couldn't get loaded");
        }
    }

    public boolean getAsBoolean(String key) {
        String value = configProperties.getProperty(key);
        return Boolean.valueOf(value);
    }

    public int getAsInt(String key) {
        String value = configProperties.getProperty(key);
        return Integer.valueOf(value);
    }

    public String get(String key) {
        return configProperties.getProperty(key);
    }

    public void set(String key, Object value) {
        String valueToStore = String.valueOf(value);
        configProperties.setProperty(key, valueToStore);
        store();
    }

    private void store() {
        try {
            FileOutputStream out = new FileOutputStream(ConstantUtility.Paths.CONFIG);
            configProperties.store(out, null);
            out.close();
        } catch (Exception ignore) {
        }
    }
}
