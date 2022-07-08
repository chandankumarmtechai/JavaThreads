import java.util.*;
import java.lang.*;
import java.util.concurrent.Semaphore;

class MessageQueue
{
    private int[] list;
    private int capacity;
    private int N;
    private int nexti;
    private int nextv;
    private Semaphore mutex, empty, full;
    public MessageQueue(int N, int capacity)
    {
        this.list = new int[capacity];
        this.capacity = capacity;
        this.N = N;
        this.nexti=0;
        this.nextv=0;
        this.mutex = new Semaphore(1, true);
        this.empty = new Semaphore(capacity,true);
        this.full = new Semaphore(0,true);
    }
    public  boolean read() throws InterruptedException{
        for(int i=0; i<this.N; i++)
        {
            full.acquire();
            mutex.acquire();
            System.out.println(Thread.currentThread().getName()+" consume " + i + " from " + i%capacity + " " + list[i%capacity]);
            if(list[i%this.capacity] != i)
                return false;
            mutex.release();
            empty.release();
        }
        return true;
    }
    public  void write() throws InterruptedException{
        for(;;)
        {
            empty.acquire();
            mutex.acquire();
            if(nexti >= N)
            {
                empty.release();
                mutex.release();
                return;
            }
            System.out.println(Thread.currentThread().getName()+" produce " + nextv +  " at " + nexti%capacity + " " +  nextv);
            this.list[nexti%capacity] =  nextv;
            nexti++;
            nextv++;
            mutex.release();
            full.release();
        }
    }
}

class Producer extends Thread{
    private MessageQueue queue;
    public Producer(MessageQueue queue)
    {
        this.queue =queue;
    }
    public void run() {
        try{
            queue.write();
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
class Consumer extends Thread{
    private MessageQueue queue;
    public Consumer(MessageQueue queue)
    {
        this.queue=queue;
    }

    public void run(){
        try{
            boolean b = queue.read();
            System.out.println(b);
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

public class Semaphore2
{
    public static void main(String[] args) throws InterruptedException
    {
        MessageQueue msg = new MessageQueue( 1100, 15);
        Producer p = new Producer(msg);
        Producer p1 = new Producer(msg);
        Producer p2 = new Producer(msg);
        Producer p3 = new Producer(msg);
        Consumer c = new Consumer(msg);
        p.start();
        p1.start();p2.start();p3.start();
        c.start();
        p.join();
        p1.join();p2.join();p3.join();
        c.join();
        System.out.println("main thread " + Thread.currentThread().getName());
    }
}