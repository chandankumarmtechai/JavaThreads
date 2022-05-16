import java.lang.*;
import java.util.*;

class MyThread extends Thread {
    public void run()
    {
        System.out.println("In run method thread state is "+ this.getState());
    }
}

public class Thread3
{
    public static void main(String[] args)
    {
        MyThread t = new MyThread();
        System.out.println("After creating thread state is "+ t.getState());
        t.start();
    }
}