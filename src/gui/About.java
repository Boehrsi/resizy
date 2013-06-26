package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import types.Config;
import types.Language;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class About {

	private JFrame frmResizyAbout;
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
			public void run() {
				try {
					About window = new About();
					window.frmResizyAbout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public About() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmResizyAbout = new JFrame();
		frmResizyAbout.setType(Type.POPUP);
		frmResizyAbout.setTitle(l.getProg() + " - " + l.getAbout());
		frmResizyAbout.setIconImage(Toolkit.getDefaultToolkit().getImage(
				About.class.getResource("/gui/icon.png")));
		frmResizyAbout.setResizable(false);
		frmResizyAbout.setBounds(100, 100, 500, 285);
		frmResizyAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmResizyAbout.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmResizyAbout.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText(l.getVersion());
		textPane_2.setOpaque(false);
		textPane_2.setFont(new Font("Arial", Font.BOLD, 12));
		textPane_2.setEditable(false);
		textPane_2.setBackground(SystemColor.menu);
		panel.add(textPane_2, "2, 3, fill, top");

		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText(c.getVersion());
		textPane_3.setOpaque(false);
		textPane_3.setFont(new Font("Arial", Font.PLAIN, 12));
		textPane_3.setEditable(false);
		textPane_3.setBackground(SystemColor.menu);
		panel.add(textPane_3, "4, 3, fill, top");

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(l.getUpdate());
		textPane_1.setOpaque(false);
		textPane_1.setFont(new Font("Arial", Font.BOLD, 12));
		textPane_1.setEditable(false);
		textPane_1.setBackground(SystemColor.menu);
		panel.add(textPane_1, "2, 5, fill, top");

		JButton btnNewButton = new JButton(l.getGetlatest());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop()
							.browse(new URI(
									"https://drive.google.com/folderview?id=0B6fLujPPaJuuTGJvMEQ3SnNOWDQ&usp=sharing"));
				} catch (URISyntaxException | IOException ex) {
				}
			}
		});
		panel.add(btnNewButton, "4, 5, fill, center");

		JPanel panel_1 = new JPanel();
		frmResizyAbout.getContentPane().add(panel_1, BorderLayout.NORTH);

		JTextPane txtpnResizy = new JTextPane();
		txtpnResizy.setText(l.getProg());
		txtpnResizy.setOpaque(false);
		txtpnResizy.setFont(new Font("Arial", Font.BOLD, 25));
		txtpnResizy.setEditable(false);
		txtpnResizy.setBackground(SystemColor.menu);
		panel_1.add(txtpnResizy);

		JPanel panel_2 = new JPanel();
		frmResizyAbout.getContentPane().add(panel_2, BorderLayout.WEST);

		BufferedImage aboutImg;
		try {
			aboutImg = ImageIO.read(About.class.getResource("/gui/icon.png"));
			JLabel lblNewLabel = new JLabel(new ImageIcon(
					aboutImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			panel_2.setBorder(new EmptyBorder(10, 10, 10, 10));
			panel_2.add(lblNewLabel);

			JPanel panel_3 = new JPanel();
			frmResizyAbout.getContentPane().add(panel_3, BorderLayout.SOUTH);

			JTextPane txtpnResizyGoogle = new JTextPane();
			txtpnResizyGoogle.setForeground(new Color(27, 133, 165));
			txtpnResizyGoogle.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						Desktop.getDesktop().browse(
								new URI("https://code.google.com/p/resizy/"));
					} catch (URISyntaxException | IOException ex) {
					}
				}
			});
			txtpnResizyGoogle.setText(l.getProg() + " " + l.getAt()
					+ " Google Code");
			txtpnResizyGoogle.setOpaque(false);
			txtpnResizyGoogle.setFont(new Font("Arial", Font.PLAIN, 12));
			txtpnResizyGoogle.setEditable(false);
			txtpnResizyGoogle.setBackground(SystemColor.menu);
			panel_3.add(txtpnResizyGoogle);

			JTextPane textPane = new JTextPane();
			textPane.setText(l.getProg() + " " + l.getAt() + " www.Boehrsi.de");
			textPane.setOpaque(false);
			textPane.setForeground(new Color(27, 133, 165));
			textPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						Desktop.getDesktop()
								.browse(new URI(
										"http://boehrsi.de/index.php?s=Resizy&notoppd=true"));
					} catch (URISyntaxException | IOException ex) {
					}
				}
			});
			textPane.setFont(new Font("Arial", Font.PLAIN, 12));
			textPane.setEditable(false);
			textPane.setBackground(SystemColor.menu);
			panel_3.add(textPane);
		} catch (IOException e) {
		}
	}

}
