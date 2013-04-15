package game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * SoundEffect is an Enum type listing the different sound effects used by the system.
 * 
 *
 */
public enum SoundEffect {
	ALIEN_APPEAR("alien_appear.wav"),
	ALIEN_DIE("alien_die.wav"),
	ASTEROID_BREAK("asteroid_break.wav"),
	HYPERSPACE("hyperspace.wav"),
	SHIP_CRASH("explosion.wav"),
	FIRE_BULLET("fire_bullet.wav"),
	FIRE_BULLET_ALIEN("fire_bullet_alien.wav"),
	GAME_START("game_start.wav"),
	POWER_UP("power_up.wav");
	
	private static final String RESOURCE_PATH = "resources/sounds/";
	
	private Clip clip;
	/**
	 * Creates a new SoundEffect from an audio file
	 * @param filename the audio file name
	 */
	SoundEffect(String filename) {
		try {
			// Get URL to resource
			URL url = this.getClass().getClassLoader().getResource(RESOURCE_PATH + filename);
			
			// Set up audio stream for the resource
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			
			// Get a clip and load the audio
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Plays the Clip
	 */
	public void play() {
		// Stop clip if it is playing already
		if (clip.isRunning()) {
			clip.stop();
		}
		
		// Play from beginning
		clip.setFramePosition(0);
		clip.start();
	}
	/**
	 * Inidicates if the Clip is playing
	 * @return <tt>true</tt> if the clip is playing
	 */
	public boolean isPlaying() {
		return clip.isRunning();
	}
	/**
	 * Loads all sound files
	 */
	static void init() {
		// Load all sound files
		values();
	}
}
