package utilities;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * MusicPlayer.java - This class is responsible for constantly playing the same sound track over and over and over.
 * The volume of the music can be decreased or increased as desired.
 * @author Iliya Kiritchkov
 *
 */
public class MusicPlayer {
	private static MusicPlayer musicPlayer;
	private MediaPlayer mediaPlayer;
	private Media loginMusic;
	
	/**
	 * Constructs a MusicPlayer object and loads in a musical file. Automatically starts playing music.
	 */
	private MusicPlayer() {
		loginMusic = new Media(new File("res/music/loginMID.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(loginMusic);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	/**
	 * Gets a static instance of the music player.
	 * @return musicPlayer single instance of the MusicPlayer.
	 */
	public static MusicPlayer getMusicPlayer() {
		if (musicPlayer == null) {
			musicPlayer = new MusicPlayer();
		}
		return musicPlayer;
	}
	
	/**
	 * Gets the current volume of the music player
	 * @return volume of the music player
	 */
	public double getVolume() {
		return mediaPlayer.getVolume();
	}
	
	/**
	 * Sets the volume of the music player.
	 * @param vol volume of the music player.
	 */
	public void setVolume(double vol) {
		mediaPlayer.setVolume(vol);
	}
}