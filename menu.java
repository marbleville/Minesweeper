import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class menu extends JFrame implements ActionListener
{

   static JMenuBar mb;
   static JFrame f;
   static JMenu x, x1;
   static JMenuItem m1, s1, s2, s3, s4;
   static int h;
   static int v;
   static int d; 
   static int di;  
    
   public menu(JFrame f, Board b) 
   {
      h = b.getH();
      v = b.getV();
      d = b.getD();
      di = b.getDi();
      this.f = f;
      // create a menubar
      mb = new JMenuBar();
  
      // create a menu
      x = new JMenu("Game");
      x1 = new JMenu("Play");
  
      // create menuitems
      m1 = new JMenuItem("Restart");
      s1 = new JMenuItem("Easy");
      s2 = new JMenuItem("Medium");
      s3 = new JMenuItem("Hard");
      s4 = new JMenuItem("Custom");
  
     // add ActionListener to menuItems
     m1.addActionListener(this);
     s1.addActionListener(this);
     s2.addActionListener(this);
     s3.addActionListener(this);
     s4.addActionListener(this);
  
     // add menu items to menu
     x1.add(s1);
     x1.add(s2);
     x1.add(s3);
     x1.add(s4);
     x.add(m1);
  
     // add submenu
     x.add(x1);
  
     // add menu to menu bar
     mb.add(x);
  
     // add menubar to frame
     f.setJMenuBar(mb);
  
   }
   
   public void actionPerformed(ActionEvent e)
   {
      String s = e.getActionCommand();
      if (s.equals("Restart")) {
         f.dispose();
         Game g = new Game(h, v, d, di);
      }
      if (s.equals("Easy")) {
         f.dispose();
         Game g = new Game(10, 10, 10, 1);
      }
      if (s.equals("Medium")) {
         f.dispose();
         Game g = new Game(15, 15, 20, 2);
      }
      if (s.equals("Hard")) {
         f.dispose();
         Game g = new Game(20, 20, 20, 3);
      }
      if (s.equals("Custom")) {
         f.dispose();
         JTextField vField = new JTextField(5);
         JTextField hField = new JTextField(5);
         JTextField bField = new JTextField(5);
         JPanel myPanel = new JPanel(new GridLayout(3, 1));
         myPanel.add(new JLabel("Vertical Size:"));
         myPanel.add(vField);
         myPanel.add(new JLabel("Horizontal Size:"));
         myPanel.add(hField);
         myPanel.add(new JLabel("Bomb Density (1 - 100):"));
         myPanel.add(bField);
         int result = JOptionPane.showConfirmDialog(null, myPanel, 
                   "Minesweeper", JOptionPane.OK_CANCEL_OPTION);
         if (result == JOptionPane.OK_OPTION/* && hField.getText() != "" && vField.getText() != "" && bField.getText() != "" */) {
             Game game = new Game(new Integer(hField.getText()), new Integer(vField.getText()), new Integer(bField.getText()), 4);
         }
      }
   }
}
