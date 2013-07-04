package core;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class FileFilter {
	DefaultListModel<String> inputFilesModel;
	JList<String> inputFiles;

	public FileFilter(DefaultListModel<String> inputFilesModel,
			JList<String> inputFiles) {
		this.inputFilesModel = inputFilesModel;
		this.inputFiles = inputFiles;
	}

	public void filterImage(File file) {
		if (file.isFile()
				&& (file.getName().toLowerCase().contains(".png")
						|| file.getName().toLowerCase().contains(".jpg") || file
						.getName().toLowerCase().contains(".jpeg"))) {
			inputFilesModel.addElement(file.getAbsolutePath());
			inputFiles.setModel(inputFilesModel);
		}
	}
}
