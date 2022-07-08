import java.util.*;
import java.lang.*;
import java.util.concurrent.Semaphore;

class MessageQueue
{
    private int[] list;
    private int capacity;
    private int N;
    private Semaphore mutex, empty, full;
    public MessageQueue(int N, int capacity)
    {
        this.list = new int[capacity];
        this.capacity = capacity;
        this.N = N;
        this.mutex = new Semaphore(1, true);
        this.empty = new Semaphore(capacity,true);
        this.full = new Semaphore(0,true);
    }
    public  boolean read() throws InterruptedException{
        for(int i=0; i<this.N; i++)
        {
            full.acquire();
            mutex.acquire();
            System.out.println("consume " + i + " from " + i%capacity + " " + list[i%capacity]);
            if(list[i%this.capacity] != i)
                return false;
            mutex.release();
            empty.release();
        }
        return true;
    }
    public  void write() throws InterruptedException{
        for(int i=0; i<this.N; i++)
        {
            empty.acquire();
            mutex.acquire();
            System.out.println("produce " + i +  " at " + i%capacity + " " +  i);
            this.list[i%this.capacity] =  i;
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

public class Semaphore1
{
    public static void main(String[] args) throws InterruptedException
    {
        MessageQueue msg = new MessageQueue( 1100, 10);
        Producer p = new Producer(msg);
        Consumer c = new Consumer(msg);
        p.start();
        c.start();
        p.join();
        c.join();
        System.out.println("main thread");
    }
}