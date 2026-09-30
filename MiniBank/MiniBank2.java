import java.util.Scanner;

public class MiniBank2 
{
    // Record to store bank information (immutable)
    record BankInfo(String name, String branch){}

    // Enum containing all menu options
    enum MenuOption
    {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }

    public static class Customer
    {
        private  String name;
        private  String email;
        private  String mobile;
        private static final String customerid="CSUT101";
        private static long customerCounter=100;

         private static String generateCustomerid()
         {
            customerCounter++;
            // System.out.println("Coustomer ID : CSUT" +customerCounter);
            return "CSUT" + customerCounter;
        }

    }

    public static class Account
    {
        private final String accountNumber="AB0001";
        private String ownerName;
        private long balance;
        boolean active;
        private static long accountCounter=100;

        
       
        private static String generateaccountNumber()
        {
            accountCounter++;
            //System.out.println("Account Number : AB"+accountCounter);
            return "AB" + accountCounter;
            
        }

        Account(String ownername,long opening_balance)
        {
            this.ownerName=ownername;
            this.balance=opening_balance;
        }

        Account(String ownername)
        {
            this(ownername, 0);
        }

        public long deposit(long amount)
        {
            this.balance +=  amount;
            return balance;
        }

        public boolean withdraw(long amount)
        {
            if(this.balance>=amount)
            {
                this.balance -= amount;
                 return true;
            }
            else
            {
                System.out.println("Warning --Balance is not Sufficient:");
                return false;
            }
        }

        public long getbalance()
            {
                return balance;
            }
    }

    public static void main(String[] args) 
    {
        // Variable declared outside the loop so it can be used in while condition
        MenuOption input;


        BankInfo b = new BankInfo("HDFC","Nadiad");
        System.out.println("======Mini Bank======");
        System.out.println("Bank Name : " +b.name());
        System.out.println("Branch : " +b.branch());

        Account[] accounts =new Account[100];
        Customer[] customers = new Customer[100];

        // Loop runs until user selects EXIT
        do
        {
            // values() returns all enum constants as an array
            MenuOption[] Menu = MenuOption.values();

            // Display menu with numbering
            for(int i = 0; i < Menu.length; i++)
            {
                System.out.println((i + 1) + " : " + Menu[i]);
            }

            // Read user's choice
            Scanner sc = new Scanner(System.in);
            int Choice = sc.nextInt();

            // Convert user's number into corresponding enum constant
            // Example: 1 -> OPEN_ACCOUNT, 2 -> DEPOSIT
            input = MenuOption.values()[Choice - 1];

            long CB;
            // Execute action based on selected enum
            switch(input)
            {
                case OPEN_ACCOUNT:
                {
                    accounts[0] = new Account("Diya",5000);
                    customers[0] = new Customer();
                    String ACNO =accounts[0].generateaccountNumber();
                    String CID = customers[0].generateCustomerid();
                    CB =accounts[0].getbalance();

                    System.out.println("Account No : "+ACNO);
                    System.out.println("Customer No : "+CID);
                    System.out.println("Current Balance : "+CB);

                    break;     
                }

                case DEPOSIT:
                {
                    CB = accounts[0].deposit(1000);
                    System.out.println("Current Balance : "+CB);

                    break;
                }

                case WITHDRAW:
                {
                    boolean condition = accounts[0].withdraw(7000);
                    System.out.println("Conditon (Have a Sufficient Balance):" +condition);
                    CB = accounts[0].getbalance();
                    System.out.println("Current Balance : "+CB);


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

        }
        // Repeat menu until EXIT is selected
        while(input != MenuOption.EXIT);
        
    }
}

