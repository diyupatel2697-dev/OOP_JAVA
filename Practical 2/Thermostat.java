public class Thermostat {

    private String Location;
    private int temperature;
    private static final int MIN=16;
    private static final int MAX=30;
    private static int activeCount =0;

    Thermostat(String location,int startTemp)
    {
        this.Location =location;
        if(temperature <MAX && temperature > MIN)
        {
            this.temperature= startTemp;

        }
        else{
            this.temperature=22;
        }
        activeCount++;
    }


    Thermostat(String location)
    {
        this(location,22);
    }



    void raise()
    {
        if(temperature < MAX)
        {
             temperature++;
        }
        else
        {
            System.out.println("Already at maximum (30)");
        }
        
    }
    
    void lower()
    {
        if (temperature > MIN)
        {
            temperature--;
        }
        else
        {
            System.out.println("Already at minimum (16)");
        }
    }

   public int getTemperature()
    {
        return temperature;
    }

    public static int getActiveCount()
    {
            return activeCount;
    }

    public static void main(String[] arg)
    {
         Thermostat t1 = new Thermostat("BedRoom",20);
         Thermostat t2 = new Thermostat("dinningHall");
         

         for(int i=0;i<10;i++)
         {
            t1.raise();
            System.out.println("Temperature:"+t1.getTemperature());
         }
        
         for(int i=0;i<20;i++)
         {
            t2.lower();
            System.out.println("Temperature:"+t1.getTemperature());

         }
         System.out.println("Active Count:"+Thermostat.getActiveCount());

    }
    
}
