import java.util.concurrent.atomic.AtomicInteger;

class MyThread extends Thread{
    AtomicInteger counter;
    public MyThread(AtomicInteger counter, String name)
    {
        super(name);
        this.counter = counter;
    }
    public void run()
    {
        //int a = counter.incrementAndGet();
        //int a = counter.updateAndGet(x->x+1);
        int a = counter.accumulateAndGet(1, (c,b)->{
            c = c*2;
            return c+b;
        });
        System.out.println(Thread.currentThread().getName() + " "+ a);
    }
}

public class AtomicVariable1 {
    public static void main(String[] args) throws InterruptedException
    {
        AtomicInteger counter = new AtomicInteger();
        int N = 5;
        MyThread[] th = new MyThread[N];
        for(int i=0; i<N; i++)
        {
            th[i] = new MyThread(counter, "thread "+ i);
        }
        for(int i=0; i<N; i++)
            th[i].start();
        for(int i=0; i<N; i++)
            th[i].join();
        
        System.out.println("main thread " + counter.get());
    }
}
