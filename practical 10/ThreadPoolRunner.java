import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner
{
    public static void main(String[] args) throws Exception
    {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++)
        {
            int taskId = i;

            pool.execute(() ->
            {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();

        pool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("All tasks completed.");
    }
}