package languages;

import lombok.Data;

import java.util.*;

/**
 * Internationalization handling based on ResourceBundles.
 *
 * @author Boehrsi
 * @version 2.0
 */

@Data
public class Language {

    public static final String NAME = "name";
    public static final String FILE = "file";
    public static final String CLOSE = "close";
    public static final String LANGUAGE = "language";
    public static final String CONFIG = "config";
    public static final String OPEN = "open";
    public static final String RESET = "reset";
    public static final String HELP = "help";
    public static final String ABOUT = "about";
    public static final String INPUT_FILES = "inputFiles";
    public static final String OUTPUT_MODIFIER = "outputModifier";
    public static final String TARGET_DIRECTORY = "targetDirectory";
    public static final String COPY_LAST_MODIFIED_DATE = "copyLastModifiedDate";
    public static final String FILE_TYPE = "fileType";
    public static final String SIZE = "size";
    public static final String PRESET = "preset";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String SAVE_PRESET = "savePreset";
    public static final String CONVERT = "convert";
    public static final String PROGRESS = "progress";
    public static final String INPUT = "input";
    public static final String CLEAR_INPUT = "clearInput";
    public static final String OUTPUT = "output";
    public static final String RESET_OUTPUT = "resetOutput";
    public static final String USE_MULTITHREADING = "useMultithreading";
    public static final String NO_PRESET_SIZE = "noPresetSize";
    public static final String KEEP_DEFAULT_FILE_TYPE = "keepDefaultFileType";
    public static final String RESTART = "restart";
    public static final String INFORMATION = "information";
    public static final String HINT_INPUT_FILES = "hintInputFiles";
    public static final String HINT_TARGET_DIRECTORY = "hintTargetDirectory";
    public static final String HINT_OUTPUT_MODIFIER = "hintOutputModifier";
    public static final String HINT_SIZE = "hintSize";
    public static final String HINT_SAVE_PRESET = "hintSavePreset";
    public static final String HINT_MANAGE_PRESET = "hintManagePreset";
    public static final String HINT_FILE_TYPE = "hintFileType";
    public static final String HINT_COPY_LAST_MODIFIED_DATE = "hintCopyLastModifiedDate";
    public static final String HINT_USE_MULTITHREADING = "hintUseMultithreading";
    public static final String VERSION = "version";
    public static final String CHECK_UPDATE = "checkUpdate";
    public static final String AT = "at";
    public static final String UPDATE = "update";
    public static final String OVERWRITE_TITLE = "overwriteTitle";
    public static final String OVERWRITE_TEXT = "overwriteText";
    public static final String BUG = "bug";
    public static final String FEATURE = "feature";
    Map<String, String> supportedLanguages = new HashMap<String, String>() {
        {
            put("Deutsch", "de");
            put("English", "en");
        }
    };
    ResourceBundle bundle;

    public Language(String languageName) {
        String languageIdentifier = supportedLanguages.get(languageName);
        Locale locale = new Locale(languageIdentifier);
        bundle = ResourceBundle.getBundle("languages.language", locale);
    }

    public ArrayList<String> langSelectable() {
        return new ArrayList<>(supportedLanguages.keySet());
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public String getErrorTitle(int id) {
        return bundle.getString("error" + id + "Title");
    }

    public String getErrorText(int id) {
        return bundle.getString("error" + id);
    }

}
