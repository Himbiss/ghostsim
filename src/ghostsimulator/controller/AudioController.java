package ghostsimulator.controller;

import ghostsimulator.util.AudioLoader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * This class handles all audio and sound
 * @author vincent
 */
public class AudioController {

	/**
	 * Plays an error sound
	 */
	public static void playErrorSound() {
		try {
			AudioStream audioStream = AudioLoader.getSound("error.wav");
			AudioPlayer.player.start(audioStream);
		} catch (Exception exc) {
		}
	}
}
