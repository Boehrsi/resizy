package main;

import javax.swing.*;

import static utilities.ConstantUtility.Settings.FILE_ENCODING_KEY;
import static utilities.ConstantUtility.Settings.FILE_ENCODING_VALUE_UTF8;

/**
 * Core class for program initialization and launch.
 *
 * @author Boehrsi
 * @version 2.0
 */

public class Main {

    public static void main(final String[] args) {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setupUi();
    }

    private static void setupUi() {
        System.setProperty(FILE_ENCODING_KEY, FILE_ENCODING_VALUE_UTF8);
        JFrame mainUI = new JFrame();
        mainUI.setContentPane(new MainUi(mainUI).getContainer());
    }

}
