import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Grid extends JFrame implements MouseListener 
{
   private Board b;
   private JButton[][] buttons;
   private int ct = 0;
   private boolean win = false;
   private JFrame frame;
   private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
   private int btnHeight;
   private int btnWidth;
   private ImageIcon one;
   private ImageIcon two;
   private ImageIcon three;
   private ImageIcon four;
   private ImageIcon five;
   private ImageIcon six;
   private ImageIcon seven;
   private ImageIcon dirt;
   private ImageIcon mined;
   private ImageIcon flag;
   private ImageIcon bomb;
   private Time t;
   private String hs1 = "";
   private String hs2 = "";
   private String hs3 = "";
   private String hs4 = "";
   private int diff;
   private ArrayList<String> lines; 
   private SoundEffects effect = new SoundEffects();
   
   
   public Grid(int r, int c, Board b, int di)
   {
      ct = 0;
      diff = di;
      getHighscore();
      this.b = b;
      buttons = new JButton[r][c];
      frame = new JFrame("Minesweeper by Allen Ehrhardt");
      JPanel panel = new JPanel(new GridLayout(r, c));
      btnHeight = (int) (size.getHeight()) / r;
      btnWidth = (int) (size.getHeight()) / c;
      one = new ImageIcon(new ImageIcon("resources/one.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "one");
      two = new ImageIcon(new ImageIcon("resources/two.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "two");
      three = new ImageIcon(new ImageIcon("resources/three.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "three");
      four = new ImageIcon(new ImageIcon("resources/four.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "four");
      five = new ImageIcon(new ImageIcon("resources/five.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "five");
      six = new ImageIcon(new ImageIcon("resources/six.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "six");
      seven = new ImageIcon(new ImageIcon("resources/seven.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "seven");
      dirt = new ImageIcon(new ImageIcon("resources/dirt.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "dirt");
      mined = new ImageIcon(new ImageIcon("resources/mine.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "mined");
      flag = new ImageIcon(new ImageIcon("resources/flag.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "flag");
      bomb = new ImageIcon(new ImageIcon("resources/bomb.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "bomb");
      // add buttons to the panel
      for (int i = 0; i < r; i++) {
         for(int j = 0; j < c; j++){
            JButton btn = new JButton((i + 1) + "," + (j + 1), dirt);
            btn.addMouseListener(this);
            buttons[i][j] = btn;
            panel.add(btn);            
         }
      }
      menu m = new menu(frame, b);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(btnHeight * c, btnHeight * r);
      frame.getContentPane().add(panel);
      frame.setIconImage(new ImageIcon("resources/icon.jpg").getImage());
      frame.setVisible(true);
   }
   
    public void mousePressed(MouseEvent e) {
       
    } 
    
    public void mouseReleased(MouseEvent e) {
       
    }

    public void mouseEntered(MouseEvent e) {
    
    }

    public void mouseExited(MouseEvent e) {
    
    }
    
     public void mouseClicked(MouseEvent e) {
       JButton button = (JButton) e.getSource();
       String name = button.getText();
       String[] coords = name.split(",");
       int x = new Integer(coords[0]);
       int y = new Integer(coords[1]);       
       Tile[][] board = b.getBoard();
       if (e.getButton() == MouseEvent.BUTTON1 && !board[y][x].getFStatus()) {
          if (ct == 0) {
              effect.play("Dig");
              b.genBoard(y, x, buttons, btnHeight);
              ct++;
              t = new Time();      
          }
          if (board[y][x].getBStatus()) {
             button.setIcon(bomb);
             b.setExplode(true);
             effect.play("Bomb");
             revealIcon(buttons);
             showKillScreen(frame);
          }
          else {
             if (board[y][x].getBValue() == 0 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(mined); board[y][x].setMStatus(true); b.clearADJ(y, x, 0, buttons, btnHeight); }
             if (board[y][x].getBValue() == 1 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(one); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 2 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(two); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 3 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(three); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 4 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(four); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 5 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(five); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 6 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(six); board[y][x].setMStatus(true); }
             if (board[y][x].getBValue() == 7 && !board[y][x].getMStatus()) { effect.play("Dig"); buttons[x - 1][y - 1].setIcon(seven); board[x][y].setMStatus(true); }
             win = b.checkWin();
          }
       }
       if (e.getButton() == MouseEvent.BUTTON3 && !board[y][x].getMStatus() && !board[y][x].getFStatus()) {
          effect.play("Flag");
          board[y][x].setFStatus(true);
          button.setIcon(flag);
          win = b.checkWin();
          if (win) {
             effect.play("Win");
             showWinScreen();
          }   
       }
       else if (board[y][x].getFStatus() && !board[y][x].getMStatus()) {
          board[y][x].setFStatus(false);
          effect.play("Dig");
          buttons[x - 1][y - 1].setIcon(dirt);
       }
    }
    
    public void revealIcon(JButton[][] buttons) {
       Tile[][] board = b.getBoard();
       for (JButton[] row : buttons) {
         for (JButton button : row) {
            String name = button.getText();
            String[] coords = name.split(",");
            int c = new Integer(coords[0]);
            int r = new Integer(coords[1]);
            if (board[r][c].getBValue() == 1) { buttons[c - 1][r - 1].setIcon(one); }
            if (board[r][c].getBValue() == 2) { buttons[c - 1][r - 1].setIcon(two); }
            if (board[r][c].getBValue() == 3) { buttons[c - 1][r - 1].setIcon(three); }
            if (board[r][c].getBValue() == 4) { buttons[c - 1][r - 1].setIcon(four); }
            if (board[r][c].getBValue() == 5) { buttons[c - 1][r - 1].setIcon(five); }
            if (board[r][c].getBValue() == 6) { buttons[c - 1][r - 1].setIcon(six); }
            if (board[r][c].getBValue() == 7) { buttons[c - 1][r - 1].setIcon(seven); }
            if (board[r][c].getBStatus()) { buttons[c - 1][r - 1].setIcon(bomb); }  
            if (!board[r][c].getBStatus() && board[r][c].getBValue() == 0) { buttons[c - 1][r - 1].setIcon(mined); }  
          }
       }
    }
    
    public void showWinScreen()
    {
       double time = (double) t.getTime() / 1000;       
       double h = 0.00;
       if (diff == 1) {h = Double.parseDouble(hs1);}
       if (diff == 2) {h = Double.parseDouble(hs2);}
       if (diff == 3) {h = Double.parseDouble(hs3);}
       if (diff == 4) {h = Double.parseDouble(hs4);}
       if (h > time) {
          writeHighscore(time);
          h = time;
       }      
       JPanel myPanel2 = new JPanel(new GridLayout(5, 1));
       myPanel2.add(new JLabel("You WIN!"));
       myPanel2.add(new JLabel("Time: " + time + " seconds"));
       myPanel2.add(new JLabel("Best Time: " + h + " seconds"));
       myPanel2.add(new JLabel(""));
       myPanel2.add(new JLabel("Do you want to play again?"));
       int result2 = JOptionPane.showConfirmDialog(null, myPanel2, 
               "Minesweeper", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
       if (result2 == JOptionPane.OK_OPTION) {
          effect.play("Click");
          GameChooser g = new GameChooser(frame);
       }
       if (result2 == JOptionPane.OK_OPTION) {
          effect.play("Click");
       }
    }
    
    public void showKillScreen(JFrame frame)
    {
       JPanel myPanel2 = new JPanel(new GridLayout(3, 1));
       myPanel2.add(new JLabel("You LOST!"));
       myPanel2.add(new JLabel("Do you want to play again?"));
       int result2 = JOptionPane.showConfirmDialog(null, myPanel2, 
               "Minesweeper", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
       if (result2 == JOptionPane.OK_OPTION) {
          effect.play("Click");
          GameChooser g = new GameChooser(frame);
       }
       if (result2 == JOptionPane.OK_OPTION) {
          effect.play("Click");
       }
    }
    
    public void getHighscore()
    {
       lines = new ArrayList<String>();
       try {
          File myObj = new File("resources/highscore.txt");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
             String data = myReader.nextLine(); 
             lines.add(data);
          }
          myReader.close();
       } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
       }
       String[] l1 = lines.get(0).split(":");
       String[] l2 = lines.get(1).split(":");
       String[] l3 = lines.get(2).split(":");
       String[] l4 = lines.get(3).split(":");
       hs1 = l1[1];
       hs2 = l2[1];
       hs3 = l3[1];
       hs4 = l4[1];
    }
    
    public void writeHighscore(double time)
    {
       String[] line = lines.get(diff - 1).split(":");
       String newline = line[0] + ":" + String.valueOf(time);
       lines.set(diff - 1, newline);
       String newinfo = "";
       for (String s : lines) {
          newinfo += s + "\r\n";
       }
       try {
           FileWriter writer = new FileWriter("resources/highscore.txt");
           writer.write(newinfo);
           writer.close();
       } catch (IOException e) {
           e.printStackTrace();  
       }
    }
}

