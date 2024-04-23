import java.net.*;
import javax.sound.sampled.*;
import java.io.IOException;

//! You can put all your tool-code in here

public class Tools {

  // Declare variables
  public static Clip clip;
  public static Clip click_SE;

  /*
   * Play another bgm (for example):
      Tools.stopBGM();
      Tools.playGameBGM();
   * Play click sound effect
      Tools.basicClick_SE();
   */

  public static void playMainBGM() {
    try {
      // Attempt to get a Clip object
      clip = AudioSystem.getClip();
      // Open the audio input stream
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/main-page-bgm.wav"));
      clip.open(inputStream);
      // Start playing the clip and loop continuously
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (LineUnavailableException e) {
        // Handle LineUnavailableException
        e.printStackTrace();
    } catch (MalformedURLException e) {
        // Handle MalformedURLException
        e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
        // Handle UnsupportedAudioFileException
        e.printStackTrace();
    } catch (IOException e) {
        // Handle IOException
        e.printStackTrace();
    }
  }

  public static void playGameBGM() {
    try {
      // Attempt to get a Clip object
      clip = AudioSystem.getClip();
      // Open the audio input stream
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/in-game-bgm.wav"));
      clip.open(inputStream);
      // Start playing the clip and loop continuously
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (LineUnavailableException e) {
        // Handle LineUnavailableException
        e.printStackTrace();
    } catch (MalformedURLException e) {
        // Handle MalformedURLException
        e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
        // Handle UnsupportedAudioFileException
        e.printStackTrace();
    } catch (IOException e) {
        // Handle IOException
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
      // Attempt to get a Clip object
      click_SE = AudioSystem.getClip();
      // Open the audio input stream
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/basic-mouse-click-SE.wav"));
      click_SE.open(inputStream);
      // Start playing the clip and loop continuously
      click_SE.start();
    } catch (LineUnavailableException e) {
        // Handle LineUnavailableException
        e.printStackTrace();
    } catch (MalformedURLException e) {
        // Handle MalformedURLException
        e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
        // Handle UnsupportedAudioFileException
        e.printStackTrace();
    } catch (IOException e) {
        // Handle IOException
        e.printStackTrace();
    }
  }

  public static void shootClick_SE() {
    try {
      // Attempt to get a Clip object
      click_SE = AudioSystem.getClip();
      // Open the audio input stream
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new URL("file:./src/audio/guile-mouse-click-SE.wav"));
      click_SE.open(inputStream);
      // Start playing the clip and loop continuously
      click_SE.start();
    } catch (LineUnavailableException e) {
        // Handle LineUnavailableException
        e.printStackTrace();
    } catch (MalformedURLException e) {
        // Handle MalformedURLException
        e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
        // Handle UnsupportedAudioFileException
        e.printStackTrace();
    } catch (IOException e) {
        // Handle IOException
        e.printStackTrace();
    }
  }


}
