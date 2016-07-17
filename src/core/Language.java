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

import utilities.ConstantUtility;

/**
 * 
 * Internationalization handling base on the file in the lang folder.
 * 
 * @author Boehrsi
 * @version 1.0
 * 
 */

public class Language {
	private Properties configFile;
	private String file, close, lang, cfg, open, reset, inf, outf, outm, outmeta, size, pre, w, h, savepreset, convert,
			restart, restarttitel, progress, hf, hof, hom, hs, hss, hp, s, hpro, help, houtmeta, about, version, update,
			prog, at, getlatest, filetype, hintfiletypes, err1, err1t, err2, err2t, err3, err3t, overwritetitle,
			overwritetext, inputLabel, inputButton, hinputButton, outputLabel, outputButton, houtputButton,
			useMultithreading;

	FileInputStream in;

	public Language(String langFile) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getAbout() {
		return about;
	}

	public String getAt() {
		return at;
	}

	public String getCfg() {
		return cfg;
	}

	public String getClose() {
		return close;
	}

	public Properties getConfigFile() {
		return configFile;
	}

	public String getConvert() {
		return convert;
	}

	public String getErr1() {
		return err1;
	}

	public String getErr1t() {
		return err1t;
	}

	public String getErr2() {
		return err2;
	}

	public String getErr2t() {
		return err2t;
	}

	public String getErr3() {
		return err3;
	}

	public String getErr3t() {
		return err3t;
	}

	public String getFile() {
		return file;
	}

	public String getFiletype() {
		return filetype;
	}

	public String getGetlatest() {
		return getlatest;
	}

	public String getH() {
		return h;
	}

	public String getHelp() {
		return help;
	}

	public String getHf() {
		return hf;
	}

	public String getHinputButton() {
		return hinputButton;
	}

	public String getHintfiletypes() {
		return hintfiletypes;
	}

	public String getHof() {
		return hof;
	}

	public String getHom() {
		return hom;
	}

	public String getHoutmeta() {
		return houtmeta;
	}

	public String getHoutputButton() {
		return houtputButton;
	}

	public String getHp() {
		return hp;
	}

	public String getHpro() {
		return hpro;
	}

	public String getHs() {
		return hs;
	}

	public String getHss() {
		return hss;
	}

	public FileInputStream getIn() {
		return in;
	}

	public String getInf() {
		return inf;
	}

	public String getInputButton() {
		return inputButton;
	}

	public String getInputLabel() {
		return inputLabel;
	}

	public String getLang() {
		return lang;
	}

	public String getOpen() {
		return open;
	}

	public String getOutf() {
		return outf;
	}

	public String getOutm() {
		return outm;
	}

	public String getOutmeta() {
		return outmeta;
	}

	public String getOutputButton() {
		return outputButton;
	}

	public String getOutputLabel() {
		return outputLabel;
	}

	public String getOverwritetext() {
		return overwritetext;
	}

	public String getOverwritetitle() {
		return overwritetitle;
	}

	public String getPre() {
		return pre;
	}

	public String getProg() {
		return prog;
	}

	public String getProgress() {
		return progress;
	}

	public String getReset() {
		return reset;
	}

	public String getRestart() {
		return restart;
	}

	public String getRestarttitel() {
		return restarttitel;
	}

	public String getS() {
		return s;
	}

	public String getSavepreset() {
		return savepreset;
	}

	public String getSize() {
		return size;
	}

	public String getUpdate() {
		return update;
	}

	public String getUseMultithreading() {
		return useMultithreading;
	}

	public String getVersion() {
		return version;
	}

	public String getW() {
		return w;
	}

	/**
	 * Load language files form lang folder and add them to the GUI.
	 * 
	 * @param c
	 *            Config object
	 * @param frame
	 *            GUI frame object
	 * @return ArrayList<JMenuItem> with available languages
	 */
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
