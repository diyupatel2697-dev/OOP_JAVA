public class ParkingLot
{
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap = 50;
    private final int fourCap = 50;
    private static long revenue = 0;

    void park(String type)
    {
        if(type.equals("two"))
        {
            if(twoWheelers < twoCap)
            {
                twoWheelers++;
                revenue += 20;
                System.out.println("Two Wheeler Parked");
            }
            else
            {
                System.out.println("Two Wheeler Section Full");
            }
        }

        if(type.equals("four"))
        {
            if(fourWheelers < fourCap)
            {
                fourWheelers++;
                revenue += 40;
                System.out.println("Four Wheeler Parked");
            }
            else
            {
                System.out.println("Four Wheeler Section Full");
            }
        }
    }

    void leave(String type)
    {
        if(type.equals("two"))
        {
            if(twoWheelers > 0)
            {
                twoWheelers--;
                System.out.println("Two Wheeler Left");
            }
            else
            {
                System.out.println("No Two Wheeler Present");
            }
        }

        if(type.equals("four"))
        {
            if(fourWheelers > 0)
            {
                fourWheelers--;
                System.out.println("Four Wheeler Left");
            }
            else
            {
                System.out.println("No Four Wheeler Present");
            }
        }
    }

    void display()
    {
        System.out.println("\nFinal Occupancy");
        System.out.println("Two Wheelers : " + twoWheelers);
        System.out.println("Four Wheelers: " + fourWheelers);
        System.out.println("Revenue      : " + revenue);
    }

    public static void main(String[] args)
    {
        ParkingLot p = new ParkingLot();

        p.park("two");
        p.park("two");

        p.park("four");
        p.park("four");

        p.leave("two");
        p.leave("four");

        p.park("two");
        p.park("four");

        p.display();
    }
}