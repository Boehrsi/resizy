package main;

import javax.swing.*;

/**
 * Core class for program initialization and launch.
 *
 * @author Boehrsi
 * @version 1.0
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
        System.setProperty("file.encoding", "UTF-8");
        JFrame mainUI = new JFrame();
        mainUI.setContentPane(new MainUi(mainUI).getContainer());
    }

}
