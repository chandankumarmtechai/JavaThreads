class Message{
    public static String msg = "hello";

    public static synchronized void append(String appnd)
    {
        msg =  msg+appnd;
    }
}

class MyThread extends Thread{
    
    public void run(){
        System.out.println("Running " + this.getName());
        Message.append(this.getName());
    }
}

public class Locks3{

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        MyThread t4 = new MyThread();
        MyThread t5 = new MyThread();
        MyThread t6 = new MyThread();

        t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();
        
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Message.msg);
    }
}