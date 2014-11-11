package ghostsimulator.util;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageLoader {

	static MediaTracker tracker = new MediaTracker(new Container());

	public static Image getImage(String name) {
		URL url = ImageLoader.class.getClassLoader().getResource("resources/" + name);
		Image img = Toolkit.getDefaultToolkit().createImage(url);
		ImageLoader.tracker.addImage(img, 1);
		try {
			ImageLoader.tracker.waitForID(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon(ImageLoader.getImage(name));
	}
	
	public static ImageIcon getScaledImageIcon(String name, int width, int height) {
		Image img = ImageLoader.getImage(name).getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

}
