import java.util.concurrent.*;
import java.lang.Thread;

public class ThreadPool1 {
    public static void main(String[] args)
    {
        //Executor executor = Executors.newSingleThreadExecutor();
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> System.out.println(Thread.currentThread().getName() + 
        " Hello World"));
    }
}
