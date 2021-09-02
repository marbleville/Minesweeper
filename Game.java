import java.awt.*;
import java.awt.Dimension;


public class Game
{
   private Board board;
   private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
   
   public Game(int h, int v, int d, int di)
   {
      int btnHeight = (int) size.getHeight() / v; 
      board = new Board(h, v, d, btnHeight, di);
      Grid gr = new Grid(v, h, board, di);
   }
}