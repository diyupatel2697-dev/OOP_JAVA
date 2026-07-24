public class CinemaShow 
{
    private String title;
    private int seatsAvailable;
    private final int capacity=100;
    private static int totalBooked=0;

    CinemaShow(String Title,int capacity)
    {
        this.seatsAvailable =capacity;
        this.title= Title;
    }

    CinemaShow(String Title)
    {
        this(Title,100);
    }

    boolean book(int n)
    {
        if( n <=seatsAvailable)
        {
           seatsAvailable-=-n;
           totalBooked+=n;
           return true;
        }
        else
        {
            return false;
        }
    }

    void cancel(int n)
    {
        if(n <=capacity-seatsAvailable)
        {
            seatsAvailable+=n;
            totalBooked-=n;
        }
    }
    public int getSeatsAvailable()
    {
        return this.seatsAvailable;
    }
   public static int getTotalBooked()
   {
        return totalBooked;
   }

public static void main(String[] arg)
{
    CinemaShow c1 = new CinemaShow("ABC",50);
    

   System.out.println("Book 40:"+c1.book(40));
   System.out.println("Seates Available :" +c1.getSeatsAvailable());

     System.out.println("Book 20:"+c1.book(20));
     System.out.println("Seates Available :" +c1.getSeatsAvailable());

     
    c1.cancel(5);
    System.out.println("After Cancel 5:");
    System.out.println("Seats Available: " + c1.getSeatsAvailable());


    c1.cancel(15);
    System.out.println("After Cancel 15:");
    System.out.println("Seats Available: " + c1.getSeatsAvailable());

    System.out.println("Total booked:"+getTotalBooked());

}
}