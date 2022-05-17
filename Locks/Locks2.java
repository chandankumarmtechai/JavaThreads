import java.util.*;
import java.lang.*;

class Message{
    private String msg;

    public Message(String s)
    {
        this.msg = s;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public  void append(String apnd)
    {
        synchronized(this){
            this.msg = this.msg+apnd;
        }
    }
}
class MyThread extends Thread{
    private Message msg;
    public MyThread(Message m)
    {
        this.msg = m;
    }
    public void run(){
        System.out.println("Running " + this.getName());
        this.msg.append(this.getName());
    }
}
public class Locks2{

    public static void main(String[] args)
    {
        Message m = new Message("hello");
        MyThread t1 = new MyThread(m);
        MyThread t2 = new MyThread(m);
        MyThread t3 = new MyThread(m);
        MyThread t4 = new MyThread(m);
        MyThread t5 = new MyThread(m);
        MyThread t6 = new MyThread(m);

        t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();
        
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(m.getMsg());
    }
}