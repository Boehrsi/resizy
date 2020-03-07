package main;

import about.About;
import config.Config;
import languages.Language;
import lombok.Getter;
import rename.Renamer;
import resize.BaseImageResizer;
import resize.ImageResizer;
import resize.MultiThreadImageResizer;
import utilities.ConstantUtility;
import utilities.ImageUtility;
import utilities.UiUtility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static config.Config.COPY_LAST_MODIFIED_DATE;
import static config.Config.HEIGHT;
import static config.Config.LANGUAGE;
import static config.Config.WIDTH;
import static config.Config.*;
import static languages.Language.OUTPUT_MODIFIER;
import static languages.Language.TARGET_DIRECTORY;
import static languages.Language.*;
import static utilities.ConstantUtility.Colors.BACKGROUND;
import static utilities.ConstantUtility.Fonts.*;
import static utilities.ConstantUtility.Paths.CONFIG;
import static utilities.ConstantUtility.Paths.CONFIG_BACKUP;
import static utilities.ConstantUtility.Strings.*;
import static utilities.ConstantUtility.Urls.GITHUB_BUG_FEATURE;
import static utilities.ImageUtility.OUTPUT_FILE_PATTERN;

/**
 * Main GUI class. Handles the main window of the program and delegates actions.
 *
 * @author Boehrsi
 * @version 1.0
 */

public class MainUi implements MainLogic.UiSynchronization {

    private static final Dimension DIALOG_DIMENSION = new Dimension(ConstantUtility.Size.DIALOG_WIDTH, ConstantUtility.Size.DIALOG_HEIGHT);

    private static final Dimension MAIN_WINDOW_DIMENSION = new Dimension(ConstantUtility.Size.MAIN_WIDTH, ConstantUtility.Size.MAIN_HEIGHT);

    private static final Border HELP_BORDER = BorderFactory.createLineBorder(Color.GRAY, 1);

    private static final String OVERWRITE_FILES_FALSE = "1";

    @Getter
    private JPanel container;
    private JLabel inputLabel;
    private JButton inputResetButton;
    private JLabel outputLabel;
    private JButton outputResetButton;
    private JLabel inputFilesLabel;
    private JLabel targetDirectoryLabel;
    private JTextField targetDirectoryField;
    private JLabel fileTypeLabel;
    private JComboBox<String> fileTypeSpinner;
    private JLabel nameModifierLabel;
    private JTextField nameModifierField;
    private JLabel copyLastModifiedLabel;
    private JCheckBox copyLastModifiedCheckBox;
    private JLabel sizePresetLabel;
    private JComboBox<String> sizePresetSpinner;
    private JLabel sizeLabel;
    private JTextField widthField;
    private JTextField heightField;
    private JButton sizePresetAddButton;
    private JTextPane sizeHelpText;
    private JLabel progressLabel;
    private JProgressBar progressBar;
    private JButton convertButton;
    private JCheckBox multiThreadingCheckBox;
    @SuppressWarnings("unused")
    private JPanel convertContainer;
    @SuppressWarnings("unused")
    private JPanel sizeContainer;
    private JList<String> inputFilesList;
    private JScrollPane inputFilesScrollPane;
    private JLabel inputFilesHelp;
    private JLabel targetDirectoryHelp;
    private JLabel fileTypeHelp;
    private JLabel nameModifierHelp;
    private JLabel copyLastModifiedHelp;
    private JLabel sizePresetHelp;
    private JLabel sizeHelp;
    private JLabel multiThreadingHelp;
    private JButton renameButton;
    private JLabel renameLabel;
    private JButton renameResetButton;
    private JTextField renameTargetDirectoryField;
    private JLabel renameTargetDirectoryHelp;
    private JTextField renamePatternField;
    private JLabel renamePatternFieldHelp;
    private JLabel renamePatternLabel;
    private JLabel renameTargetDirectoryLabel;
    private JLabel executeLabel;
    private MainLogic logic;


    MainUi(JFrame frame) {
        setupLogic();
        Config config = logic.getConfig();
        Language language = logic.getLanguage();
        setupFrame(frame, language);
        setupMenu(frame, config, language);
        setupText(language);
        setupHelp(language);
        setupSizePanel();
        String inputFilesLabelText = language.get(INPUT_FILES);
        setupInputFilesList(inputFilesLabelText);
        setupInputResetButton(inputFilesLabelText);
        setupTargetDirectory();
        setupRenameTargetDirectory();
        setupOutputResetButton();
        setupPresets();
        setupMultiThreading();
        setupLastModified();
        setupRenameButton();
        setupRenameResetButton();
        setupConvertButton();
        setupConfigDefaultValues(config);
    }

    @Override
    public void updateProgress() {
        int progressbarValue = progressBar.getValue();
        progressBar.setValue(progressbarValue + 1);
        progressBar.setString(progressbarValue + 1 + " / " + logic.getInputFilesModelSize());
    }

    @Override
    public void finishProgress() {
        int progressbarFinishValue = progressBar.getMaximum();
        progressBar.setValue(progressbarFinishValue);
        progressBar.setString(progressbarFinishValue + " / " + logic.getInputFilesModelSize());
        enableActions(true);
    }

    private void enableActions(boolean enable) {
        convertButton.setEnabled(enable);
        renameButton.setEnabled(enable);
    }

    private void setupLogic() {
        logic = new MainLogic();
    }

    private void setupFrame(JFrame frame, Language language) {
        UiUtility.setLogo(frame);
        UiUtility.setTitle(frame, language.get(NAME), ConstantUtility.Version.CORE);
        frame.setPreferredSize(MAIN_WINDOW_DIMENSION);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setupMenu(JFrame frame, Config config, Language language) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu(language.get(FILE));
        menuBar.add(fileMenu);
        JMenuItem closeMenuItem = new JMenuItem(language.get(CLOSE));
        closeMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(closeMenuItem);

        JMenu languageMenu = new JMenu(language.get(LANGUAGE));
        menuBar.add(languageMenu);
        ArrayList<String> languagesToAdd = language.langSelectable();
        for (String languageToAdd : languagesToAdd) {
            addLanguageToMenu(languageToAdd, config, languageMenu, frame);
        }

        JMenu configMenu = new JMenu(language.get(Language.CONFIG));
        menuBar.add(configMenu);
        JMenuItem openMenuItem = new JMenuItem(language.get(OPEN));
        openMenuItem.addActionListener(e -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.edit(new File(CONFIG));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        configMenu.add(openMenuItem);
        JMenuItem resetMenuItem = new JMenuItem(language.get(RESET));
        resetMenuItem.addActionListener(e -> {
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            FileInputStream inStream = null;
            FileOutputStream outStream = null;
            try {
                inStream = new FileInputStream(new File(CONFIG_BACKUP));
                outStream = new FileOutputStream(new File(CONFIG));
                inChannel = inStream.getChannel();
                outChannel = outStream.getChannel();
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }

            try {
                if (inChannel != null) {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                }
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            JOptionPane.showMessageDialog(frame, language.get(RESTART), language.get(INFORMATION), JOptionPane.INFORMATION_MESSAGE);
        });
        configMenu.add(resetMenuItem);

        JMenu helpMenu = new JMenu(language.get(HELP));
        menuBar.add(helpMenu);
        JMenuItem bugMenuItem = new JMenuItem(language.get(BUG));
        bugMenuItem.addActionListener(event -> openGitHub(language));
        JMenuItem featureMenuItem = new JMenuItem(language.get(FEATURE));
        featureMenuItem.addActionListener(event -> openGitHub(language));
        JMenuItem aboutMenuItem = new JMenuItem(language.get(ABOUT));
        aboutMenuItem.addActionListener(arg0 -> new About(language));
        helpMenu.add(bugMenuItem);
        helpMenu.add(featureMenuItem);
        helpMenu.add(aboutMenuItem);
    }

    private void addLanguageToMenu(String languageString, Config config, JMenu languageMenu, JFrame frame) {
        Language language = logic.getLanguage();
        JMenuItem item = new JMenuItem(languageString);
        item.addActionListener(event -> {
            config.set(LANGUAGE, event.getActionCommand());
            JOptionPane.showMessageDialog(frame, language.get(RESTART), language.get(INFORMATION), JOptionPane.INFORMATION_MESSAGE);
        });
        languageMenu.add(item);
    }

    private void openGitHub(Language language) {
        try {
            Desktop.getDesktop()
                    .browse(new URI(GITHUB_BUG_FEATURE));
        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(null, language.getErrorText(4), language.getErrorTitle(4), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupText(Language language) {
        setHeader(inputLabel, language.get(INPUT));
        setLabel(inputFilesLabel, language.get(INPUT_FILES) + " (0)");
        setHeader(renameLabel, language.get(RENAME));
        setLabel(renameTargetDirectoryLabel, language.get(TARGET_DIRECTORY));
        setLabel(renamePatternLabel, language.get(OUTPUT_NAME_PATTERN));
        setHeader(outputLabel, language.get(CONVERT));
        setLabel(targetDirectoryLabel, language.get(TARGET_DIRECTORY));
        setLabel(fileTypeLabel, language.get(FILE_TYPE));
        setLabel(nameModifierLabel, language.get(OUTPUT_MODIFIER));
        setLabel(copyLastModifiedLabel, language.get(Language.COPY_LAST_MODIFIED_DATE));
        setLabel(sizePresetLabel, language.get(Config.PRESET));
        setLabel(sizeLabel, language.get(SIZE));
        setLabel(progressLabel, language.get(PROGRESS));
        inputResetButton.setText(language.get(CLEAR_INPUT));
        renameResetButton.setText(language.get(RESET_RENAME));
        outputResetButton.setText(language.get(RESET_OUTPUT));
        setHeader(executeLabel, language.get(EXECUTE));
        convertButton.setText(language.get(CONVERT));
        renameButton.setText(language.get(RENAME));
        multiThreadingCheckBox.setText(language.get(USE_MULTITHREADING));
    }

    private void setHeader(JLabel component, String text) {
        component.setFocusable(false);
        component.setForeground(ConstantUtility.Colors.TEXT_HIGHLIGHT);
        component.setOpaque(false);
        component.setFont(BIG_BOLD);
        component.setText(text);
    }

    private void setLabel(JLabel component, String text) {
        component.setFocusable(false);
        component.setFont(NORMAL_PLAIN);
        component.setText(text);
    }

    private void setupHelp(Language language) {
        setupHelpLabel(inputFilesHelp, language.get(HINT_INPUT_FILES));
        setupHelpLabel(targetDirectoryHelp, language.get(HINT_TARGET_DIRECTORY));
        setupHelpLabel(fileTypeHelp, language.get(HINT_FILE_TYPE));
        setupHelpLabel(nameModifierHelp, language.get(HINT_OUTPUT_MODIFIER));
        setupHelpLabel(copyLastModifiedHelp, language.get(HINT_COPY_LAST_MODIFIED_DATE));
        setupHelpLabel(sizePresetHelp, language.get(HINT_MANAGE_PRESET));
        setupHelpLabel(sizeHelp, language.get(HINT_SIZE));
        setupHelpLabel(multiThreadingHelp, language.get(HINT_USE_MULTITHREADING));
        setupHelpLabel(renameTargetDirectoryHelp, language.get(HINT_RENAME_TARGET_DIRECTORY));
        setupHelpLabel(renamePatternFieldHelp, language.get(HINT_RENAME_PATTERN));
    }

    private void setupHelpLabel(JLabel component, String text) {
        component.setBorder(HELP_BORDER);
        component.setToolTipText(text);
        component.setFont(SMALL_BOLD);
    }

    private void setupSizePanel() {
        Dimension sizeDimension = new Dimension(100, widthField.getPreferredSize().height);
        widthField.setPreferredSize(sizeDimension);
        heightField.setPreferredSize(sizeDimension);
        sizeHelpText.setFocusable(false);
        sizeHelpText.setFont(NORMAL_PLAIN);
        sizeHelpText.setBackground(BACKGROUND);
        sizeHelpText.setText("x");
        sizePresetAddButton.addActionListener(arg0 -> {
            boolean wasAdded = logic.addSizePreset(widthField.getText(), heightField.getText());
            if (!wasAdded) {
                Language language = logic.getLanguage();
                JOptionPane.showMessageDialog(null, language.get(PRESET_NOT_SAVED), language.get(INFORMATION), JOptionPane.INFORMATION_MESSAGE);
            } else {
                sizePresetSpinner.setModel(logic.getSizePresetModel());
            }
        });
    }

    private void setupInputFilesList(String inputFilesLabelText) {
        final DefaultListModel<String> inputFilesModel = logic.getInputFilesModel();
        inputFilesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if (key.getKeyCode() == KeyEvent.VK_DELETE) {
                    int[] toDelete = inputFilesList.getSelectedIndices();
                    for (int i = toDelete.length - 1; i >= 0; i--) {
                        inputFilesModel.removeElement(inputFilesModel.elementAt(toDelete[i]));
                    }
                    inputFilesList.setModel(inputFilesModel);
                }
            }
        });

        inputFilesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(true);
                    chooser.setDialogType(JFileChooser.OPEN_DIALOG);

                    int status = chooser.showDialog(container, null);

                    if (status == JFileChooser.APPROVE_OPTION) {
                        File[] selectedFiles = chooser.getSelectedFiles();
                        FileFilter rightFilter = new FileFilter(inputFilesModel, inputFilesList);
                        for (File selectedFile : selectedFiles) {
                            rightFilter.filterImage(selectedFile);
                        }
                    }
                } else {
                    try {
                        if (evt.getClickCount() == 2) {
                            @SuppressWarnings("unchecked")
                            JList<String> list = (JList<String>) evt.getSource();
                            int index = list.locationToIndex(evt.getPoint());
                            inputFilesModel.removeElement(inputFilesModel.elementAt(index));
                            inputFilesList.setModel(inputFilesModel);
                        }
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        exception.printStackTrace();
                    }
                }
                setInputFilesLabelText(inputFilesLabelText);
            }
        });
        inputFilesList.setDropTarget(new DropTarget() {
            private static final long serialVersionUID = 1L;

            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    java.util.List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    FileFilter dropFilter = new FileFilter(inputFilesModel, inputFilesList);
                    for (File file : droppedFiles) {
                        dropFilter.filterImage(file);
                    }
                    setInputFilesLabelText(inputFilesLabelText);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        inputFilesScrollPane.setViewportView(inputFilesList);
    }

    private void setInputFilesLabelText(String inputFilesLabelText) {
        inputFilesLabel.setText(inputFilesLabelText + " (" + logic.getInputFilesModel().getSize() + ")");
    }

    private void setupInputResetButton(String inputFilesLabelText) {
        inputResetButton.addActionListener(arg0 -> {
            logic.getInputFilesModel().removeAllElements();
            inputFilesList.setModel(logic.getInputFilesModel());
            setInputFilesLabelText(inputFilesLabelText);
        });
    }

    private void setupTargetDirectory() {
        setupDirectoryTextField(targetDirectoryField);
    }

    private void setupRenameTargetDirectory() {
        setupDirectoryTextField(renameTargetDirectoryField);
    }

    private void setupDirectoryTextField(JTextField directoryField) {
        directoryField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);

                int status = chooser.showDialog(container, null);

                if (status == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        directoryField.setText(selectedFile.getParent().trim() + File.separator + selectedFile.getName());
                    } catch (NullPointerException exception) {
                        directoryField.setText(selectedFile.getName());
                    }
                }
            }
        });
    }

    private void setupRenameResetButton() {
        renameResetButton.addActionListener(e -> {
            renameTargetDirectoryField.setText(EMPTY);
            renamePatternField.setText(FILE_PATTERN);
            logic.resetRenameSettings();
        });
    }

    private void setupOutputResetButton() {
        outputResetButton.addActionListener(e -> {
            targetDirectoryField.setText(EMPTY);
            nameModifierField.setText(FILENAME_MODIFIER);
            fileTypeSpinner.setSelectedIndex(0);
            copyLastModifiedCheckBox.setSelected(false);
            sizePresetSpinner.setSelectedIndex(0);
            widthField.setText("0");
            heightField.setText("0");
            logic.resetOutputSettings();
        });
    }

    private void setupPresets() {
        logic.loadPresets();
        sizePresetSpinner.setModel(logic.getSizePresetModel());
        sizePresetSpinner.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if (key.getKeyCode() == KeyEvent.VK_DELETE || key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    logic.removeSizePreset(sizePresetSpinner.getSelectedIndex());
                }
            }
        });
        sizePresetSpinner.addItemListener(item -> {
            String itemString = (String) item.getItem();
            if (!itemString.equals(logic.getLanguage().get(NO_PRESET_SIZE))) {
                widthField.setEnabled(false);
                heightField.setEnabled(false);
                sizePresetAddButton.setEnabled(false);
            } else {
                widthField.setEnabled(true);
                heightField.setEnabled(true);
                sizePresetAddButton.setEnabled(true);
            }
        });

        fileTypeSpinner.setModel(logic.getFileTypeModel());
        fileTypeSpinner.addItemListener(item -> logic.setFileTypePosition(fileTypeSpinner.getSelectedIndex()));
    }

    private void setupMultiThreading() {
        multiThreadingCheckBox.addActionListener(arg0 -> logic.setMultiThreading(multiThreadingCheckBox.isSelected()));
        boolean selected = logic.getMultiThreading();
        multiThreadingCheckBox.setSelected(selected);
    }

    private void setupLastModified() {
        copyLastModifiedCheckBox.addActionListener(arg0 -> logic.setCopyLastModifiedDate(copyLastModifiedCheckBox.isSelected()));
        boolean selected = logic.getMultiThreading();
        copyLastModifiedCheckBox.setSelected(selected);
    }

    private void setupRenameButton() {
        renameButton.addActionListener(e -> {
            Language language = logic.getLanguage();
            Config config = logic.getConfig();
            if (renameTargetDirectoryField.getText().isEmpty() && config.get(OVERWRITE_WARNING).equals(OVERWRITE_FILES_FALSE)) {
                int answer = createOverwriteWarningDialog(language, RENAME_OVERWRITE_TEXT);
                if (answer == JOptionPane.YES_OPTION) {
                    config.set(OVERWRITE_WARNING, 0);
                    execRename();
                }
            } else {
                execRename();
            }
        });
    }

    private int createOverwriteWarningDialog(Language language, String text) {
        JPanel panel = new JPanel();
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setText(language.get(text));
        textPane.setPreferredSize(DIALOG_DIMENSION);
        panel.add(textPane);
        panel.setPreferredSize(DIALOG_DIMENSION);
        return JOptionPane.showConfirmDialog(null, panel, language.get(OVERWRITE_TITLE), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private void execRename() {
        Language language = logic.getLanguage();
        Config config = logic.getConfig();
        DefaultListModel<String> inputFileModel = logic.getInputFilesModel();
        ArrayList<String> inputFileList = defaultModelToArrayList(inputFileModel);
        if (inputFileModel.size() == 0) {
            JOptionPane.showMessageDialog(null, language.getErrorText(2), language.getErrorTitle(2), JOptionPane.ERROR_MESSAGE);
            return;
        }
        Renamer renamer = new Renamer();
        renamer.setup(language, this);
        String outputPath = renameTargetDirectoryField.getText();
        String renamePattern = renamePatternField.getText();
        if (renamePattern.contains(OUTPUT_FILE_PATTERN)) {
            enableActions(false);
            renamer.renameImageList(inputFileList, renamePattern, outputPath);
            config.set(Config.RENAME_PATTERN, renamePattern);
            config.set(Config.RENAME_TARGET_DIRECTORY, outputPath);
        } else {
            JOptionPane.showMessageDialog(null, language.getErrorText(9), language.getErrorTitle(9), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupConvertButton() {
        convertButton.addActionListener(e -> {
            Config config = logic.getConfig();
            Language language = logic.getLanguage();
            if (nameModifierField.getText().isEmpty() && config.get(OVERWRITE_WARNING).equals(OVERWRITE_FILES_FALSE)) {
                int answer = createOverwriteWarningDialog(language, OVERWRITE_TEXT);
                if (answer == JOptionPane.YES_OPTION) {
                    config.set(OVERWRITE_WARNING, 0);
                    execResize();
                }
            } else {
                execResize();
            }
        });
    }

    private void execResize() {
        enableActions(false);
        Config config = logic.getConfig();
        Language language = logic.getLanguage();
        DefaultListModel<String> inputFileModel = logic.getInputFilesModel();
        if (inputFileModel.size() != 0) {
            String widthString = widthField.getText().isEmpty() ? "0" : widthField.getText();
            String heightString = heightField.getText().isEmpty() ? "0" : heightField.getText();
            boolean usePreset = false;
            String itemString = (String) sizePresetSpinner.getSelectedItem();
            if (itemString != null && !itemString.isEmpty() && !itemString.equals(logic.getLanguage().get(NO_PRESET_SIZE))) {
                usePreset = true;
            }

            if (ImageUtility.isGivenSizeValid(widthString) || ImageUtility.isGivenSizeValid(heightString) || usePreset) {
                progressBar.setMaximum(inputFileModel.size());
                progressBar.setValue(0);
                progressBar.setStringPainted(true);
                convertButton.setEnabled(false);

                int calcWidth;
                int calcHeight;
                if (usePreset) {
                    String[] res = itemString.split("x");
                    widthString = res[0];
                    heightString = res[1];
                    calcWidth = Integer.parseInt(widthString);
                    calcHeight = Integer.parseInt(heightString);
                } else {
                    calcWidth = Integer.parseInt(widthString);
                    calcHeight = Integer.parseInt(heightString);
                }


                String outputModifier = nameModifierField.getText();
                String outputPath = targetDirectoryField.getText();
                boolean saveMetaData = copyLastModifiedCheckBox.isSelected();
                ArrayList<String> inputFileList = defaultModelToArrayList(inputFileModel);
                String outputFileType = (String) fileTypeSpinner.getSelectedItem();

                BaseImageResizer resizer;
                if (multiThreadingCheckBox.isSelected()) {
                    resizer = new MultiThreadImageResizer();
                } else {
                    resizer = new ImageResizer();
                }
                resizer.setup(language, this);
                resizer.resizeImageList(calcWidth, calcHeight, outputModifier, outputPath, inputFileList,
                        outputFileType, saveMetaData);

                config.set(HEIGHT, heightString);
                config.set(WIDTH, widthString);
                config.set(OUTPUT_MODIFIER, outputModifier);
                config.set(Config.TARGET_DIRECTORY, outputPath);
            } else {
                JOptionPane.showMessageDialog(null, language.getErrorText(1), language.getErrorTitle(1), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, language.getErrorText(2), language.getErrorTitle(2), JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<String> defaultModelToArrayList(DefaultListModel<String> model) {
        return new ArrayList<>(Collections.list(model.elements()));
    }

    private void setupConfigDefaultValues(Config config) {
        widthField.setText(config.get(WIDTH));
        heightField.setText(config.get(HEIGHT));
        renamePatternField.setText(config.get(RENAME_PATTERN));
        nameModifierField.setText(config.get(Config.OUTPUT_MODIFIER));
        targetDirectoryField.setText(config.get(Config.TARGET_DIRECTORY));
        copyLastModifiedCheckBox.setSelected(config.getAsBoolean(COPY_LAST_MODIFIED_DATE));
        fileTypeSpinner.setSelectedIndex(config.getAsInt(FILE_TYPE_POSITION));
    }

}
