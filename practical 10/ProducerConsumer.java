class Buffer
{
    private int value;
    private boolean available = false;

    synchronized void produce(int value) throws InterruptedException
    {
        while (available)
        {
            wait();
        }

        this.value = value;
        available = true;

        System.out.println("Produced: " + value);

        notify();
    }

    synchronized int consume() throws InterruptedException
    {
        while (!available)
        {
            wait();
        }

        int result = value;
        available = false;

        System.out.println("Consumed: " + result);

        notify();

        return result;
    }
}

class Producer extends Thread
{
    Buffer buffer;

    Producer(Buffer buffer)
    {
        this.buffer = buffer;
    }

    public void run()
    {
        try
        {
            for (int i = 1; i <= 5; i++)
            {
                buffer.produce(i);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread
{
    Buffer buffer;

    Consumer(Buffer buffer)
    {
        this.buffer = buffer;
    }

    public void run()
    {
        try
        {
            for (int i = 1; i <= 5; i++)
            {
                buffer.consume();
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumer
{
    public static void main(String[] args) throws Exception
    {
        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Production and consumption completed.");
    }
}
