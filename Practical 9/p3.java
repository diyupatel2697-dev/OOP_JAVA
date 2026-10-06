class Sum
{
    int total = 0;

    synchronized void add(int value)
    {
        total += value;

        System.out.println(
            Thread.currentThread().getName()
            + " added " + value
            + " | Total = " + total
        );
    }
}

class SumThread extends Thread
{
    int[] arr;
    int start;
    int end;
    Sum sum;

    SumThread(int[] arr, int start, int end, Sum sum)
    {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.sum = sum;
    }

    public void run()
    {
        System.out.println(
            Thread.currentThread().getName()
            + " started | Index " + start
            + " to " + (end - 1)
        );

        for (int i = start; i < end; i++)
        {
            System.out.println(
                Thread.currentThread().getName()
                + " processing index " + i
                + " | Value = " + arr[i]
            );

            sum.add(arr[i]);
        }

        System.out.println(
            Thread.currentThread().getName()
            + " completed"
        );
    }
}

public class p3
{
    public static void main(String[] args) throws Exception
    {
        int[] arr = new int[20];

        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = i + 1;
        }

        Sum sum = new Sum();

        Thread t1 = new SumThread(arr, 0, 5, sum);
        Thread t2 = new SumThread(arr, 5, 10, sum);
        Thread t3 = new SumThread(arr, 10, 15, sum);
        Thread t4 = new SumThread(arr, 15, 20, sum);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");
        t4.setName("Thread-4");

        System.out.println("Starting Threads...\n");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("\nAll Threads Completed");
        System.out.println("Final Total: " + sum.total);
    }
}
