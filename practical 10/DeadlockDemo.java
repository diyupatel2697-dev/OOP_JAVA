class Account
{
    int accountNumber;
    int balance;

    Account(int accountNumber, int balance)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}

public class DeadlockDemo
{
    public static void main(String[] args)
    {
        Account accountA = new Account(101, 5000);
        Account accountB = new Account(102, 5000);

        Thread t1 = new Thread(() ->
        {
            synchronized (accountA)
            {
                System.out.println("Thread 1 locked Account A");

                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }

                synchronized (accountB)
                {
                    System.out.println("Thread 1 locked Account B");
                }
            }
        });

        Thread t2 = new Thread(() ->
        {
            synchronized (accountB)
            {
                System.out.println("Thread 2 locked Account B");

                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }

                synchronized (accountA)
                {
                    System.out.println("Thread 2 locked Account A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
