import java.awt.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board
{
   private int horSize;
   private int vertSize;
   private int bDensity;
   private int di;
   private boolean explode = false;
   private Tile[][] board;
   private ImageIcon one;
   private ImageIcon two;
   private ImageIcon three;
   private ImageIcon four;
   private ImageIcon five;
   private ImageIcon six;
   private ImageIcon seven;
   private ImageIcon mined;
   
   public Board(int hs, int vs, int density, int btnHeight, int d)
   {
      one = new ImageIcon(new ImageIcon("resources/one.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "one");
      two = new ImageIcon(new ImageIcon("resources/two.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "two");
      three = new ImageIcon(new ImageIcon("resources/three.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "three");
      four = new ImageIcon(new ImageIcon("resources/four.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "four");
      five = new ImageIcon(new ImageIcon("resources/five.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "five");
      six = new ImageIcon(new ImageIcon("resources/six.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "six");
      seven = new ImageIcon(new ImageIcon("resources/seven.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "seven");
      mined = new ImageIcon(new ImageIcon("resources/mine.jpg").getImage().getScaledInstance(btnHeight + 15, btnHeight, Image.SCALE_SMOOTH), "mined");
      horSize = hs;
      vertSize = vs;
      bDensity = density;
      di = d; 
      board = new Tile[hs + 1][vs + 1];
      for (int i = 0; i <= horSize; i++) {                
         for (int j = 0; j <= vertSize; j++) {
            Tile t = new Tile(i, j, false);
               board[i][j] = t;
         }
      }     
   }
   
   public void genBoard(int r, int c, JButton[][] buttons, int btnHeight)
   {
      String[] adj = new String[9];
      adj[0] = new String(r + "," + c);
      adj[1] = new String(r + "," + (c + 1));
      adj[2] = new String(r + "," + (c - 1));
      adj[3] = new String((r - 1) + "," + c);
      adj[4] = new String((r - 1) + "," + (c - 1));
      adj[5] = new String((r - 1) + "," + (c + 1));
      adj[6] = new String((r + 1) + "," + c);
      adj[7] = new String((r + 1) + "," + (c - 1));
      adj[8] = new String((r + 1) + "," + (c + 1));
      for (int i = 0; i <= horSize; i++) {                 //randomly assigns bombs based on given density
         for (int j = 0; j <= vertSize; j++) {
            if (i != 0 && j != 0) {
               String co = i + "," + j; 
               if (!find(adj, co)) { 
                  int rand = (int) (Math.random() * 100);
                  if (rand <= bDensity) {
                     Tile t = new Tile(i, j, true);
                     board[i][j] = t;
                  }
                  else {
                     Tile t = new Tile(i, j, false);
                     board[i][j] = t;
                  }
               }
               else {
                  Tile t = new Tile(i, j, false);
                  board[i][j] = t;
               }
            }   
         }
      }
      calcValues();
      board[r][c].setFStatus(false);
      board[r][c].setMStatus(true);
      clearADJ(r, c, 0, buttons, btnHeight);
   }
   
   public void printBoard()
   {
      for (Tile[] tiles : board) {
         for (Tile tile : tiles) {
            System.out.print(tile.toString(explode));
         }
         System.out.println();
      }
   }
   
   public boolean checkWin() //checks if all bombs are flagged
   {
      int bCt = 0;
      int bfCt = 0;
      int fCt = 0;
      for (Tile[] spaces : board)
         for (Tile space : spaces)
         {
            if (space.getBStatus() == true)
               bCt++;
            if (space.getBStatus() == true && space.getFStatus() == true)
               bfCt++;
            if (space.getFStatus())
               fCt++;
         }
      if (bCt == bfCt && fCt == bCt)
         return true;
      return false; 
   }
   
   private void calcValues() //find num bombs near tiles
   {
      for (int i = 1; i < board.length - 1; i++) {
         for (int j = 1; j < board[0].length - 1; j++) {
            int Ct = 0;
            if (board[i - 1][j - 1].getBStatus() == true) { Ct++; }
            if (board[i - 1][j].getBStatus() == true) { Ct++; }
            if (board[i - 1][j + 1].getBStatus() == true) { Ct++; }
            if (board[i][j - 1].getBStatus() == true) { Ct++; }
            if (board[i][j + 1].getBStatus() == true) { Ct++; }
            if (board[i + 1][j - 1].getBStatus() == true) { Ct++; }
            if (board[i + 1][j].getBStatus() == true) { Ct++; }
            if (board[i + 1][j + 1].getBStatus() == true) { Ct++; }
       
            board[i][j].setBValue(Ct);
         } 
      } 
 
      for (int k = 1; k < board[0].length - 1; k++) {
         int Ct2 = 0;
         if (board[horSize][k - 1].getBStatus() == true) { Ct2++; }
         if (board[horSize][k + 1].getBStatus() == true) { Ct2++; }
         if (board[horSize - 1][k - 1].getBStatus() == true) { Ct2++; }
         if (board[horSize - 1][k + 1].getBStatus() == true) { Ct2++; }
         if (board[horSize - 1][k].getBStatus() == true) { Ct2++; }     
         board[horSize][k].setBValue(Ct2);
      } 
      
      for (int l = 1; l < board.length - 1; l++) {
         int Ct3 = 0;
         if (board[l - 1][vertSize].getBStatus() == true) { Ct3++; }
         if (board[l + 1][vertSize].getBStatus() == true) { Ct3++; }
         if (board[l][vertSize - 1].getBStatus() == true) { Ct3++; }
         if (board[l + 1][vertSize - 1].getBStatus() == true) { Ct3++; }
         if (board[l - 1][vertSize - 1].getBStatus() == true) { Ct3++; }
         
         board[l][vertSize].setBValue(Ct3);
      } 
      
      int Ct4 = 0;
      if (board[horSize - 1][1].getBStatus() == true) { Ct4++; }
      if (board[horSize - 1][2].getBStatus() == true) { Ct4++; }
      if (board[horSize][2].getBStatus() == true) { Ct4++; } 
      board[horSize][1].setBValue(Ct4);
      
      int Ct5 = 0;
      if (board[horSize - 1][vertSize].getBStatus() == true) { Ct5++; }
      if (board[horSize - 1][vertSize - 1].getBStatus() == true) { Ct5++; }
      if (board[horSize][vertSize - 1].getBStatus() == true) { Ct5++; } 
      board[horSize][vertSize].setBValue(Ct5);
   }
   
   public void clearADJ(int r, int c, int ct, JButton[][] buttons, int btnHeight) 
   {
      if (board[r][c].getBValue() != 0) {
         if (r > 0 && c > 0) {
            board[r][c].setMStatus(true);
            board[r][c].setClear(true);
            if (board[r][c].getBValue() == 1) { buttons[c - 1][r - 1].setIcon(one); }
            if (board[r][c].getBValue() == 2) { buttons[c - 1][r - 1].setIcon(two); }
            if (board[r][c].getBValue() == 3) { buttons[c - 1][r - 1].setIcon(three); }
            if (board[r][c].getBValue() == 4) { buttons[c - 1][r - 1].setIcon(four); }
            if (board[r][c].getBValue() == 5) { buttons[c - 1][r - 1].setIcon(five); }
            if (board[r][c].getBValue() == 6) { buttons[c - 1][r - 1].setIcon(six); }
            if (board[r][c].getBValue() == 7) { buttons[c - 1][r - 1].setIcon(seven); }
         }
      }
      else if (board[r][c].getBValue() == 0 && !board[r][c].getBStatus()) {
         board[r][c].setMStatus(true);
         board[r][c].setClear(true);
         if (r > 0 && c > 0) {
            buttons[c - 1][r - 1].setIcon(mined);
         }
         if (r != 0 && c != 0 && r < vertSize && c < horSize) {
            if (!board[r - 1][c - 1].checkClear() && !board[r - 1][c - 1].getBStatus()) { clearADJ(r - 1, c - 1, ct + 1, buttons, btnHeight); }
            if (!board[r - 1][c].checkClear() && !board[r - 1][c].getBStatus()) { clearADJ(r - 1, c, ct + 1, buttons, btnHeight); } 
            if (!board[r - 1][c + 1].checkClear() && !board[r - 1][c + 1].getBStatus()) { clearADJ(r - 1, c + 1, ct + 1, buttons, btnHeight); } 
            if (!board[r][c - 1].checkClear() && !board[r][c - 1].getBStatus()) { clearADJ(r, c - 1, ct + 1, buttons, btnHeight); } 
            if (!board[r][c + 1].checkClear() && !board[r][c + 1].getBStatus()) { clearADJ(r, c + 1, ct + 1, buttons, btnHeight); } 
            if (!board[r + 1][c].checkClear() && !board[r + 1][c].getBStatus()) { clearADJ(r + 1, c, ct + 1, buttons, btnHeight); } 
            if (!board[r + 1][c - 1].checkClear() && !board[r + 1][c - 1].getBStatus()) { clearADJ(r + 1, c - 1, ct + 1, buttons, btnHeight); } 
            if (!board[r + 1][c + 1].checkClear() && !board[r + 1][c + 1].getBStatus()) { clearADJ(r + 1, c + 1, ct + 1, buttons, btnHeight); }
         } 
         
         else if (r != 0 && c != 0 && (r == vertSize || c == horSize)) {
            if (r != 0 && c != 0 && r == vertSize && c != horSize) {
               if (!board[r][c + 1].checkClear() && !board[r][c + 1].getBStatus()) { clearADJ(r, c + 1, ct + 1, buttons, btnHeight); }
               if (!board[r][c - 1].checkClear() && !board[r][c - 1].getBStatus()) { clearADJ(r, c - 1, ct + 1, buttons, btnHeight); }
               if (!board[r - 1][c - 1].checkClear() && !board[r - 1][c - 1].getBStatus()) { clearADJ(r - 1, c - 1, ct + 1, buttons, btnHeight); }
               if (!board[r - 1][c].checkClear() && !board[r - 1][c].getBStatus()) { clearADJ(r - 1, c, ct + 1, buttons, btnHeight); }
               if (!board[r - 1][c + 1].checkClear() && !board[r - 1][c + 1].getBStatus()) { clearADJ(r - 1, c + 1, ct + 1, buttons, btnHeight); }
            }
            else if (r != 0 && c != 0 && r != vertSize && c == horSize) {
               if (!board[r - 1][c].checkClear() && !board[r - 1][c].getBStatus()) { clearADJ(r - 1, c, ct + 1, buttons, btnHeight); }
               if (!board[r + 1][c].checkClear() && !board[r + 1][c].getBStatus()) { clearADJ(r + 1, c, ct + 1, buttons, btnHeight); }
               if (!board[r - 1][c - 1].checkClear() && !board[r - 1][c - 1].getBStatus()) { clearADJ(r - 1, c - 1, ct + 1, buttons, btnHeight); }
               if (!board[r][c - 1].checkClear() && !board[r][c - 1].getBStatus()) { clearADJ(r, c - 1, ct + 1, buttons, btnHeight); }
               if (!board[r + 1][c - 1].checkClear() && !board[r + 1][c - 1].getBStatus()) { clearADJ(r + 1, c - 1, ct + 1, buttons, btnHeight); }          
            }
            else if (r == vertSize && c == horSize) { 
               if (!board[r - 1][c].checkClear() && !board[r - 1][c].getBStatus()) { clearADJ(r - 1, c, ct + 1, buttons, btnHeight); }
               if (!board[r - 1][c - 1].checkClear() && !board[r - 1][c - 1].getBStatus()) { clearADJ(r - 1, c - 1, ct + 1, buttons, btnHeight); }
               if (!board[r][c - 1].checkClear() && !board[r][c - 1].getBStatus()) { clearADJ(r, c - 1, ct + 1, buttons, btnHeight); }
            }
         }
      } 
   }
   
   public boolean find(String[] arr, String coord) 
   {
      for (int i = 0; i < arr.length; i++) {
         if (arr[i].equals(coord))
            return true;
      }
      return false;
   }
   
   public boolean getBoardStatus()
   {
      return explode;
   }
   
   public Tile[][] getBoard()
   {
      return board;
   }
   
   public void setExplode(boolean e)
   {
      explode = e;
   }
   
   public int getH() 
   {
      return horSize;
   }
   
   public int getV() 
   {
      return vertSize;
   }
   
   public int getD() 
   {
      return bDensity;
   }
   
   public int getDi() 
   {
      return di;
   }
}

