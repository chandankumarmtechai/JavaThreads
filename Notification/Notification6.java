import java.util.*;
import java.lang.*;
import java.util.concurrent.locks.*;

class MessageQueue
{
    private List<String> list;
    private int capacity;
    private Lock lock;
    private Condition condition;
    public int count;
    public MessageQueue(List<String> list, int capacity, Lock lock, int count)
    {
        this.list = list;
        this.capacity = capacity;
        this.lock=lock;
        this.condition= lock.newCondition();
        this.count = count;
    }
    public  String read() throws InterruptedException{
        lock.lock();
        while(list.size() < 1)
        {
            condition.await();
        }
        String msg = list.get(0);
        list.remove(0);
        condition.signal();
        lock.unlock();
        return msg;
    }
    public  void write(String msg) throws InterruptedException{
        lock.lock();
        while(list.size() == capacity)
        {
            condition.await();
        }
        list.add(msg);
        condition.signalAll();
        lock.unlock();
    }
}

class Producer extends Thread{
    private List<String> list;
    private MessageQueue queue;
    public Producer(List<String> list, MessageQueue queue)
    {
        this.list = list;
        this.queue =queue;
    }
    public void run(){
        for(int i=0; i<list.size(); i++)
        {
            System.out.println("writing......... "+ list.get(i));
            try{
                queue.write(list.get(i));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}

class Consumer extends Thread{
    private MessageQueue queue;
    private Lock lock;
    public Consumer(MessageQueue queue, Lock lock)
    {
        this.queue=queue;
        this.lock = lock;
    }

    public void run(){
        String s="";
        lock.lock();
        while(queue.count > 0){
            System.out.println(Thread.currentThread().getName() + " "+ queue.count);
            queue.count--;
            lock.unlock();
            try{
                s = queue.read();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"reading...  "+ s);
            lock.lock();
        }
        lock.unlock();
    }
}

public class Notification6{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<>(2);
        Lock lock = new ReentrantLock();
        Lock clock = new ReentrantLock();
        MessageQueue queue = new MessageQueue(list, 2, lock,10);
        List<String> fruits = new ArrayList<>();
        fruits.add("mango");
        fruits.add("grapes");
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("orange");
        fruits.add("pineapple");
        fruits.add("kiwi");
        fruits.add("apricot");
        fruits.add("lemon");
        fruits.add("anar");

        Producer p = new Producer(fruits, queue);
        Consumer c1 = new Consumer(queue,clock);
        Consumer c2 = new Consumer(queue,clock);
        Consumer c3 = new Consumer(queue,clock);
        p.start();
        c1.start();
        c2.start();
        c3.start();
    }
}