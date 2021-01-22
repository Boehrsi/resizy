# Status

This repository is now archived and Resizy won't be developed any further, as I'm currently switching from Java to Kotlin and Flutter / Dart.
I'm considering a modern Flutter / Dart rewrite, but there is currently no timeline or guarantee it will be developed at all.
If you are interested in a Resizy rewrite please contact me.

# Short description
Resizy is a small image resize tool featuring the following functions:
  * Simple GUI
  * Multithread support
  * Drag and Drop support
  * Edit multiple files and rename them
  * Set only width or height and the other value is calculated automatically
  * Save often used width and height settings as presetting
  * Use german or english language or easily add your own language (see instructions below)

# Downloads
 * [Aktueller Release](https://github.com/Boehrsi/resizy/releases/latest)
 * [Alle Releases](https://github.com/Boehrsi/resizy/releases)

# Screenshots
![https://github.com/Boehrsi/resizy/blob/master/assets/resizy_ui.png](https://github.com/Boehrsi/resizy/blob/master/assets/resizy_ui.png)

# Localization

  * To add your own language open [language_en.properties](https://github.com/Boehrsi/resizy/blob/master/src/languages/language_en.properties) and create a copy of that file named language_**YOUR_LANGUAGE**.properties (two-letter language code is required [List of ISO 639-1 codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)). 
  * Translate the content (right side of the equal sign) within the file into the wanted language. 
  * Convert the file to ISO-8859-1 encoding by using the [native2ascii](https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/native2ascii.html) tool
  * Place it inside [src/languages](https://github.com/Boehrsi/resizy/tree/master/src/languages)
  * Adjust the [Language.java](https://github.com/Boehrsi/resizy/blob/master/src/languages/Language.java#L64) file to add the language to the menu
  * If you add a new localization feel free to submit it to the project here on GitHub to help other users. Would be much appreciated.
