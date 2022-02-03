package load.resource;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip clip;
	private URL soundURL[] = new URL[30];

	public Sound() {
		soundURL[0] = getClass().getResource("/sound/BGsong.wav");
		soundURL[1] = getClass().getResource("/sound/button.wav");
		soundURL[2] = getClass().getResource("/sound/car.wav");
		soundURL[3] = getClass().getResource("/sound/fual.wav");
		soundURL[4] = getClass().getResource("/sound/pick.wav");
		soundURL[5] = getClass().getResource("/sound/store.wav");
	}

	public void setFile(int number) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[number]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {

		}
	}

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public URL[] getSoundURL() {
		return soundURL;
	}

	public void setSoundURL(URL[] soundURL) {
		this.soundURL = soundURL;
	}

}
