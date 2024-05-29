import java.net.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

//! You can put all your tool-code in here

public class Tools {

  // Declare variables
  public static Clip clip;
  public static Clip click_SE;

  /*
   * Play another bgm (for example):
   * Tools.stopBGM();
   * Tools.playGameBGM();
   * Play click sound effect
   * Tools.basicClick_SE();
   */

  public static void playMainBGM() {
    try {
      // get Clip
      clip = AudioSystem.getClip();
      // open the audio input stream
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/main-page-bgm.wav"));
      clip.open(inputStream);

      // get the volume control
      FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      // countrol the volume
      float min = volumeControl.getMinimum();
      float max = volumeControl.getMaximum();
      float volume = (max - min) * 0.68f + min;
      volumeControl.setValue(volume);

      // start playing the clip and loop continuously
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (LineUnavailableException e) {
      e.printStackTrace();  // Handle LineUnavailableException
    } catch (MalformedURLException e) {
      e.printStackTrace();  // Handle MalformedURLException
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();  // Handle UnsupportedAudioFileException
    } catch (IOException e) {
      e.printStackTrace();  // Handle IOException
    }
  }

  public static void playGameBGM() {
    try {
      clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/in-game-bgm.wav"));
      clip.open(inputStream);

      // get the volume control
      FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      // countrol the volume
      float min = volumeControl.getMinimum();
      float max = volumeControl.getMaximum();
      float volume = (max - min) * 0.69f + min;
      volumeControl.setValue(volume);

      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void stopBGM() {
    // Check if the clip is not null and is currently running
    if (clip != null && clip.isRunning()) {
      clip.stop(); // Stop the clip
      clip.close(); // Close the clip
    }
  }

  public static void basicClick_SE() {
    try {
      click_SE = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem
          .getAudioInputStream(new URL("file:./src/audio/basic-mouse-click-SE.wav"));
      click_SE.open(inputStream);

      // get the volume control
      FloatControl volumeControl = (FloatControl) click_SE.getControl(FloatControl.Type.MASTER_GAIN);
      // countrol the volume
      float min = volumeControl.getMinimum();
      float max = volumeControl.getMaximum();
      float volume = (max - min) * 0.7f + min;
      volumeControl.setValue(volume);

      click_SE.start();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void shootClick_SE() {
    try {
      click_SE = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem
          .getAudioInputStream(new URL("file:./src/audio/guile-mouse-click-SE.wav"));
      click_SE.open(inputStream);

      // get the volume control
      FloatControl volumeControl = (FloatControl) click_SE.getControl(FloatControl.Type.MASTER_GAIN);
      // countrol the volume
      float min = volumeControl.getMinimum();
      float max = volumeControl.getMaximum();
      float volume = (max - min) * 0.7f + min;
      volumeControl.setValue(volume);

      click_SE.start();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void adjustVolume(float adjustValue) {
    if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
      FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float newVolume = volume.getValue() + adjustValue;
      float minVolume = volume.getMinimum();
      float maxVolume = volume.getMaximum();

      if (newVolume < minVolume) {
        newVolume = minVolume;
      } else if (newVolume > maxVolume) {
        newVolume = maxVolume;
      }
      volume.setValue(newVolume);
    } else {
      System.out.println("Volume control not supported");
    }
  }

  public static void drawText(String text, Font font, Graphics2D g2d, int x, int y, int border_width){
    g2d.setFont(font);
    // 描邊文本（黑色）
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(border_width)); // 設置描邊寬度
    FontRenderContext frc = g2d.getFontRenderContext();
    TextLayout tl = new TextLayout(text, font, frc);
    Shape shape = tl.getOutline(null);
    g2d.translate(x, y);
    g2d.draw(shape);

    // 填充文本（白色）
    g2d.setColor(Color.WHITE);
    g2d.fill(shape); // 填充文本
    g2d.translate(-x, -y);
  }

}
