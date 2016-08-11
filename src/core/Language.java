package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import lombok.Data;
import utilities.ConstantUtility;

/**
 * 
 * Internationalization handling base on the file in the lang folder.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

@Data
public class Language {
	private Properties configFile;
	private String file, close, lang, cfg, open, reset, inf, outf, outm, outmeta, size, pre, w, h, savepreset, convert,
			restart, restarttitel, progress, hf, hof, hom, hs, hss, hp, s, hpro, help, houtmeta, about, version, update,
			prog, at, getlatest, filetype, hintfiletypes, err1, err1t, err2, err2t, err3, err3t, overwritetitle,
			overwritetext, inputLabel, inputButton, hinputButton, outputLabel, outputButton, houtputButton,
			useMultithreading, hintmultithread;

	public Language(String langFile) {
		FileInputStream in = null;
		try {
			in = new FileInputStream("lang" + File.separator + langFile + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		configFile = new Properties();
		try {
			configFile.load(in);
			in.close();
			file = configFile.getProperty("file");
			close = configFile.getProperty("close");
			lang = configFile.getProperty("lang");
			cfg = configFile.getProperty("cfg");
			open = configFile.getProperty("open");
			reset = configFile.getProperty("reset");
			inf = configFile.getProperty("inf");
			outf = configFile.getProperty("outf");
			outm = configFile.getProperty("outm");
			outmeta = configFile.getProperty("outmeta");
			houtmeta = configFile.getProperty("hintoutmeta");
			size = configFile.getProperty("size");
			pre = configFile.getProperty("pre");
			w = configFile.getProperty("w");
			h = configFile.getProperty("h");
			hf = configFile.getProperty("hintfiles");
			hof = configFile.getProperty("hintoutputfolder");
			hom = configFile.getProperty("hintoutputmod");
			hs = configFile.getProperty("hintsize");
			hss = configFile.getProperty("hintsizesave");
			hp = configFile.getProperty("hintpresets");
			hpro = configFile.getProperty("hintpro");
			progress = configFile.getProperty("progress");
			savepreset = configFile.getProperty("savepreset");
			convert = configFile.getProperty("convert");
			restart = configFile.getProperty("restart");
			restarttitel = configFile.getProperty("restarttitel");
			s = configFile.getProperty("do");
			help = configFile.getProperty("help");
			about = configFile.getProperty("about");
			filetype = configFile.getProperty("filetype");
			hintfiletypes = configFile.getProperty("hintfiletypes");
			version = configFile.getProperty("version");
			update = configFile.getProperty("update");
			prog = configFile.getProperty("prog");
			at = configFile.getProperty("at");
			getlatest = configFile.getProperty("getlatest");
			err1 = configFile.getProperty("err1");
			err1t = configFile.getProperty("err1t");
			err2 = configFile.getProperty("err2");
			err2t = configFile.getProperty("err2t");
			err3 = configFile.getProperty("err3");
			err3t = configFile.getProperty("err3t");
			overwritetitle = configFile.getProperty("overwritetitle");
			overwritetext = configFile.getProperty("overwritetext");
			inputLabel = configFile.getProperty("inputLabel");
			inputButton = configFile.getProperty("inputButton");
			outputLabel = configFile.getProperty("outputLabel");
			outputButton = configFile.getProperty("outputButton");
			hinputButton = configFile.getProperty("hinputButton");
			houtputButton = configFile.getProperty("houtputButton");
			useMultithreading = configFile.getProperty("useMultithreading");
			hintmultithread = configFile.getProperty("hintmultithread");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<JMenuItem> langSelectable(final Config c, final JFrame frame) {
		ArrayList<JMenuItem> items = new ArrayList<JMenuItem>();
		File folder = new File("lang");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				JMenuItem tempItem = new JMenuItem(file.getName().replace(".txt", ConstantUtility.EMPTY));
				tempItem.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(final ActionEvent e) {
						c.setLanguage(e.getActionCommand());
						JOptionPane.showMessageDialog(frame, getRestart(), getRestarttitel(),
								JOptionPane.INFORMATION_MESSAGE);
					}
				});
				items.add(tempItem);
			}
		}
		return items;
	}
}
