import java.util.Scanner;

public class VendingMachine {

    public static void main(String[] arg) 
    
    {
      enum COIN 
      {
        ONE, TWO, FIVE, TEN;
     }
     int change=0;
     
       int total = 0;
        int Price = 15;

        Scanner coin = new Scanner(System.in);

        while(total < Price) {
            System.out.println("Enter coin: ONE, TWO, FIVE, TEN");

            String c = coin.nextLine();
            COIN input = COIN.valueOf(c.toUpperCase());  // convert string to enum

            switch(input) 
            {
                case ONE: total += 1; break;
                case TWO: total += 2; break;
                case FIVE: total += 5; break;
                case TEN: total += 10; break;
            }

            System.out.println("Total: " + total);
        }

        if(total>Price)
         change = total - Price;
        System.out.println("Change is: " + change);
    }
}


