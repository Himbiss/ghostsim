package ghostsimulator.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import org.xml.sax.InputSource;

public class Resources {

	static Properties prop;
	static Locale locale;
	static ResourceBundle bundle;

	static {
		Resources.prop = new Properties();
		try {
			InputStream stream = Resources.class.getClassLoader().getResourceAsStream("resources/prop/ghostsim.properties");
			Resources.prop.load(stream);
			String language = Resources.prop.getProperty("language");
			if (language == null) {
				Resources.locale = Locale.getDefault();
			} else {
				Resources.locale = new Locale(language);
			}
		} catch (Throwable e) {
			Resources.locale = Locale.getDefault();
		}
		Locale.setDefault(Resources.locale);
		JComponent.setDefaultLocale(Resources.locale);
		Resources.bundle = ResourceBundle.getBundle(
				"resources.prop.language",
				Resources.locale);
	}
	
	public static String getValue(String key) {
		return Resources.bundle.getString(key);
	}
	
	public static char getMnemonic(String key) {
		return Resources.bundle.getString(key).charAt(0);
	}
	
	public static String getSystemProperty(String key) {
		return Resources.prop.getProperty(key);
	}
}
