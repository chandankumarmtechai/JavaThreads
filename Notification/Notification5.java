import java.util.*;
import java.lang.*;
import java.util.concurrent.locks.*;

class MessageQueue
{
    private List<String> list;
    private int capacity;
    private Lock lock;
    private Condition condition;
    public MessageQueue(List<String> list, int capacity, Lock lock)
    {
        this.list = list;
        this.capacity = capacity;
        this.lock=lock;
        this.condition= lock.newCondition();
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
        condition.signal();
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
    public Consumer(MessageQueue queue)
    {
        this.queue=queue;
    }

    public void run(){
        int count =20;
        String s="";
        while(count > 0){
            try{
                s = queue.read();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("reading...  "+ s);
            count--;
        }
    }
}

public class Notification5{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<>(5);
        Lock lock = new ReentrantLock();
        MessageQueue queue = new MessageQueue(list, 5, lock);
        List<String> fruits = new ArrayList<>();
        fruits.add("mango");fruits.add("grapes");fruits.add("banana");fruits.add("apple");
        fruits.add("orange");fruits.add("pineapple");fruits.add("kiwi");
        fruits.add("apricot");fruits.add("lemon");fruits.add("anar");

        List<String> veg = new ArrayList<>();
        veg.add("potato");veg.add("brinjal");veg.add("cabbage");veg.add("tomato");
        veg.add("ladyfinger");veg.add("beans");veg.add("drumstick");veg.add("corn");
        veg.add("chilly");veg.add("capsicum");

        Producer p1 = new Producer(fruits, queue);
        Producer p2 = new Producer(veg, queue);
        Consumer c = new Consumer(queue);
        p1.start();
        p2.start();
        c.start();
    }
}