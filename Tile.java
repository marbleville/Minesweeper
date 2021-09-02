public class Tile 
{
   private int row;
   private int col;
   private int bValue; //num bombs around tile
   private boolean bomb; //tile is bomb 
   private boolean flagged = false;
   private boolean mined = false;
   private boolean cleared = false; //for auto clearing of empty spaces
   
   public Tile(int r, int c, boolean b)
   {
      row = r;
      col = c;
      bomb = b; 
   }
   
   public int getRow()
   {
      return row;
   }
   
   public int getCol()
   {
      return col; 
   }
   
   public boolean getBStatus()
   {
      return bomb;
   }
   
   public void setBStatus(boolean b)
   {
      bomb = b;
   }
   
   public boolean getFStatus()
   {
      return flagged;
   }
   
   public void setFStatus(boolean f)
   {
      flagged = f;
   }
   
   public void setMStatus(boolean m)
   {
      mined = m;
   }
   
   public boolean getMStatus()
   {
      return mined;
   }
   
   public void setBValue(int i)
   {
      bValue = i;
   }
   
   public int getBValue()
   {
      return bValue;
   }
   
   public boolean checkClear()
   {
      return cleared;
   }
   
   public void setClear(boolean i)
   {
      cleared = i;
   } 
   
   public String toString(boolean ex) 
   {
      if (row == 0 && col == 0)
         return "   ";
      if (row == 0)
         if (col < 9)
            return "" + (col) + "  ";
         else 
            return "" + (col) + " ";
      if (col == 0)
         if (row < 10)
            return "" + (row) + " ";
         else 
            return "" + (row);
      if (ex == true && bomb == true) 
         return "[*]";       
      else if (flagged == true) 
         return "[F]";
      else if (mined == false)
         return "[M]";         
      else if (bValue != 0) 
         return "[" + bValue + "]"; 
      else
         return "[ ]";   
   }
}