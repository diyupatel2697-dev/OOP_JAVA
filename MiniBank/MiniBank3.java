import java.util.Scanner;

public class MiniBank3
{
    // Record to store bank information (immutable)
    record BankInfo(String name, String branch){}

    // Enum containing all menu options
    enum MenuOption
    {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }

    public static class Customer implements Cloneable
    {
        private String name;
        private String email;
        private String mobile;
        private static long customerCounter = 100;
        private String customerId;
        private Address address;

        // Nested static Address class
        public static class Address {
            private String line;
            private String city;
            private String pincode;

            public Address(String line, String city, String pincode) {
                this.line = line;
                this.city = city;
                this.pincode = pincode;
            }

            public String getLine() { return line; }
            public String getCity() { return city; }
            public String getPincode() { return pincode; }

            @Override
            public String toString() {
                return line + ", " + city + " - " + pincode;
            }
        }

        public Customer(String name, String email, String mobile, Address address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.address = address;
            this.customerId = generateCustomerId();
        }

        private static String generateCustomerId() {
            customerCounter++;
            return "CSUT" + customerCounter;
        }

        public Address getAddress() {
            return address;
        }

        @Override
        public Customer clone() {
            try {
                return (Customer) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        @Override
        public String toString() {
            return "Customer [ID=" + customerId + ", Name=" + name + 
                   ", Email=" + email + ", Mobile=" + mobile + 
                   ", Address=" + address + "]";
        }
    }

    public static class Account
    {
        private String accountNumber;
        private String ownerName;
        private long balance;
        private static long accountCounter = 100;

        private static String generateAccountNumber() {
            accountCounter++;
            return "AB" + accountCounter;
        }

        Account(String ownerName, long openingBalance) {
            this.ownerName = ownerName;
            this.balance = openingBalance;
            this.accountNumber = generateAccountNumber();
        }

        Account(String ownerName) {
            this(ownerName, 0);
        }

        public long deposit(long amount) {
            this.balance += amount;
            return balance;
        }

        public boolean withdraw(long amount) {
            if(this.balance >= amount) {
                this.balance -= amount;
                return true;
            } else {
                System.out.println("Warning -- Balance is not sufficient.");
                return false;
            }
        }

        public long getBalance() {
            return balance;
        }

        @Override
        public String toString() {
            return "Account [No=" + accountNumber + ", Owner=" + ownerName + ", Balance=" + balance + "]";
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof Account)) return false;
            Account other = (Account) o;
            return this.accountNumber.equals(other.accountNumber);
        }

        @Override
        public int hashCode() {
            return accountNumber.hashCode();
        }
    }

    public static void main(String[] args) 
    {
        MenuOption input;
        BankInfo b = new BankInfo("HDFC","Nadiad");
        System.out.println("======Mini Bank======");
        System.out.println("Bank Name : " + b.name());
        System.out.println("Branch : " + b.branch());

        Account[] accounts = new Account[100];
        Customer[] customers = new Customer[100];

        do {
            MenuOption[] Menu = MenuOption.values();
            for(int i = 0; i < Menu.length; i++) {
                System.out.println((i + 1) + " : " + Menu[i]);
            }

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            input = MenuOption.values()[choice - 1];

            long CB;
            switch(input) {
                case OPEN_ACCOUNT: {
                    accounts[0] = new Account("Mayuri", 5000);
                    customers[0] = new Customer("Mayuri", "mayuri@gmail.com", "9876543210",
                                    new Customer.Address("123 Main Street", "Nadiad", "387001"));

                    CB = accounts[0].getBalance();
                    System.out.println(accounts[0]);   // toString()
                    System.out.println(customers[0]);  // toString()

                    // Clone demo
                    Customer cloneCustomer = customers[0].clone();
                    System.out.println("Cloned Customer: " + cloneCustomer);

                    break;     
                }

                case DEPOSIT: {
                    CB = accounts[0].deposit(1000);
                    System.out.println("Current Balance : " + CB);
                    break;
                }

                case WITHDRAW: {
                    boolean condition = accounts[0].withdraw(7000);
                    System.out.println("Condition (Sufficient Balance): " + condition);
                    CB = accounts[0].getBalance();
                    System.out.println("Current Balance : " + CB);
                    break;
                }

                case TRANSFER: {
                    System.out.println("Transfer : to be implemented in a later lab");
                    break;
                }

                case EXIT: {
                    System.out.println("Good Bye!");
                    break;
                }
            }

            // Demo equals() and instanceof
            if(accounts[0] != null) {
                Account anotherAcc = new Account("Diya", 5000);
                System.out.println("Accounts equal? " + accounts[0].equals(anotherAcc));
                System.out.println("accounts[0] instanceof Account? " + (accounts[0] instanceof Account));
            }

        } while(input != MenuOption.EXIT);
    }
}

