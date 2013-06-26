package gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
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
import javax.swing.UnsupportedLookAndFeelException;

import types.Config;
import types.Language;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import core.FileFilter;
import core.ImageResize;

public class Main {

	private class ConvertAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ConvertAction() {
			putValue(NAME, "Convert");
			putValue(SHORT_DESCRIPTION, l.getS());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			progress.setMaximum(inputFilesModel.size());
			progress.setValue(0);
			progress.setStringPainted(true);
			btnConvert.setEnabled(false);
			Thread converThread = new Thread(new Runnable() {
				@Override
				public void run() {
					int calcWidth, calcHeight;
					try {
						calcWidth = Integer.parseInt(width.getText());
					} catch (NumberFormatException nfe) {
						calcWidth = 0;
					}
					try {
						calcHeight = Integer.parseInt(height.getText());
					} catch (NumberFormatException nfe) {
						calcHeight = 0;
					}
					String outputModifier = outputString.getText();
					String outputPathEcho = outputPath.getText();
					for (int i = 0; i < inputFilesModel.size(); i++) {
						String outputFile = inputFilesModel.elementAt(i);
						outputFile = outputPathEcho
								+ File.separator
								+ outputFile.substring(
										outputFile.lastIndexOf("\\") + 1,
										outputFile.lastIndexOf("."))
								+ outputModifier
								+ outputFile.substring(
										outputFile.lastIndexOf("."),
										outputFile.length());
						ImageResize.resizeImageWithHint(
								inputFilesModel.elementAt(i), calcWidth,
								calcHeight, outputFile);
						progress.setValue(i + 1);
						progress.setString(i + 1 + " / "
								+ inputFilesModel.size());
					}
					c.setHeight(height.getText());
					c.setWidth(width.getText());
					c.setOutputMod(outputString.getText());
					c.setOutputDir(outputPath.getText());
					btnConvert.setEnabled(true);
				}
			}, "Thread for convert");
			converThread.start();
		}
	}

	private class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveAction() {
			putValue(NAME, "Save Preset");
			putValue(SHORT_DESCRIPTION, l.getHss());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			presetSizesInput.addElement(width.getText() + "x"
					+ height.getText());
			String newPreset = "";
			for (int i = 0; i < presetSizesInput.getSize(); i++) {
				newPreset += presetSizesInput.getElementAt(i) + ",";
			}
			c.setPreset(newPreset);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			ToolTipManager.sharedInstance().setDismissDelay(10000);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frmRezisy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private JFrame frmRezisy;
	private JTextField outputPath;
	private final JPanel top = new JPanel();
	private JPanel center = new JPanel();
	private JPanel bottom = new JPanel();
	private JTextField width;;
	private JTextField height;
	private DefaultComboBoxModel<String> presetSizesInput = new DefaultComboBoxModel<String>();
	private JComboBox<String> presetSizes;
	private JTextField outputString;
	private JTextPane txtpnInputFiles;
	private JTextPane txtpnOutputFiles;
	private JPanel SizePanel;
	private JTextPane txtpnHeight;
	private JTextPane txtpnWidth;
	private JTextPane txtpnPresetSizes;
	private JTextPane txtpnOutputAppendString;
	private JList<String> inputFiles;
	private DefaultListModel<String> inputFilesModel;
	private JScrollPane inputFilesScroll;
	private JTextPane txtpnSize;
	private Config c = new Config();
	private Language l = new Language(c.getLang());
	private final Action startConversion = new ConvertAction();
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JButton btnPresetsave;
	private final Action savePreset = new SaveAction();
	private JMenu mnLanguage;
	private JMenu mnConfig;
	private JButton btnConvert = new JButton("");
	private JMenuItem mntmOpen;
	private JMenuItem mntmReset;
	private JMenuItem mntmClose;
	private JTextPane txtpnProgress;
	private JProgressBar progress;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;

	/**
	 * Create the application.
	 */
	public Main() {
		System.setProperty("file.encoding", "UTF-8");
		initialize();
		width.setText(c.getWidth());
		height.setText(c.getHeight());
		outputString.setText(c.getOutputMod());
		String[] tempSize = c.getPreset().split(",");
		for (int i = 0; i < tempSize.length; i++) {
			presetSizesInput.addElement(tempSize[i]);
		}
		presetSizes.setModel(presetSizesInput);

		btnPresetsave = new JButton("");
		btnPresetsave.setAction(savePreset);
		btnPresetsave.setText(l.getSavepreset());
		SizePanel.add(btnPresetsave, "3, 5, fill, fill");

		txtpnProgress = new JTextPane();
		txtpnProgress.setText(l.getProgress());
		txtpnProgress.setOpaque(false);
		txtpnProgress.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnProgress.setEditable(false);
		txtpnProgress.setBackground(SystemColor.menu);
		center.add(txtpnProgress, "2, 18, fill, fill");

		progress = new JProgressBar();
		progress.setToolTipText(l.getHpro());
		center.add(progress, "4, 18");

		menuBar = new JMenuBar();
		frmRezisy.setJMenuBar(menuBar);

		mnFile = new JMenu(l.getFile());
		menuBar.add(mnFile);

		mntmClose = new JMenuItem(l.getClose());
		mntmClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmClose);

		mnLanguage = new JMenu(l.getLang());
		menuBar.add(mnLanguage);

		ArrayList<JMenuItem> langs = l.langSelectable(c, frmRezisy);
		for (int i = 0; i < langs.size(); i++) {
			mnLanguage.add(langs.get(i));
		}

		mnConfig = new JMenu(l.getCfg());
		menuBar.add(mnConfig);

		mntmOpen = new JMenuItem(l.getOpen());
		mntmOpen.addActionListener(new ActionListener() {
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
		mnConfig.add(mntmOpen);

		mntmReset = new JMenuItem(l.getReset());
		mntmReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileChannel inChannel = null;
				FileChannel outChannel = null;
				try {
					inChannel = new FileInputStream(new File(
							"cfg/config.ini.backup")).getChannel();
					outChannel = new FileOutputStream(
							new File("cfg/config.ini")).getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				try {
					inChannel.transferTo(0, inChannel.size(), outChannel);
					inChannel.close();
					outChannel.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				JOptionPane.showMessageDialog(frmRezisy, l.getRestart(),
						l.getRestarttitel(), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnConfig.add(mntmReset);
		
		mnNewMenu = new JMenu(l.getHelp());
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem(l.getAbout());
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About.main(null);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		inputFilesModel = new DefaultListModel<String>();
		frmRezisy = new JFrame();
		frmRezisy.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Main.class.getResource("/gui/icon.png")));
		frmRezisy.setTitle(l.getProg());
		frmRezisy.setBounds(100, 100, 600, 500);
		frmRezisy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		center.setBackground(UIManager.getColor("Label.background"));
		frmRezisy.getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("150px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("130px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("20px"),
				RowSpec.decode("4dlu:grow"), }));

		txtpnInputFiles = new JTextPane();
		txtpnInputFiles.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnInputFiles.setOpaque(false);
		center.add(txtpnInputFiles, "2, 2, left, center");
		txtpnInputFiles.setBackground(UIManager.getColor("Label.background"));
		txtpnInputFiles.setEditable(false);
		txtpnInputFiles.setText(l.getInf());

		inputFilesScroll = new JScrollPane();
		center.add(inputFilesScroll, "4, 2, fill, fill");

		inputFiles = new JList<String>();
		inputFiles.setToolTipText(l.getHf());
		inputFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				if (SwingUtilities.isRightMouseButton(evt)) {
					JFileChooser chooser = new JFileChooser();
					chooser.setMultiSelectionEnabled(true);
					chooser.setDialogType(JFileChooser.OPEN_DIALOG);

					int status = chooser.showDialog(frmRezisy, null);

					if (status == JFileChooser.APPROVE_OPTION) {
						File[] selectedFiles = chooser.getSelectedFiles();
						FileFilter rightFilter = new FileFilter(
								inputFilesModel, inputFiles);
						for (int i = 0; i < selectedFiles.length; i++) {
							rightFilter.filterImage(selectedFiles[i]);
						}
					} else if (status == JFileChooser.CANCEL_OPTION) {
					}
				} else {
					try {
						if (evt.getClickCount() == 2) {
							@SuppressWarnings("unchecked")
							JList<String> list = (JList<String>) evt
									.getSource();
							int index = list.locationToIndex(evt.getPoint());
							inputFilesModel.removeElement(inputFilesModel
									.elementAt(index));
							inputFiles.setModel(inputFilesModel);
						}
					} catch (ArrayIndexOutOfBoundsException aioobe) {

					}
				}
			}
		});
		inputFiles.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			@Override
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
							.getTransferable().getTransferData(
									DataFlavor.javaFileListFlavor);
					FileFilter dropFilter = new FileFilter(inputFilesModel,
							inputFiles);
					for (File file : droppedFiles) {
						dropFilter.filterImage(file);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		inputFilesScroll.setViewportView(inputFiles);

		outputPath = new JTextField();
		outputPath.setToolTipText(l.getHof());
		outputPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);

				int status = chooser.showDialog(frmRezisy, null);

				if (status == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					try {
						outputPath.setText(selectedFile.getParent().trim()
								+ File.separator + selectedFile.getName());
					} catch (NullPointerException npe) {
						outputPath.setText(selectedFile.getName());
					}
				} else if (status == JFileChooser.CANCEL_OPTION) {

				}
			}
		});

		txtpnOutputFiles = new JTextPane();
		txtpnOutputFiles.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnOutputFiles.setOpaque(false);
		txtpnOutputFiles.setOpaque(false);
		txtpnOutputFiles.setBackground(UIManager.getColor("Label.background"));
		txtpnOutputFiles.setEditable(false);
		txtpnOutputFiles.setText(l.getOutf());
		center.add(txtpnOutputFiles, "2, 6, left, top");
		center.add(outputPath, "4, 6, fill, fill");

		txtpnOutputAppendString = new JTextPane();
		txtpnOutputAppendString.setOpaque(false);
		txtpnOutputAppendString.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnOutputAppendString.setBackground(UIManager
				.getColor("Label.background"));
		txtpnOutputAppendString.setEditable(false);
		txtpnOutputAppendString.setText(l.getOutm());
		center.add(txtpnOutputAppendString, "2, 10, left, top");

		outputString = new JTextField();
		outputString.setToolTipText(l.getHom());
		center.add(outputString, "4, 10, fill, fill");

		txtpnSize = new JTextPane();
		txtpnSize.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnSize.setOpaque(false);
		txtpnSize.setBackground(UIManager.getColor("Label.background"));
		txtpnSize.setText(l.getSize());
		txtpnSize.setEditable(false);
		center.add(txtpnSize, "2, 14, left, center");

		SizePanel = new JPanel();
		center.add(SizePanel, "4, 14, fill, top");
		SizePanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		txtpnPresetSizes = new JTextPane();
		txtpnPresetSizes.setBackground(UIManager.getColor("Label.background"));
		txtpnPresetSizes.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnPresetSizes.setOpaque(false);
		txtpnPresetSizes.setEditable(false);
		txtpnPresetSizes.setText(l.getPre());
		SizePanel.add(txtpnPresetSizes, "1, 1, fill, fill");

		txtpnWidth = new JTextPane();
		txtpnWidth.setBackground(UIManager.getColor("Label.background"));
		txtpnWidth.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnWidth.setOpaque(false);
		txtpnWidth.setEditable(false);
		txtpnWidth.setText(l.getW());
		SizePanel.add(txtpnWidth, "3, 1, fill, fill");

		txtpnHeight = new JTextPane();
		txtpnHeight.setBackground(UIManager.getColor("Label.background"));
		txtpnHeight.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnHeight.setOpaque(false);
		txtpnHeight.setEditable(false);
		txtpnHeight.setText(l.getH());
		SizePanel.add(txtpnHeight, "5, 1, fill, fill");

		presetSizes = new JComboBox<String>();
		presetSizes.setToolTipText(l.getHp());
		presetSizes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_DELETE
						|| key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					presetSizesInput.removeElementAt(presetSizes
							.getSelectedIndex());
					String newPreset = "";
					for (int i = 0; i < presetSizesInput.getSize(); i++) {
						newPreset += presetSizesInput.getElementAt(i) + ",";
					}
					c.setPreset(newPreset);
				}
			}
		});
		presetSizes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				String[] res = ((String) item.getItem()).split("x");
				width.setText(res[0]);
				height.setText(res[1]);
			}
		});
		SizePanel.add(presetSizes, "1, 3, fill, fill");

		width = new JTextField();
		width.setToolTipText(l.getHs());
		SizePanel.add(width, "3, 3, fill, fill");
		width.setColumns(10);

		height = new JTextField();
		height.setToolTipText(l.getHs());
		SizePanel.add(height, "5, 3, fill, fill");
		height.setColumns(10);
		frmRezisy.getContentPane().add(top, BorderLayout.NORTH);
		frmRezisy.getContentPane().add(bottom, BorderLayout.SOUTH);

		btnConvert.setAction(startConversion);
		btnConvert.setText(l.getConvert());
		bottom.add(btnConvert);
	}
}
