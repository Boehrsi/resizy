package utilities;

import javax.swing.*;
import java.awt.*;

import static utilities.ConstantUtility.Paths.PATH_LOGO;

/**
 * Utility class for UI operations.
 *
 * @author Boehrsi
 * @version 1.0
 */

public class UiUtility {

    public static void setLogo(JFrame frame) {
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PATH_LOGO));
    }

    public static void setTitle(JFrame frame, String programName, String windowTitle) {
        frame.setTitle(programName + " - " + windowTitle);
    }

    public static JFrame getPopupFrame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setType(Window.Type.POPUP);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        return frame;
    }

}
