import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoundEffects {
	
   Clip bomb;
   Clip error;
   Clip dig;
   Clip flag;
   Clip win;
   Clip click;
	
	public SoundEffects(){
      try{
			File bombFile = new File("resources/explosion.wav");
         File errorFile = new File("resources/error.wav");
         File digFile = new File("resources/dig.wav");
         File flagFile = new File("resources/flag.wav");
         File winFile = new File("resources/win.wav");
         File clickFile = new File("resources/click.wav");
			AudioInputStream bombSound = AudioSystem.getAudioInputStream(bombFile);	
         AudioInputStream errorSound = AudioSystem.getAudioInputStream(errorFile);
         AudioInputStream digSound = AudioSystem.getAudioInputStream(digFile);
         AudioInputStream flagSound = AudioSystem.getAudioInputStream(flagFile);
         AudioInputStream winSound = AudioSystem.getAudioInputStream(winFile);
         AudioInputStream clickSound = AudioSystem.getAudioInputStream(clickFile);
			bomb = AudioSystem.getClip();
         error = AudioSystem.getClip();
         dig = AudioSystem.getClip();
         flag = AudioSystem.getClip();
         win = AudioSystem.getClip();
         click = AudioSystem.getClip();
			bomb.open(bombSound);
         error.open(errorSound);
         dig.open(digSound);
         flag.open(flagSound);
         win.open(winSound);
         click.open(clickSound);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
		
		
	
	public void play(String sound){
      if (sound.equals("Bomb")) {
         bomb.setFramePosition(0);
		   bomb.start();
      }
      if (sound.equals("Error")) {
         error.setFramePosition(0);
		   error.start();
      }
      if (sound.equals("Dig")) {
         dig.setFramePosition(0);
         dig.start();
      }
      if (sound.equals("Flag")) {
         flag.setFramePosition(0);
         flag.start();
      }
      if (sound.equals("Win")) {
         win.setFramePosition(0);
         win.start();
      }
      if (sound.equals("Click")) {
         click.setFramePosition(0);
         click.start();
      }
	}
}