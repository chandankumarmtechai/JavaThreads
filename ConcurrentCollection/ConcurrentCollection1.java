import java.util.*;
import java.lang.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.*;

class Producer extends Thread{
    private List<String> list;
    private BlockingQueue<String> queue;
    public Producer(List<String> list, BlockingQueue<String> queue)
    {
        this.list = list;
        this.queue =queue;
    }
    public void run(){
        for(int i=0; i<list.size(); i++)
        {
            System.out.println(Thread.currentThread().getName() + " writing... "+ list.get(i));
            try{
                //queue.write(list.get(i));
                queue.put(list.get(i));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}

class Consumer extends Thread{
    private BlockingQueue queue;
    public Consumer(BlockingQueue queue)
    {
        this.queue=queue;
    }

    public void run(){
        String s = "";
        try{
            while(true)
            {
                s= (String)this.queue.take();
                System.out.println(Thread.currentThread().getName()+ " Reading..........  " + s);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

public class ConcurrentCollection1{
    public static void main(String[] args)
    {
        BlockingQueue queue = new ArrayBlockingQueue<>(5,true);
        List<String> fruits = new ArrayList<>();
        fruits.add("mango");fruits.add("grapes");fruits.add("banana");fruits.add("apple");
        fruits.add("orange");fruits.add("pineapple");fruits.add("kiwi");fruits.add("apricot");
        fruits.add("lemon");fruits.add("anar");

        List<String> veg = new ArrayList<>();
        veg.add("potato");veg.add("tomato");veg.add("brinjal");veg.add("cabbage");veg.add("drumstick");
        veg.add("cauliflower");veg.add("mint");veg.add("coriander");veg.add("snakegaurd");

        Producer p1 = new Producer(fruits, queue);
        Producer p2 = new Producer(veg, queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        p1.start();p2.start();
        c1.start();c2.start();c3.start();
    }
}