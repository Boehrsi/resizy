package about;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import languages.Language;
import utilities.ConstantUtility;
import utilities.UiUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static languages.Language.*;
import static utilities.ConstantUtility.Colors.TEXT_HIGHLIGHT;
import static utilities.ConstantUtility.Fonts.*;
import static utilities.ConstantUtility.Paths.PATH_LOGO;
import static utilities.ConstantUtility.Strings.GITHUB_PAGES;
import static utilities.ConstantUtility.Urls.*;

public class About {

    public About(Language language) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setupUi(language);
    }

    private void setupUi(Language language) {
        JFrame frame = UiUtility.getPopupFrame(ConstantUtility.Size.ABOUT_WIDTH, ConstantUtility.Size.ABOUT_HEIGHT);
        UiUtility.setLogo(frame);
        UiUtility.setTitle(frame, language.get(NAME), language.get(ABOUT));

        JPanel centerPanel = new JPanel();
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new FormLayout(
                new ColumnSpec[]{FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("35dlu:grow"),
                        FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
                        FormSpecs.RELATED_GAP_COLSPEC,},
                new RowSpec[]{RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC, FormSpecs.NARROW_LINE_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),}));
        setupCenterPanel(language, centerPanel);

        JPanel topPanel = new JPanel();
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        setupTopPanel(language, topPanel);

        JPanel westPanel = new JPanel();
        frame.getContentPane().add(westPanel, BorderLayout.WEST);
        setupWestPanel(language, frame, westPanel);
    }

    private void setupCenterPanel(Language language, JPanel centerPanel) {
        JTextPane versionPane = new JTextPane();
        versionPane.setText(language.get(VERSION));
        versionPane.setOpaque(false);
        versionPane.setFont(SMALL_BOLD);
        versionPane.setEditable(false);
        versionPane.setBackground(SystemColor.menu);
        centerPanel.add(versionPane, "2, 3, fill, top");

        JTextPane versionNumberPane = new JTextPane();
        versionNumberPane.setText(ConstantUtility.Version.CORE);
        versionNumberPane.setOpaque(false);
        versionNumberPane.setFont(SMALL_PLAIN);
        versionNumberPane.setEditable(false);
        versionNumberPane.setBackground(SystemColor.menu);
        centerPanel.add(versionNumberPane, "4, 3, fill, top");

        JTextPane updateCheckPane = new JTextPane();
        updateCheckPane.setText(language.get(CHECK_UPDATE));
        updateCheckPane.setOpaque(false);
        updateCheckPane.setFont(SMALL_BOLD);
        updateCheckPane.setEditable(false);
        updateCheckPane.setBackground(SystemColor.menu);
        centerPanel.add(updateCheckPane, "2, 5, fill, top");

        JButton updateButton = new JButton(language.get(UPDATE));
        updateButton.addActionListener(arg0 -> {
            try {
                Desktop.getDesktop()
                        .browse(new URI(GITHUB));
            } catch (URISyntaxException | IOException ex) {
                showErrorDialog(language);
            }
        });
        centerPanel.add(updateButton, "4, 5, fill, center");
    }

    private void setupTopPanel(Language language, JPanel top) {
        JTextPane header = new JTextPane();
        header.setText(language.get(NAME));
        header.setOpaque(false);
        header.setFont(HUGE_BOLD);
        header.setEditable(false);
        header.setBackground(SystemColor.menu);
        top.add(header);
    }

    private void setupWestPanel(Language language, JFrame frame, JPanel logoPanel) {
        BufferedImage aboutImageBuffered;
        try {
            File aboutImageFile = new File(PATH_LOGO);
            aboutImageBuffered = ImageIO.read(aboutImageFile);
            JLabel lblNewLabel = new JLabel(new ImageIcon(aboutImageBuffered.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            logoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            logoPanel.add(lblNewLabel);

            JPanel bottom = new JPanel();
            frame.getContentPane().add(bottom, BorderLayout.SOUTH);

            JTextPane githubIoPane = new JTextPane();
            githubIoPane.setForeground(TEXT_HIGHLIGHT);
            githubIoPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    try {
                        Desktop.getDesktop().browse(new URI(GITHUB_IO));
                    } catch (URISyntaxException | IOException ex) {
                        showErrorDialog(language);
                    }
                }
            });
            githubIoPane.setText(language.get(NAME) + " " + language.get(AT) + " " + GITHUB_PAGES);
            githubIoPane.setOpaque(false);
            githubIoPane.setFont(SMALL_PLAIN);
            githubIoPane.setEditable(false);
            githubIoPane.setBackground(SystemColor.menu);
            bottom.add(githubIoPane);

            JTextPane blogPane = new JTextPane();
            blogPane.setText(language.get(NAME) + " " + language.get(AT) + " " + ConstantUtility.Strings.BOEHRSI_DE);
            blogPane.setOpaque(false);
            blogPane.setForeground(TEXT_HIGHLIGHT);
            blogPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    try {
                        Desktop.getDesktop()
                                .browse(new URI(BOEHRSI_DE));
                    } catch (URISyntaxException | IOException ex) {
                        showErrorDialog(language);
                    }
                }
            });
            blogPane.setFont(SMALL_PLAIN);
            blogPane.setEditable(false);
            blogPane.setBackground(SystemColor.menu);
            bottom.add(blogPane);
            frame.setVisible(true);

        } catch (IOException ignore) {
        }
    }

    private void showErrorDialog(Language language) {
        JOptionPane.showMessageDialog(null, language.getErrorText(4), language.getErrorTitle(4), JOptionPane.ERROR_MESSAGE);
    }

}
