package types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Language {

	private Properties configFile;
	private String file, close, lang, cfg, open, reset, inf, outf, outm, size,
			pre, w, h, savepreset, convert, restart, restarttitel, progress;
	FileInputStream in;

	public Language(String langFile) {
		try {
			in = new FileInputStream("lang" + File.separator + langFile
					+ ".txt");
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
			size = configFile.getProperty("size");
			pre = configFile.getProperty("pre");
			w = configFile.getProperty("w");
			h = configFile.getProperty("h");
			progress = configFile.getProperty("progress");
			savepreset = configFile.getProperty("savepreset");
			convert = configFile.getProperty("convert");
			restart = configFile.getProperty("restart");
			restarttitel = configFile.getProperty("restarttitel");
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public String getFile() {
		return file;
	}

	public String getH() {
		return h;
	}

	public FileInputStream getIn() {
		return in;
	}

	public String getInf() {
		return inf;
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

	public String getPre() {
		return pre;
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

	public String getSavepreset() {
		return savepreset;
	}

	public String getSize() {
		return size;
	}

	public String getW() {
		return w;
	}

	public ArrayList<JMenuItem> langSelectable(JMenu mnLanguage,
			final Config c, final JFrame frame) {
		ArrayList<JMenuItem> items = new ArrayList<JMenuItem>();
		File folder = new File("lang");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				JMenuItem tempItem = new JMenuItem(file.getName().replace(
						".txt", ""));
				tempItem.addActionListener(new ActionListener() {
					@Override
					public final void actionPerformed(final ActionEvent e) {
						c.setLanguage(e.getActionCommand());
						JOptionPane.showMessageDialog(frame, getRestart(),
								getRestarttitel(),
								JOptionPane.INFORMATION_MESSAGE);
					}
				});
				items.add(tempItem);
			}
		}
		return items;
	}
}
