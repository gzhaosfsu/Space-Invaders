package space_invaders.game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    private Clip clip;

    public MusicPlayer() {
        try {
            File file = new File("resources/song.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio file: " + e.getMessage());
        }
    }

    public void startMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0); // Restart the music from the beginning
            clip.start();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
