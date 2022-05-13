import java.lang.*;

class MyThread extends Thread{

    public void run()
    {
        System.out.println("greeting from my thread");
    }
}

public class Thread1
{

    public static void main(String[] args)
    {
        Thread t1 = new MyThread();
        t1.start();
    }
}