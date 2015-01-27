package ghostsimulator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import sun.audio.AudioStream;

public class AudioLoader {

	public static AudioStream getSound(String name) throws URISyntaxException,
			IOException {
		URL url = ImageLoader.class.getClassLoader().getResource(
				"resources/" + name);
		FileInputStream stream = new FileInputStream(new File(url.toURI()));
		return new AudioStream(stream);
	}
}
