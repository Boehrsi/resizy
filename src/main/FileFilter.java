package main;

import javax.swing.*;
import java.io.File;

/**
 * File filter for image file import. JPG, JPEG and PNG are currently allowed.
 *
 * @author Boehrsi
 * @version 1.0
 */

class FileFilter {

    private final DefaultListModel<String> inputFilesModel;

    private final JList<String> inputFiles;

    FileFilter(DefaultListModel<String> inputFilesModel, JList<String> inputFiles) {
        this.inputFilesModel = inputFilesModel;
        this.inputFiles = inputFiles;
    }

    void filterImage(File file) {
        if (file.isFile() && !inputFilesModel.contains(file.getAbsolutePath())
                && (file.getName().toLowerCase().contains(".png") || file.getName().toLowerCase().contains(".jpg")
                || file.getName().toLowerCase().contains(".jpeg"))) {
            inputFilesModel.addElement(file.getAbsolutePath());
            inputFiles.setModel(inputFilesModel);
        }
    }
}
