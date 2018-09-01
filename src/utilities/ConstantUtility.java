package utilities;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for globally used constants.
 *
 * @author Boehrsi
 * @version 1.0
 */

@UtilityClass
public class ConstantUtility {

    public class Version {

        public static final String CORE = "1.0";

    }

    public class Urls {

        public static final String GITHUB = "https://github.com/Boehrsi/resizy/releases";

        public static final String GITHUB_BUG_FEATURE = "https://github.com/Boehrsi/resizy/issues/new";

        public static final String GITHUB_IO = "https://boehrsi.github.io/resizy/";

        public static final String BOEHRSI_DE = "https://boehrsi.de/projects/resizy/";
    }

    public class Strings {

        public static final String EMPTY = "";

        public static final String GITHUB_PAGES = "GitHub Pages";

        public static final String BOEHRSI_DE = "www.Boehrsi.de";

    }

    public class Paths {

        public static final String PATH_LOGO = "res/icon.png";

        public static final String CONFIG = "cfg/config.ini";

        public static final String CONFIG_BACKUP = "cfg/config.ini.backup";

    }

    public class Size {

        public static final int ABOUT_WIDTH = 500;

        public static final int ABOUT_HEIGHT = 285;

        public static final int MAIN_WIDTH = 700;

        public static final int MAIN_HEIGHT = 550;

        public static final int HELP_WIDTH = 600;

        public static final int HELP_HEIGHT = 600;

        public static final int DIALOG_WIDTH = 400;

        public static final int DIALOG_HEIGHT = 80;

    }

    public class Colors {

        public static final Color TEXT_HIGHLIGHT = UIManager.getColor("textHighlight");

        public static final Color BACKGROUND = UIManager.getColor("Label.background");
    }

    public class Fonts {

        public static final Font SMALL_PLAIN = new Font("Arial", Font.PLAIN, 12);

        public static final Font SMALL_BOLD = new Font("Arial", Font.BOLD, 12);

        public static final Font NORMAL_PLAIN = new Font("Arial", Font.PLAIN, 13);

        public static final Font BIG_BOLD = new Font("Arial", Font.BOLD, 16);

        public static final Font HUGE_BOLD = new Font("Arial", Font.BOLD, 25);

    }

}
