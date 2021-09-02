import java.util.Date;
  
public class Time
{
    public static long time;
    
    public Time()
    {
       time = System.currentTimeMillis();
    }
    
    public long getTime()
    {
      return System.currentTimeMillis() - time;
    }
    
    public void resetTime()
    {
       time = System.currentTimeMillis();
    }
}
  

