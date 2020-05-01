package rename;


import languages.Language;
import lombok.NoArgsConstructor;
import main.MainLogic;
import utilities.ImageUtility;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

@NoArgsConstructor
public class Renamer {

    private Language language;
    private MainLogic.UiSynchronization uiSynchronization;

    public void setup(Language language, MainLogic.UiSynchronization uiSynchronization) {
        this.language = language;
        this.uiSynchronization = uiSynchronization;
    }

    public void renameImageList(ArrayList<String> inputFileList, String outputFilePattern, String outputPath) {
        Thread renameThread = new Thread(() -> {
            for (int index = 0; index < inputFileList.size(); index++) {
                String newOutputFileName = ImageUtility.generatePathWithIndex(outputPath, outputFilePattern, index);
                if (newOutputFileName != null) {
                    try {
                        renameImage(inputFileList.get(index), newOutputFileName);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, language.getErrorText(8), language.getErrorTitle(8), JOptionPane.ERROR_MESSAGE);
                    }
                }
                uiSynchronization.updateProgress();
            }
            uiSynchronization.finishProgress();
        }, "Renaming thread");
        renameThread.start();
    }

    private void renameImage(String inputFileName, String outputFileName) throws IOException {
        File inputFile = new File(inputFileName);
        if (!outputFileName.contains(":\\")) {
            // No output folder is set, use input folder.
            outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf("\\")) + outputFileName;
        }
        outputFileName += inputFileName.substring(inputFileName.lastIndexOf("."));
        File outputFile = new File(outputFileName);
        Files.copy(inputFile.toPath(), outputFile.toPath());
    }

}
