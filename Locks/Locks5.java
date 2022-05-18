class MyLock
{
    int flag;
}

class Message{
    private String msg;
    private MyLock lock;

    public Message(String s, MyLock lock)
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
        synchronized(lock){
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

public class Locks5{
    public static void main(String[] args) {
        MyLock lock = new MyLock();
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