
public class Driver
{
    public static void main(String[] args)
    {
        
        
        //Point

        Point[] points =
        {
            new Point(2, 3),
            new Point(5, 6),
            new Point(2, 3),
            new Point(1, 4),
            new Point(5, 6)
        };

        int count = 0;

        for (int i = 0; i < points.length; i++)
        {
            boolean found = false;

            for (int j = 0; j < i; j++)
            {
                if (points[i].equals(points[j]))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                count++;
            }
        }

        System.out.println("Distinct: " + count);
    


        //Card

        Card[] cards = new Card[5];

        cards[0] = new Card("Ace", "Spades");
        cards[1] = new Card("King", "Hearts");
        cards[2] = new Card("Ace", "Spades");
        cards[3] = new Card("Jack", "Clubs");
        cards[4] = new Card("Jack", "Clubs");

        boolean duplicateFound = false;

        for (int i = 0; i < cards.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (cards[i].equals(cards[j]))
                {
                    System.out.println("Duplicate found: " + cards[i]);
                    duplicateFound = true;
                    break;
                }
            }

            if (duplicateFound)
            {
              break;
            }
        }




        //Fraction

        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);
        Fraction f4 = new Fraction(3, 9);

        System.out.println("Fractions after reducing:");

        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);
        System.out.println("f3 = " + f3);
        System.out.println("f4 = " + f4);

        System.out.println();

        System.out.println("Equality Check:");

        System.out.println("f1 equals f2 : " + f1.equals(f2));
        System.out.println("f2 equals f3 : " + f2.equals(f3));
        System.out.println("f3 equals f4 : " + f3.equals(f4));
        System.out.println("f1 equals f4 : " + f1.equals(f4));

        System.out.println();

        System.out.println("Hash Codes:");

        System.out.println("f1 : " + f1.hashCode());
        System.out.println("f2 : " + f2.hashCode());
        System.out.println("f3 : " + f3.hashCode());
        System.out.println("f4 : " + f4.hashCode());
    }

    
}

