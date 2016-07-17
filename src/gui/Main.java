package gui;

import static utilities.ConstantUtility.EMPTY;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import core.BaseImageResizer;
import core.Config;
import core.FileFilter;
import core.ImageResizer;
import core.Language;
import core.MultiThreadImageResizer;
import interfaces.UiSynchronization;
import utilities.ConstantUtility;
import utilities.ImageUtility;
import utilities.ListUtility;
import utilities.UiUtility;

public class Main implements UiSynchronization {

	private class ConvertAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ConvertAction() {
			putValue(NAME, "Convert");
			putValue(SHORT_DESCRIPTION, lang.getS());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((outputPathTextfield.getText().equals(EMPTY) || outputTextfield.getText().equals(EMPTY))
					&& config.getOverwrite().equals(ConstantUtility.OVERWRITE_FILES_FALSE)) {
				JPanel panel = new JPanel();
				JTextPane textpane = new JTextPane();
				textpane.setEditable(false);
				textpane.setOpaque(false);
				textpane.setText(lang.getOverwritetext());
				textpane.setPreferredSize(dialogDimension);
				panel.add(textpane);
				panel.setPreferredSize(dialogDimension);
				int answer = JOptionPane.showConfirmDialog(null, panel, lang.getOverwritetitle(),
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					config.setOverwrite("0");
					execResize();
				}
			} else {
				execResize();
			}
		}
	}

	private class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveAction() {
			putValue(NAME, "Save Preset");
			putValue(SHORT_DESCRIPTION, lang.getHss());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String tempInsert = widthTextfield.getText() + "x" + heightTextfield.getText();
			if (presetSizesInputModel.getIndexOf(tempInsert) == -1) {
				presetSizesInputModel.addElement(tempInsert);
				String newPreset = EMPTY;
				for (int i = 0; i < presetSizesInputModel.getSize(); i++) {
					newPreset += presetSizesInputModel.getElementAt(i) + ",";
				}
				config.setPreset(newPreset);
			}
		}
	}

	private Config config = new Config();
	private Language lang = new Language(config.getLang());

	private final Action startConversion = new ConvertAction();
	private final Action savePreset = new SaveAction();

	private Dimension dialogDimension = new Dimension(400, 80);
	private Dimension mainWindowDimension = new Dimension(700, 550);
	private JFrame mainFrame;
	private JTextField outputPathTextfield;
	private final JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JTextField widthTextfield;;
	private JTextField heightTextfield;
	private DefaultComboBoxModel<String> presetSizesInputModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> presetTypesModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> presetSizesCombobox;
	private JComboBox<String> fileTypesModel;
	private JTextField outputTextfield;
	private JTextPane inputFilesTextpane;
	private JTextPane outputFilesTextpane;
	private JPanel sizePanel;
	private JTextPane heightTextpane;
	private JTextPane widthTextpane;
	private JTextPane presetSizesTextpane;
	private JTextPane outputStringTextpane;
	private JList<String> inputFileList;
	private DefaultListModel<String> inputFileModel;
	private JScrollPane inputFileScrollpane;
	private JTextPane sizeTextpane;
	private JTextPane progressTextpane;
	private JProgressBar progressbar;
	private JMenuBar menubar;
	private JMenu fileMenu;
	private JButton savePresetButton;
	private JMenu languageMenu;
	private JMenu configMenu;
	private JMenuItem openMenuitem;
	private JMenuItem resetMenuitem;
	private JMenuItem closeMenuitem;
	private JMenu helpMenu;
	private JMenuItem aboutMenuitem;
	private JMenuItem helpMenuitem;
	private JTextPane fileTypesTextpane;
	private JTextPane metaSaveTextpane;
	private JCheckBox metaSaveCheckbox;
	private JTextPane outputLabelTextpane;
	private JTextPane inputLabelTextpane;
	private JButton outputButton;
	private JButton inputButton;
	private JButton convertButton = new JButton(EMPTY);
	private JCheckBox multiThreadCheckbox;

	public static void main(final String[] args) {
		try {
			ToolTipManager.sharedInstance().setDismissDelay(10000);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		System.setProperty("file.encoding", "UTF-8");
		inputFileModel = new DefaultListModel<String>();
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/gui/icon.png")));
		mainFrame.setTitle(lang.getProg() + " - v" + config.getVersion());
		mainFrame.setPreferredSize(mainWindowDimension);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		centerPanel.setBackground(UIManager.getColor("Label.background"));
		mainFrame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("200px"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("140px:grow"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC, RowSpec.decode("20px"),
						FormSpecs.RELATED_GAP_ROWSPEC, }));

		inputLabelTextpane = new JTextPane();
		inputLabelTextpane.setForeground(UIManager.getColor("textHighlight"));
		inputLabelTextpane.setEditable(false);
		inputLabelTextpane.setText(lang.getInputLabel());
		inputLabelTextpane.setOpaque(false);
		inputLabelTextpane.setFont(new Font("Arial", Font.BOLD, 14));
		inputLabelTextpane.setBackground(SystemColor.menu);
		centerPanel.add(inputLabelTextpane, "2, 2, center, top");

		inputButton = new JButton(lang.getInputButton());
		inputButton.setToolTipText(lang.getHinputButton());
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputFileModel.removeAllElements();
				inputFileList.setModel(inputFileModel);
				UiUtility.alterPane(inputFilesTextpane, lang.getInf() + " (" + inputFileModel.getSize() + ")");
			}
		});
		centerPanel.add(inputButton, "4, 2, center, center");

		inputFilesTextpane = new JTextPane();
		inputFilesTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		inputFilesTextpane.setOpaque(false);
		centerPanel.add(inputFilesTextpane, "2, 4, left, center");
		inputFilesTextpane.setBackground(UIManager.getColor("Label.background"));
		inputFilesTextpane.setEditable(false);
		UiUtility.alterPane(inputFilesTextpane, lang.getInf() + " (" + inputFileModel.getSize() + ")");

		inputFileScrollpane = new JScrollPane();
		centerPanel.add(inputFileScrollpane, "4, 4, fill, fill");

		inputFileList = new JList<String>();
		inputFileList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_DELETE) {
					int[] toDelete = inputFileList.getSelectedIndices();
					for (int i = toDelete.length - 1; i >= 0; i--) {
						inputFileModel.removeElement(inputFileModel.elementAt(toDelete[i]));
					}
					inputFileList.setModel(inputFileModel);
				}
			}
		});
		inputFileList.setToolTipText(lang.getHf());
		inputFileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (SwingUtilities.isRightMouseButton(evt)) {
					JFileChooser chooser = new JFileChooser();
					chooser.setMultiSelectionEnabled(true);
					chooser.setDialogType(JFileChooser.OPEN_DIALOG);

					int status = chooser.showDialog(mainFrame, null);

					if (status == JFileChooser.APPROVE_OPTION) {
						File[] selectedFiles = chooser.getSelectedFiles();
						FileFilter rightFilter = new FileFilter(inputFileModel, inputFileList);
						for (int i = 0; i < selectedFiles.length; i++) {
							rightFilter.filterImage(selectedFiles[i]);
						}
					} else if (status == JFileChooser.CANCEL_OPTION) {
					}
				} else {
					try {
						if (evt.getClickCount() == 2) {
							@SuppressWarnings("unchecked")
							JList<String> list = (JList<String>) evt.getSource();
							int index = list.locationToIndex(evt.getPoint());
							inputFileModel.removeElement(inputFileModel.elementAt(index));
							inputFileList.setModel(inputFileModel);
						}
					} catch (ArrayIndexOutOfBoundsException aioobe) {

					}
				}
				UiUtility.alterPane(inputFilesTextpane, lang.getInf() + " (" + inputFileModel.getSize() + ")");
			}
		});
		inputFileList.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			@Override
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					FileFilter dropFilter = new FileFilter(inputFileModel, inputFileList);
					for (File file : droppedFiles) {
						dropFilter.filterImage(file);
					}
					UiUtility.alterPane(inputFilesTextpane, lang.getInf() + " (" + inputFileModel.getSize() + ")");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		inputFileScrollpane.setViewportView(inputFileList);

		outputPathTextfield = new JTextField();
		outputPathTextfield.setToolTipText(lang.getHof());
		outputPathTextfield.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);

				int status = chooser.showDialog(mainFrame, null);

				if (status == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					try {
						outputPathTextfield
								.setText(selectedFile.getParent().trim() + File.separator + selectedFile.getName());
					} catch (NullPointerException npe) {
						outputPathTextfield.setText(selectedFile.getName());
					}
				}
			}
		});

		outputLabelTextpane = new JTextPane();
		outputLabelTextpane.setForeground(UIManager.getColor("textHighlight"));
		outputLabelTextpane.setText(lang.getOutputLabel());
		outputLabelTextpane.setOpaque(false);
		outputLabelTextpane.setFont(new Font("Arial", Font.BOLD, 14));
		outputLabelTextpane.setEditable(false);
		outputLabelTextpane.setBackground(SystemColor.menu);
		centerPanel.add(outputLabelTextpane, "2, 8, center, top");

		outputButton = new JButton(lang.getOutputButton());
		outputButton.setToolTipText(lang.getHoutputButton());
		outputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputPathTextfield.setText(EMPTY);
				fileTypesModel.setSelectedIndex(0);
				outputTextfield.setText(EMPTY);
				metaSaveCheckbox.setSelected(false);
				presetSizesCombobox.setSelectedIndex(0);
			}
		});
		centerPanel.add(outputButton, "4, 8, center, center");

		outputFilesTextpane = new JTextPane();
		outputFilesTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		outputFilesTextpane.setOpaque(false);
		outputFilesTextpane.setOpaque(false);
		outputFilesTextpane.setBackground(UIManager.getColor("Label.background"));
		outputFilesTextpane.setEditable(false);
		outputFilesTextpane.setText(lang.getOutf());
		centerPanel.add(outputFilesTextpane, "2, 10, left, top");
		centerPanel.add(outputPathTextfield, "4, 10, fill, fill");

		fileTypesTextpane = new JTextPane();
		fileTypesTextpane.setText(lang.getFiletype());
		fileTypesTextpane.setOpaque(false);
		fileTypesTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		fileTypesTextpane.setEditable(false);
		fileTypesTextpane.setBackground(UIManager.getColor("Label.background"));
		centerPanel.add(fileTypesTextpane, "2, 12, left, top");

		fileTypesModel = new JComboBox<String>();
		fileTypesModel.setToolTipText(lang.getHintfiletypes());
		centerPanel.add(fileTypesModel, "4, 12, fill, center");

		outputStringTextpane = new JTextPane();
		outputStringTextpane.setOpaque(false);
		outputStringTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		outputStringTextpane.setBackground(UIManager.getColor("Label.background"));
		outputStringTextpane.setEditable(false);
		outputStringTextpane.setText(lang.getOutm());
		centerPanel.add(outputStringTextpane, "2, 14, left, top");

		outputTextfield = new JTextField();
		outputTextfield.setToolTipText(lang.getHom());
		centerPanel.add(outputTextfield, "4, 14, fill, fill");

		metaSaveTextpane = new JTextPane();
		metaSaveTextpane.setText((String) null);
		metaSaveTextpane.setOpaque(false);
		metaSaveTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		metaSaveTextpane.setEditable(false);
		metaSaveTextpane.setBackground(UIManager.getColor("Label.background"));
		metaSaveTextpane.setText(lang.getOutmeta());
		centerPanel.add(metaSaveTextpane, "2, 16, left, top");

		metaSaveCheckbox = new JCheckBox(EMPTY);
		metaSaveCheckbox.setToolTipText(lang.getHoutmeta());
		centerPanel.add(metaSaveCheckbox, "4, 16, left, center");

		sizeTextpane = new JTextPane();
		sizeTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		sizeTextpane.setOpaque(false);
		sizeTextpane.setBackground(UIManager.getColor("Label.background"));
		sizeTextpane.setText(lang.getSize());
		sizeTextpane.setEditable(false);
		centerPanel.add(sizeTextpane, "2, 18, left, center");

		sizePanel = new JPanel();
		centerPanel.add(sizePanel, "4, 18, fill, center");
		sizePanel.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.LINE_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LINE_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		presetSizesTextpane = new JTextPane();
		presetSizesTextpane.setBackground(UIManager.getColor("Label.background"));
		presetSizesTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		presetSizesTextpane.setOpaque(false);
		presetSizesTextpane.setEditable(false);
		presetSizesTextpane.setText(lang.getPre());
		sizePanel.add(presetSizesTextpane, "1, 1, fill, fill");

		widthTextpane = new JTextPane();
		widthTextpane.setBackground(UIManager.getColor("Label.background"));
		widthTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		widthTextpane.setOpaque(false);
		widthTextpane.setEditable(false);
		widthTextpane.setText(lang.getW());
		sizePanel.add(widthTextpane, "3, 1, fill, fill");

		heightTextpane = new JTextPane();
		heightTextpane.setBackground(UIManager.getColor("Label.background"));
		heightTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		heightTextpane.setOpaque(false);
		heightTextpane.setEditable(false);
		heightTextpane.setText(lang.getH());
		sizePanel.add(heightTextpane, "5, 1, fill, fill");

		presetSizesCombobox = new JComboBox<String>();
		presetSizesCombobox.setToolTipText(lang.getHp());
		presetSizesCombobox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_DELETE || key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					presetSizesInputModel.removeElementAt(presetSizesCombobox.getSelectedIndex());
					String newPreset = EMPTY;
					for (int i = 0; i < presetSizesInputModel.getSize(); i++) {
						newPreset += presetSizesInputModel.getElementAt(i) + ",";
					}
					config.setPreset(newPreset);
				}
			}
		});
		presetSizesCombobox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				String[] res = ((String) item.getItem()).split("x");
				widthTextfield.setText(res[0]);
				heightTextfield.setText(res[1]);
			}
		});
		sizePanel.add(presetSizesCombobox, "1, 3, fill, fill");

		widthTextfield = new JTextField();
		widthTextfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (widthTextfield.getText().equals(EMPTY)) {
					widthTextfield.setText("0");
				}
			}
		});
		widthTextfield.setToolTipText(lang.getHs());
		sizePanel.add(widthTextfield, "3, 3, fill, fill");
		widthTextfield.setColumns(10);

		heightTextfield = new JTextField();
		heightTextfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (heightTextfield.getText().equals(EMPTY)) {
					heightTextfield.setText("0");
				}
			}
		});
		heightTextfield.setToolTipText(lang.getHs());
		sizePanel.add(heightTextfield, "5, 3, fill, fill");
		heightTextfield.setColumns(10);
		mainFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
		mainFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		convertButton.setAction(startConversion);
		convertButton.setText(lang.getConvert());
		bottomPanel.add(convertButton);

		multiThreadCheckbox = new JCheckBox(lang.getUseMultithreading());
		multiThreadCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (multiThreadCheckbox.isSelected()) {
					config.setUseMultithreading("1");
				} else {
					config.setUseMultithreading("0");
				}
			}
		});
		boolean selected = config.getUseMultithreading().equals("1") ? true : false;
		multiThreadCheckbox.setSelected(selected);
		bottomPanel.add(multiThreadCheckbox);

		widthTextfield.setText(config.getWidth());
		heightTextfield.setText(config.getHeight());
		outputTextfield.setText(config.getOutputMod());
		String[] tempSize = config.getPreset().split(",");
		for (int i = 0; i < tempSize.length; i++) {
			presetSizesInputModel.addElement(tempSize[i]);
		}
		presetSizesCombobox.setModel(presetSizesInputModel);

		String[] tempTypes = config.getTypes().split(",");
		for (int i = 0; i < tempTypes.length; i++) {
			presetTypesModel.addElement(tempTypes[i]);
		}
		fileTypesModel.setModel(presetTypesModel);

		savePresetButton = new JButton(EMPTY);
		savePresetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		savePresetButton.setAction(savePreset);
		savePresetButton.setText(lang.getSavepreset());
		sizePanel.add(savePresetButton, "3, 5, fill, fill");

		progressTextpane = new JTextPane();
		progressTextpane.setText(lang.getProgress());
		progressTextpane.setOpaque(false);
		progressTextpane.setFont(new Font("Arial", Font.PLAIN, 12));
		progressTextpane.setEditable(false);
		progressTextpane.setBackground(SystemColor.menu);
		centerPanel.add(progressTextpane, "2, 22, fill, fill");

		progressbar = new JProgressBar();
		progressbar.setToolTipText(lang.getHpro());
		centerPanel.add(progressbar, "4, 22");

		menubar = new JMenuBar();
		mainFrame.setJMenuBar(menubar);

		fileMenu = new JMenu(lang.getFile());
		menubar.add(fileMenu);

		closeMenuitem = new JMenuItem(lang.getClose());
		closeMenuitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(closeMenuitem);

		languageMenu = new JMenu(lang.getLang());
		menubar.add(languageMenu);

		ArrayList<JMenuItem> langs = lang.langSelectable(config, mainFrame);
		for (int i = 0; i < langs.size(); i++) {
			languageMenu.add(langs.get(i));
		}

		configMenu = new JMenu(lang.getCfg());
		menubar.add(configMenu);

		openMenuitem = new JMenuItem(lang.getOpen());
		openMenuitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop desktop = Desktop.getDesktop();
					desktop.edit(new File("cfg/config.ini"));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		configMenu.add(openMenuitem);

		resetMenuitem = new JMenuItem(lang.getReset());
		resetMenuitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileChannel inChannel = null;
				FileChannel outChannel = null;
				FileInputStream inStream = null;
				FileOutputStream outStream = null;
				try {
					inStream = new FileInputStream(new File("cfg/config.ini.backup"));
					outStream = new FileOutputStream(new File("cfg/config.ini"));
					inChannel = inStream.getChannel();
					outChannel = outStream.getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				try {
					inChannel.transferTo(0, inChannel.size(), outChannel);
					inStream.close();
					outStream.close();
					inChannel.close();
					outChannel.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				JOptionPane.showMessageDialog(mainFrame, lang.getRestart(), lang.getRestarttitel(),
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		configMenu.add(resetMenuitem);

		helpMenu = new JMenu(lang.getHelp());
		menubar.add(helpMenu);

		helpMenuitem = new JMenuItem(lang.getProg() + " - " + lang.getHelp());
		helpMenuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.main(null);
			}
		});
		helpMenu.add(helpMenuitem);

		aboutMenuitem = new JMenuItem(lang.getAbout());
		aboutMenuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About.main(null);
			}
		});
		helpMenu.add(aboutMenuitem);
	}

	private void execResize() {
		if (inputFileModel.size() != 0) {
			if (ImageUtility.isGivenSizeValid(widthTextfield.getText())
					|| ImageUtility.isGivenSizeValid(heightTextfield.getText())) {
				progressbar.setMaximum(inputFileModel.size());
				progressbar.setValue(0);
				progressbar.setStringPainted(true);
				convertButton.setEnabled(false);

				int calcWidth = Integer.parseInt(widthTextfield.getText());
				int calcHeight = Integer.parseInt(heightTextfield.getText());
				String outputModifier = outputTextfield.getText();
				String outputPath = outputPathTextfield.getText();
				boolean saveMetaData = metaSaveCheckbox.isSelected();
				ArrayList<String> inputFileList = ListUtility.defaultModeltoArrayList(inputFileModel);
				String outputfileType = (String) fileTypesModel.getSelectedItem();

				BaseImageResizer resizer;
				if (multiThreadCheckbox.isSelected()) {
					resizer = new MultiThreadImageResizer();
				} else {
					resizer = new ImageResizer();
				}
				resizer.setup(config, lang, convertButton, this);
				resizer.resizeImageList(calcWidth, calcHeight, outputModifier, outputPath, inputFileList,
						outputfileType, saveMetaData);

				config.setHeight(heightTextfield.getText());
				config.setWidth(widthTextfield.getText());
				config.setOutputMod(outputModifier);
				config.setOutputDir(outputPath);
			} else {
				JOptionPane.showMessageDialog(null, lang.getErr1(), lang.getErr1t(), JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, lang.getErr2(), lang.getErr2t(), JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void updateProgress() {
		int progressbarValue = progressbar.getValue();
		progressbar.setValue(progressbarValue + 1);
		progressbar.setString(progressbarValue + 1 + " / " + inputFileModel.size());
	}

	@Override
	public void finishProgress() {
		int progressbarFinishValue = progressbar.getMaximum();
		progressbar.setValue(progressbarFinishValue);
		progressbar.setString(progressbarFinishValue + " / " + inputFileModel.size());
	}

}
