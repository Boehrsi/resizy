package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import core.Config;
import core.Language;
import utilities.ConstantUtility;

public class Help {
	private JFrame frmResizyHelp;
	private Config c = new Config();
	private Language l = new Language(c.getLang());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
					Help window = new Help();
					window.frmResizyHelp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Help() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String initialString = "<html>\n <ul>\n" + "<li><strong>" + l.getInf() + "</strong><br>" + l.getHf() + "</li>\n"
				+ "<li><strong>" + l.getOutf() + "</strong><br>" + l.getHof() + "</li>\n" + "<li><strong>"
				+ l.getFiletype() + "</strong><br>" + l.getHintfiletypes() + "</li>\n" + "<li><strong>" + l.getOutm()
				+ "</strong><br>" + l.getHom() + "</li>\n" + "<li><strong>" + l.getOutmeta() + "</strong><br>"
				+ l.getHoutmeta() + "</li>\n" + "<li><strong>" + l.getSize() + "</strong><br>" + l.getHs() + "</li>\n"
				+ "<li><strong>" + l.getPre() + "</strong><br>" + l.getHp() + "</li>\n"
				+ "<li><strong>" + l.getUseMultithreading() + "</strong><br>" + l.getHintmultithread() + "</li>\n" + "</ul></html>";

		frmResizyHelp = new JFrame();
		frmResizyHelp.setType(Type.POPUP);
		frmResizyHelp.setTitle(l.getProg() + " - " + l.getHelp());
		frmResizyHelp.setIconImage(Toolkit.getDefaultToolkit().getImage(Help.class.getResource("/gui/icon.png")));
		frmResizyHelp.setResizable(false);
		frmResizyHelp.setPreferredSize(new Dimension(ConstantUtility.HELP_WIDTH, ConstantUtility.HELP_HEIGHT));
		frmResizyHelp.pack();
		frmResizyHelp.setLocationRelativeTo(null);
		frmResizyHelp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frmResizyHelp.getContentPane().setLayout(new BorderLayout(0, 0));

		JTextPane helpText = new JTextPane();
		helpText.setEditable(false);
		helpText.setFont(new Font("Arial", Font.PLAIN, 12));
		helpText.setContentType("text/html");
		helpText.setBackground(SystemColor.control);
		helpText.setText(initialString);
		frmResizyHelp.getContentPane().add(helpText, BorderLayout.CENTER);
	}

}
