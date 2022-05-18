import java.util.concurrent.locks.*;

class Message{
    private String msg;
    private ReentrantLock lock;

    public Message(String s, ReentrantLock lock)
    {
        this.msg = s;
        this.lock = lock;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public  void append(String apnd)
    {
        lock.lock();
        this.msg = this.msg+apnd;
        lock.unlock();
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

public class Locks4{
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Message m = new Message("hello",lock);
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