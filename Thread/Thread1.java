import java.lang.*;

class MyThread extends Thread{
    public MyThread(){}
    public MyThread(Runnable rn)
    {
        super(rn);
    }

    public void run()
    {
        System.out.println("greeting from my thread");
    }
}

class MyThread2 implements Runnable{
    
    public void run()
    {
        System.out.println("Greeting from thread2 ");
    }
}

class MyThread3 extends Thread{
    public MyThread3(){}
    public MyThread3(Runnable rn)
    {
        super(rn);
    }
}

public class Thread1
{

    public static void main(String[] args)
    {
        Thread t1 = new MyThread();
        //MyThread t1 = new MyThread();
        t1.start();

        Runnable rn =  new MyThread2();
        Thread t2 = new Thread(rn);
        t2.start();

        MyThread3 t3 = new MyThread3(rn);
        t3.start();
    }
}