package main;

import config.Config;
import languages.Language;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

import static config.Config.*;
import static utilities.ConstantUtility.Strings.EMPTY;

/**
 * Main logic class for the config UI. Mostly called by MainUi.java.
 *
 * @author Boehrsi
 * @version 1.0
 */

@RequiredArgsConstructor
public class MainLogic {

    public interface UiSynchronization {

        void updateProgress();

        void finishProgress();

    }

    @Getter
    private DefaultListModel<String> inputFilesModel = new DefaultListModel<>();

    @Getter
    private DefaultComboBoxModel<String> sizePresetModel = new DefaultComboBoxModel<>();

    @Getter
    private DefaultComboBoxModel<String> fileTypeModel = new DefaultComboBoxModel<>();

    @Getter
    private Config config = new Config();

    @Getter
    private Language language = new Language(config.get(LANGUAGE));

    void setMultiThreading(boolean selected) {
        config.set(MULTI_THREADING, selected);
    }

    boolean getMultiThreading() {
        return config.getAsBoolean(MULTI_THREADING);
    }

    void loadPresets() {
        sizePresetModel.addElement(language.get(Language.NO_PRESET_SIZE));
        String[] sizeArray = config.get(PRESET).split(",");
        for (String size : sizeArray) {
            sizePresetModel.addElement(size);
        }

        fileTypeModel.addElement(language.get(Language.KEEP_DEFAULT_FILE_TYPE));
        String[] typesArray = config.get(TYPES).split(",");
        for (String type : typesArray) {
            fileTypeModel.addElement(type);
        }
    }

    void addSizePresent(String width, String height) {
        if (width.isEmpty() || height.isEmpty() || (Integer.valueOf(width) < 1 && Integer.valueOf(height) < 1)) {
            //TODO
            return;
        }
        String newPreset = width + "x" + height;
        if (sizePresetModel.getIndexOf(newPreset) == -1) {
            sizePresetModel.addElement(newPreset);
            saveSizePresets();
        }
    }

    private void saveSizePresets() {
        StringBuilder newPreset = new StringBuilder(EMPTY);
        for (int i = 0; i < sizePresetModel.getSize(); i++) {
            newPreset.append(sizePresetModel.getElementAt(i)).append(",");
        }
        config.set(PRESET, newPreset.toString());
    }

    void removeSizePreset(int selectedIndex) {
        if (selectedIndex == 0) {
            return;
        }
        sizePresetModel.removeElementAt(selectedIndex);
        saveSizePresets();
    }

    int getInputFilesModelSize() {
        return inputFilesModel.getSize();
    }

    void setCopyLastModifiedDate(boolean selected) {
        config.set(COPY_LAST_MODIFIED_DATE, selected);
    }

    void setFileTypePosition(int selectedIndex) {
        config.set(FILE_TYPE_POSITION, selectedIndex);
    }

    void resetOutputSettings() {
        config.set(TARGET_DIRECTORY, EMPTY);
        config.set(OUTPUT_MODIFIER, EMPTY);
        config.set(FILE_TYPE_POSITION, 0);
        config.set(COPY_LAST_MODIFIED_DATE, false);
        config.set(WIDTH, 0);
        config.set(HEIGHT, 0);
    }
}