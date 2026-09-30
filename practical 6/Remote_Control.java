interface Switchable
    {
        void on();
        void off();

        default void toggle()
        {
            System.out.println("Toggling..");
            on();
            off();
        }
    }

    class Fan implements Switchable
    {
        @Override
        public void on()
            {
                System.out.println("Fan is ON");
            }
        @Override
        public void off()
        {
            System.out.println("Fan is OFF");
        }
    }
    
    class Light implements Switchable
    {
         @Override
        public void on()
            {
                System.out.println("Light is ON");
            }
        @Override
        public void off()
        {
            System.out.println("Light is OFF");
        }
    }

    
    interface Permission
    {
        boolean maySwitchOn(Switchable device, int hour);
    }

public class Remote_Control 
{
    
    public static void main(String[] arg)
    {
        Switchable[] devices={new Fan(),new Light()};

        for(Switchable device:devices)
        {
            device.toggle();
        }

        System.out.println("\nPermission Check ");
        Permission nightonly=new Permission()
        {
            @Override
            public boolean maySwitchOn(Switchable device,int hour)
            {
                return hour>=18 || hour<6;
            }
        };

       for (Switchable device : devices) 
        {
            System.out.println(device.getClass().getSimpleName() +
                    " may switch on at 20: " + nightonly.maySwitchOn(device, 20));
        }

        System.out.println("\n=== Permission Check (Lambda) ===");
        Permission dayOnly = (device, hour) -> hour >= 6 && hour < 18;

        for (Switchable device : devices) 
        {
            System.out.println(device.getClass().getSimpleName() +
                    " may switch on at 10: " + dayOnly.maySwitchOn(device, 10));
        }
    }
}
