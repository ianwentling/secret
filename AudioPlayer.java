import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer implements AudioPlayerInterface {

    private Clip clip;
    private Long currentFrame;
    private AudioInputStream audioInputStream;
    private String filePath;

    @Override
    public void play(String audioFilePath) {
        try {
            filePath = audioFilePath;
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    @Override
    public void pause() {
        if (clip != null && clip.isRunning()) {
            currentFrame = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    @Override
    public void resume() {
        try {
            if (clip != null && !clip.isRunning()) {
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                clip.open(audioInputStream);
                clip.setMicrosecondPosition(currentFrame);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
