import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
public class MiniBank14
{
    @Retention(RetentionPolicy.RUNTIME)

    @Target(ElementType.FIELD)
    @interface Id
    {
    }

    @Retention(RetentionPolicy.RUNTIME)

    @Target(ElementType.FIELD)
    @interface Positive
    {
    }

    @Retention(RetentionPolicy.RUNTIME)

    @Target(ElementType.FIELD)
    @interface MaxLength
    {
        int value();
    }

    interface Transactable
    {
        void deposit(long amount);
        boolean withdraw(long amount);
    }

    interface InterestBearing
    {
        double interestRate();
    }

    interface WithdrawRule
    {
        boolean canWithdraw(long amount);
    }

    interface Premium
    {
    }

    static class Validator
    {
        private static final Pattern MOBILE = Pattern.compile("^[6-9][0-9]{9}$");
        private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        private static final Pattern PAN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
        private static final Pattern IFSC = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

        static boolean isMobile(String value)
        {
            return MOBILE.matcher(value).matches();
        }

        static boolean isEmail(String value)
        {
            return EMAIL.matcher(value).matches();
        }

        static boolean isPAN(String value)
        {
            return PAN.matcher(value).matches();
        }

        static boolean isIFSC(String value)
        {
            return IFSC.matcher(value).matches();
        }
    }

    static class AnnotationValidator
    {
        static boolean validate(Object object)
        {
            boolean valid = true;
            for (Field field : object.getClass().getDeclaredFields())
            {
                try
                {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (field.isAnnotationPresent(Id.class))
                    {
                        if (value == null || value.toString().isEmpty())
                        {
                            valid = false;
                        }
                    }
                    if (field.isAnnotationPresent(Positive.class))
                    {
                        if (value instanceof Number && ((Number) value).doubleValue() <= 0)
                        {
                            valid = false;
                        }
                    }
                    if (field.isAnnotationPresent(MaxLength.class))
                    {
                        if (value != null && value.toString().length() > field.getAnnotation(MaxLength.class).value())
                        {
                            valid = false;
                        }
                    }
                }
                catch (Exception e)
                {
                    valid = false;
                }
            }
            return valid;
        }
    }

    enum TransactionType
    {
        DEPOSIT,
        WITHDRAW
    }

    record Command(TransactionType type, String accountNumber, long amount)
    {
    }

    static class CommandParser
    {
        static Command parse(String line)
        {
            String[] parts = line.split("\\s+");
            TransactionType type = TransactionType.valueOf(parts[0]);
            String accountNumber = parts[1];
            long amount = Long.parseLong(parts[2]);
            return new Command(type, accountNumber, amount);
        }
    }

    static class StatementFormatter
    {
        static String format(String accountNumber, String type, long amount)
        {
            return accountNumber + " | " + type + " | " + amount;
        }
    }

    static class Customer implements Cloneable, Serializable
    {
        private String name;
        private Address address;
        Customer(String name, Address address)
        {
            this.name = name;
            this.address = address;
        }

        static class Address implements Serializable
        {
            private String city;
            Address(String city)
            {
                this.city = city;
            }

            public String getCity()
            {
                return city;
            }

            public void setCity(String city)
            {
                this.city = city;
            }

            @Override

            public String toString()
            {
                return city;
            }
        }

        public Address getAddress()
        {
            return address;
        }

        @Override

        protected Customer clone()
        {
            try
            {
                Customer copy = (Customer) super.clone();
                copy.address = new Address(address.city);
                return copy;
            }
            catch (CloneNotSupportedException e)
            {
                throw new RuntimeException(e);
            }
        }

        @Override

        public String toString()
        {
            return name + " - " + address;
        }
    }

    static abstract class Account implements Transactable, InterestBearing, WithdrawRule, Serializable, Comparable<Account>
    {
        @Id
        protected String accountNumber;
        @MaxLength(50)
        protected String holderName;
        @Positive
        protected long balance;
        Account(String holderName, long balance, int accountNumber)
        {
            this.holderName = holderName;
            this.balance = balance;
            this.accountNumber = "ACC" + (1000 + accountNumber);
        }

        public String getAccountNumber()
        {
            return accountNumber;
        }

        public String getHolderName()
        {
            return holderName;
        }

        public long balance()
        {
            return balance;
        }

        @Override

        public synchronized void deposit(long amount)
        {
            if (amount > 0)
            {
                balance += amount;
            }
        }

        @Override

        public synchronized boolean withdraw(long amount)
        {
            if (canWithdraw(amount))
            {
                balance -= amount;
                return true;
            }
            return false;
        }

        @Override

        public boolean canWithdraw(long amount)
        {
            return amount > 0 && amount <= balance;
        }

        @Override

        public int compareTo(Account other)
        {
            return this.accountNumber.compareTo(other.accountNumber);
        }

        @Override

        public boolean equals(Object object)
        {
            if (this == object)
            {
                return true;
            }
            if (!(object instanceof Account))
            {
                return false;
            }
            Account other = (Account) object;
            return accountNumber.equals(other.accountNumber);
        }

        @Override

        public String toString()
        {
            return accountNumber + " | " + holderName + " | " + balance;
        }
    }

    static class SavingsAccount extends Account implements Premium
    {
        SavingsAccount(String holderName, long balance, int accountNumber)
        {
            super(holderName, balance, accountNumber);
        }

        @Override

        public double interestRate()
        {
            return 0.04;
        }
    }

    static class CurrentAccount extends Account
    {
        CurrentAccount(String holderName, long balance, int accountNumber)
        {
            super(holderName, balance, accountNumber);
        }

        @Override

        public double interestRate()
        {
            return 0.02;
        }

        @Override

        public boolean canWithdraw(long amount)
        {
            return amount > 0 && amount <= balance + 1000;
        }
    }

    static class FixedDepositAccount extends Account
    {
        FixedDepositAccount(String holderName, long balance, int accountNumber)
        {
            super(holderName, balance, accountNumber);
        }

        @Override

        public double interestRate()
        {
            return 0.07;
        }

        @Override

        public boolean canWithdraw(long amount)
        {
            return false;
        }
    }

    static class AccountWorker implements Runnable
    {
        private Account account;
        private int times;
        private long amount;
        AccountWorker(Account account, int times, long amount)
        {
            this.account = account;
            this.times = times;
            this.amount = amount;
        }

        @Override

        public void run()
        {
            for (int i = 0; i < times; i++)
            {
                synchronized (account)
                {
                    account.deposit(amount);
                }
            }
            System.out.println(Thread.currentThread().getName() + " completed");
        }
    }

    static class TransactionProcessor
    {
        private ExecutorService executor;
        private ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

        public void addAccount(Account account)
        {
            sharedAccounts.put(account.getAccountNumber(), account);
        }

        public Account getAccount(String accountNumber)
        {
            return sharedAccounts.get(accountNumber);
        }

        public ConcurrentHashMap<String, Account> getSharedAccounts()
        {
            return sharedAccounts;
        }
        TransactionProcessor()
        {
            executor = Executors.newFixedThreadPool(4);
        }

        public void submit(Runnable task)
        {
            executor.execute(task);
        }

        public void stop() throws InterruptedException
        {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    static class Transaction
    {
        String type;
        Account account;
        long amount;
        Transaction(String type, Account account, long amount)
        {
            this.type = type;
            this.account = account;
            this.amount = amount;
        }
    }

    static class TransactionBuffer
    {
        private Transaction[] buffer = new Transaction[5];
        private int count = 0;

        public synchronized void add(Transaction transaction) throws InterruptedException
        {
            while (count == buffer.length)
            {
                wait();
            }
            buffer[count] = transaction;
            count++;
            System.out.println("Produced: " + transaction.type + " " + transaction.amount);
            notify();
        }

        public synchronized Transaction remove() throws InterruptedException
        {
            while (count == 0)
            {
                wait();
            }
            Transaction transaction = buffer[0];
            for (int i = 1; i < count; i++)
            {
                buffer[i - 1] = buffer[i];
            }
            count--;
            System.out.println("Consumed: " + transaction.type + " " + transaction.amount);
            notify();
            return transaction;
        }
    }

    static void transfer(Account from, Account to, long amount)
    {
        Account first;
        Account second;
        if (from.getAccountNumber().compareTo(to.getAccountNumber()) < 0)
        {
            first = from;
            second = to;
        }
        else
        {
            first = to;
            second = from;
        }
        synchronized (first)
        {
            synchronized (second)
            {
                if (from.withdraw(amount))
                {
                    to.deposit(amount);
                    System.out.println(
                        "Transfer completed: " +
                        from.getAccountNumber() +
                        " -> " +
                        to.getAccountNumber() +
                        " | Amount: " +
                        amount
                    );
                }
            }
        }
    }

    static class AccountStore
    {
        private HashMap<String, Account> accounts = new HashMap<>();
        private ArrayList<String> transactionHistory = new ArrayList<>();

        public void add(Account account)
        {
            accounts.put(account.getAccountNumber(), account);
        }

        public Account get(String accountNumber)
        {
            return accounts.get(accountNumber);
        }

        public ArrayList<Account> getAll()
        {
            return new ArrayList<>(accounts.values());
        }

        public void addTransaction(String transaction)
        {
            transactionHistory.add(transaction);
        }

        public ArrayList<String> getTransactionHistory()
        {
            return new ArrayList<>(transactionHistory);
        }

        public ArrayList<Account> sortedByBalance()
        {
            ArrayList<Account> sorted = getAll();
            sorted.sort(Comparator.comparingLong(Account::balance).reversed());
            return sorted;
        }
    }

    static class Repository<T>
    {
        private HashMap<String, T> items = new HashMap<>();

        public T save(T entity)
        {
            try
            {
                Class<?> type = entity.getClass();
                while (type != null)
                {
                    for (Field field : type.getDeclaredFields())
                    {
                        if (field.isAnnotationPresent(Id.class))
                        {
                            field.setAccessible(true);
                            Object value = field.get(entity);
                            if (value == null)
                            {
                                throw new IllegalArgumentException("ID cannot be null");
                            }
                            return items.put(value.toString(), entity);
                        }
                    }
                    type = type.getSuperclass();
                }
                throw new IllegalArgumentException("No field marked with @Id found");
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
        }

        @SafeVarargs
        public final void saveAll(T... entities)
        {
            for (T entity : entities)
            {
                save(entity);
            }
        }

        public T findById(String id)
        {
            return items.get(id);
        }

        public ArrayList<T> findAll()
        {
            return new ArrayList<>(items.values());
        }

        public boolean delete(String id)
        {
            return items.remove(id) != null;
        }

        public int size()
        {
            return items.size();
        }
    }

    static <N extends Number> double average(N[] values)
    {
        if (values.length == 0)
        {
            return 0.0;
        }
        double total = 0;
        for (N value : values)
        {
            total += value.doubleValue();
        }
        return total / values.length;
    }

    static class StatePersister
    {
        public static void save(Account[] accounts, Path file) throws IOException
        {
            Path parent = file.getParent();
            if (parent != null)
            {
                Files.createDirectories(parent);
            }
            try (ObjectOutputStream out = new ObjectOutputStream(
                    Files.newOutputStream(file)))
            {
                out.writeObject(accounts);
            }
        }

        public static Account[] load(Path file)
                throws IOException, ClassNotFoundException
        {
            try (ObjectInputStream in = new ObjectInputStream(
                    Files.newInputStream(file)))
            {
                return (Account[]) in.readObject();
            }
        }
    }

    static class TransactionLog
    {
        public static void append(Path file, String line) throws IOException
        {
            Path parent = file.getParent();
            if (parent != null)
            {
                Files.createDirectories(parent);
            }
            Files.write(file, (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }

    static class ReportGenerator
    {
        public static String generate(Path logsFolder, Path reportFile) throws IOException
        {
            long totalDeposits = 0;
            long totalWithdrawals = 0;
            StringBuilder report = new StringBuilder();
            if (Files.exists(logsFolder))
            {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsFolder, "*.log"))
                {
                    for (Path file : stream)
                    {
                        if (!Files.isRegularFile(file) || Files.size(file) == 0)
                        {
                            continue;
                        }
                        BasicFileAttributes attributes = Files.readAttributes(
                                file, BasicFileAttributes.class);
                        report.append("File: ").append(file.getFileName()).append('\n');
                        report.append("Size: ").append(attributes.size()).append(" bytes\n");
                        report.append("Last modified: ").append(attributes.lastModifiedTime()).append('\n');
                        try (BufferedReader reader = Files.newBufferedReader(file))
                        {
                            String line;
                            while ((line = reader.readLine()) != null)
                            {
                                String[] parts = line.trim().split("\\s+");
                                if (parts.length >= 3)
                                {
                                    try
                                    {
                                        long amount = Long.parseLong(parts[2]);
                                        if (parts[0].equalsIgnoreCase("DEPOSIT"))
                                        {
                                            totalDeposits += amount;
                                        }
                                        else if (parts[0].equalsIgnoreCase("WITHDRAW"))
                                        {
                                            totalWithdrawals += amount;
                                        }
                                    }
                                    catch (NumberFormatException ignored)
                                    {
                                    }
                                }
                            }
                        }
                        report.append('\n');
                    }
                }
            }
            report.append("Total deposits: ").append(totalDeposits).append('\n');
            report.append("Total withdrawals: ").append(totalWithdrawals).append('\n');
            report.append("Net change: ").append(totalDeposits - totalWithdrawals).append('\n');
            Path parent = reportFile.getParent();
            if (parent != null)
            {
                Files.createDirectories(parent);
            }
            Files.write(reportFile, report.toString().getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return report.toString();
        }
    }


    static class BankService
    {
        private final Repository<Account> repository = new Repository<>();
        private final TransactionProcessor processor = new TransactionProcessor();
        private final Path accountsFile = Paths.get("data", "accounts.dat");
        private final Path logsFolder = Paths.get("data", "logs");
        private final Path transactionFile = logsFolder.resolve("transactions.log");
        private final Path reportFile = Paths.get("data", "report.txt");

        BankService() throws Exception
        {
            if (Files.exists(accountsFile))
            {
                for (Account account : StatePersister.load(accountsFile))
                {
                    repository.save(account);
                    processor.addAccount(account);
                }
            }
        }

        public Account openAccount(String name, long initialBalance, int type, int id) throws IOException
        {
            Account account;
            if (type == 1)
            {
                account = new SavingsAccount(name, initialBalance, id);
            }
            else if (type == 2)
            {
                account = new CurrentAccount(name, initialBalance, id);
            }
            else
            {
                throw new IllegalArgumentException("Invalid account type");
            }
            if (!AnnotationValidator.validate(account))
            {
                throw new IllegalArgumentException("Invalid account details or balance");
            }
            if (repository.findById(account.getAccountNumber()) != null)
            {
                throw new IllegalArgumentException("Account number already exists");
            }
            repository.save(account);
            processor.addAccount(account);
            TransactionLog.append(transactionFile, "OPEN " + account.getAccountNumber() + " " + initialBalance);
            return account;
        }

        public void deposit(String id, long amount) throws IOException
        {
            Account account = requireAccount(id);
            if (amount <= 0)
            {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            account.deposit(amount);
            TransactionLog.append(transactionFile, "DEPOSIT " + id + " " + amount);
        }

        public void withdraw(String id, long amount) throws IOException
        {
            Account account = requireAccount(id);
            if (!account.withdraw(amount))
            {
                throw new IllegalArgumentException("Invalid amount or insufficient balance");
            }
            TransactionLog.append(transactionFile, "WITHDRAW " + id + " " + amount);
        }

        public void transfer(String fromId, String toId, long amount) throws IOException
        {
            Account from = requireAccount(fromId);
            Account to = requireAccount(toId);
            if (from == to)
            {
                throw new IllegalArgumentException("Source and destination must be different");
            }
            Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
            Account second = first == from ? to : from;
            synchronized (first)
            {
                synchronized (second)
                {
                    if (!from.withdraw(amount))
                    {
                        throw new IllegalArgumentException("Invalid amount or insufficient balance");
                    }
                    to.deposit(amount);
                }
            }
            TransactionLog.append(transactionFile, "WITHDRAW " + fromId + " " + amount);
            TransactionLog.append(transactionFile, "DEPOSIT " + toId + " " + amount);
        }

        public String statement(String id)
        {
            return requireAccount(id).toString();
        }

        public ArrayList<Account> listAccounts()
        {
            return repository.findAll();
        }

        public void saveAccounts() throws IOException
        {
            StatePersister.save(repository.findAll().toArray(new Account[0]), accountsFile);
        }

        public String generateReport() throws IOException
        {
            return ReportGenerator.generate(logsFolder, reportFile);
        }

        public void processBatch(String[] commands) throws Exception
        {
            for (String line : commands)
            {
                if (line == null || line.trim().isEmpty())
                {
                    continue;
                }
                Command command = CommandParser.parse(line);
                Account account = requireAccount(command.accountNumber());
                processor.addAccount(account);
                processor.submit(() ->
                {
                    try
                    {
                        if (command.type() == TransactionType.DEPOSIT)
                        {
                            deposit(command.accountNumber(), command.amount());
                        }
                        else
                        {
                            withdraw(command.accountNumber(), command.amount());
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Batch transaction failed: " + e.getMessage());
                    }
                });
            }
            processor.stop();
        }

        private Account requireAccount(String id)
        {
            Account account = repository.findById(id);
            if (account == null)
            {
                throw new IllegalArgumentException("Account not found: " + id);
            }
            return account;
        }
    }

    public static void main(String[] args) throws Exception
    {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        BankService bank = new BankService();
        boolean running = true;
        while (running)
        {
            System.out.println("\n===== MiniBank Menu =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Statement");
            System.out.println("6. List Accounts");
            System.out.println("7. End-of-Day Report");
            System.out.println("8. Process Batch Transactions");
            System.out.println("9. Save and Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            try
            {
                switch (choice)
                {
                    case "1" ->
                    {
                        System.out.print("Holder name: ");
                        String name = sc.nextLine();
                        System.out.print("Initial balance: ");
                        long balance = Long.parseLong(sc.nextLine());
                        System.out.print("Type (1-Savings, 2-Current): ");
                        int type = Integer.parseInt(sc.nextLine());
                        System.out.print("Account ID number: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.println("Created: " + bank.openAccount(name, balance, type, id));
                    }
                    case "2" ->
                    {
                        System.out.print("Account number: ");
                        String id = sc.nextLine();
                        System.out.print("Deposit amount: ");
                        long amount = Long.parseLong(sc.nextLine());
                        bank.deposit(id, amount);
                        System.out.println("Deposit successful.");
                    }
                    case "3" ->
                    {
                        System.out.print("Account number: ");
                        String id = sc.nextLine();
                        System.out.print("Withdrawal amount: ");
                        long amount = Long.parseLong(sc.nextLine());
                        bank.withdraw(id, amount);
                        System.out.println("Withdrawal successful.");
                    }
                    case "4" ->
                    {
                        System.out.print("From account: ");
                        String from = sc.nextLine();
                        System.out.print("To account: ");
                        String to = sc.nextLine();
                        System.out.print("Amount: ");
                        long amount = Long.parseLong(sc.nextLine());
                        bank.transfer(from, to, amount);
                        System.out.println("Transfer successful.");
                    }
                    case "5" ->
                    {
                        System.out.print("Account number: ");
                        System.out.println(bank.statement(sc.nextLine()));
                    }
                    case "6" ->
                    {
                        for (Account account : bank.listAccounts())
                        {
                            System.out.println(account);
                        }
                    }
                    case "7" -> System.out.println(bank.generateReport());
                    case "8" ->
                    {
                        System.out.println("Enter transactions as DEPOSIT accountNumber amount or WITHDRAW accountNumber amount.");
                        System.out.println("Enter done to finish batch input.");
                        ArrayList<String> commands = new ArrayList<>();
                        while (true)
                        {
                            String line = sc.nextLine();
                            if (line.equalsIgnoreCase("done"))
                            {
                                break;
                            }
                            commands.add(line);
                        }
                        bank.processBatch(commands.toArray(new String[0]));
                        System.out.println("Batch processing completed.");
                    }
                    case "9" ->
                    {
                        bank.saveAccounts();
                        running = false;
                        System.out.println("Accounts saved. Goodbye.");
                    }
                    default -> System.out.println("Invalid menu choice.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
