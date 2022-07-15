import java.util.concurrent.*;
import java.lang.Thread;

public class ThreadPool2 {
    public static void main(String[] args) throws InterruptedException,ExecutionException
    {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //executor.execute(() -> System.out.println(Thread.currentThread().getName() + 
        //" Hello World"));
        Future<String> future = executor.submit(() -> "Hello World");
        String result = future.get();
        System.out.println(result);
        executor.shutdown();
    }
}
