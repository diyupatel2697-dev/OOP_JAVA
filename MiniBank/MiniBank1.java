import java.util.Scanner;

public class MiniBank1 
{
    record BankInfo(String name,String branch){}

    enum MenuOption
    {
        OPEN_ACCOUNT, DEPOSIT,WITHDRAW, TRANSFER ,EXIT
    }

    public static void main(String[] args) {
        {
            MenuOption input;
            do
            {

            MenuOption[]  Menu= MenuOption.values();
            //for(MenuOption menu : Menu)
            //{
            //    System.out.println(menu);
            //}

            for(int i=0;i<Menu.length;i++)
            {
                System.out.println((i+1)+ ":"+Menu[i]);
            }

             

            Scanner sc = new Scanner(System.in);
            int Choice =sc.nextInt();
            input = MenuOption.values()[Choice - 1];

            
            switch(input)
            {
                case OPEN_ACCOUNT:
                {
                    System.out.println("Open Account : to be implemented in a later lab");
                    break;
                }
                case DEPOSIT:
                {
                    System.out.println("Deposit : to be implemented in a later lab");
                    break;
                }
                case WITHDRAW:
                {
                    System.out.println("Withdraw : to be implemented in a later lab");
                    break;
                }
                case TRANSFER:
                {
                    System.out.println("Transfer : to be implemented in a later lab");
                    break;
                }
                case EXIT:
                {
                    System.out.println("Good Bye!");
                    break;
                }
            }
            
        }  while(input!=MenuOption.EXIT);

        }
    }
}
