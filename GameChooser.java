import java.awt.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameChooser extends JFrame implements ActionListener
{
   private JFrame f;
   private SoundEffects effect = new SoundEffects();
   
   public GameChooser()
   {
      JTextField vField = new JTextField(5);
      JTextField hField = new JTextField(5);
      JTextField bField = new JTextField(5);
      GridLayout g = new GridLayout(3, 3);
      g.setHgap(10);
      JPanel myPanel = new JPanel(g);
      myPanel.add(new JLabel("Vertical Size:"));
      myPanel.add(vField);
      JButton b = new JButton("Easy");
      b.addActionListener(this); 
      myPanel.add(b);
      myPanel.add(new JLabel("Horizontal Size:"));
      myPanel.add(hField);
      JButton b1 = new JButton("Medium");
      b1.addActionListener(this); 
      myPanel.add(b1);
      myPanel.add(new JLabel("Bomb Density (1 - 100):"));
      myPanel.add(bField);
      JButton b2 = new JButton("Hard");
      b2.addActionListener(this); 
      myPanel.add(b2);
    
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Minesweeper", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION && (hField.getText().equals("") || vField.getText().equals("") || bField.getText().equals(""))) {
         effect.play("Error");
         GameChooser gamechooser = new GameChooser();
      }
      else if (result != JOptionPane.CANCEL_OPTION) {
         if (!hField.getText().equals("") && !vField.getText().equals("") && !bField.getText().equals("")) {
            effect.play("Click");
            Game game = new Game(new Integer(hField.getText()), new Integer(vField.getText()), new Integer(bField.getText()), 4);
         }      
      }
   }
   
   public GameChooser(JFrame frame)
   {
      f = frame;
      JTextField vField = new JTextField(5);
      JTextField hField = new JTextField(5);
      JTextField bField = new JTextField(5);
      GridLayout g = new GridLayout(3, 3);
      g.setHgap(10);
      JPanel myPanel = new JPanel(g);
      myPanel.add(new JLabel("Vertical Size:"));
      myPanel.add(vField);
      JButton b = new JButton("Easy");
      b.addActionListener(this); 
      myPanel.add(b);
      myPanel.add(new JLabel("Horizontal Size:"));
      myPanel.add(hField);
      JButton b1 = new JButton("Medium");
      b1.addActionListener(this); 
      myPanel.add(b1);
      myPanel.add(new JLabel("Bomb Density (1 - 100):"));
      myPanel.add(bField);
      JButton b2 = new JButton("Hard");
      b2.addActionListener(this); 
      myPanel.add(b2);
    
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Minesweeper", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
         effect.play("Click");
         f.dispose();
         Game game = new Game(new Integer(hField.getText()), new Integer(vField.getText()), new Integer(bField.getText()), 4);
      }
      if (result == JOptionPane.CANCEL_OPTION) {
         effect.play("Click");
      }
   }
   public void actionPerformed(ActionEvent e)
   {
      effect.play("Click");
      if (f != null)
         f.dispose();
      JComponent comp = (JComponent) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();
      String s = e.getActionCommand();
      if (s.equals("Easy")) {
         Game g = new Game(10, 10, 10, 1);
      }
      if (s.equals("Medium")) {
         Game g = new Game(15, 15, 10, 2);
      }
      if (s.equals("Hard")) {
         Game g = new Game(20, 20, 15, 3);
      }
   }
}