import java.util.*;
import java.lang.*;

class MessageQueue
{
    private List<String> list;
    private int capacity;
    public MessageQueue(List<String> list, int capacity)
    {
        this.list = list;
        this.capacity = capacity;
    }
    public synchronized String read() throws InterruptedException{
        while(list.size() < 1)
        {
            wait();
        }
        String msg = list.get(0);
        list.remove(0);
        notify();
        return msg;
    }
    public synchronized void write(String msg) throws InterruptedException{
        while(list.size() == capacity)
        {
            wait();
        }
        list.add(msg);
        notify();
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
        int count =10;
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

public class Notification4{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<>(7);
        MessageQueue queue = new MessageQueue(list, 7);
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
        Consumer c = new Consumer(queue);
        p.start();
        c.start();
    }
}