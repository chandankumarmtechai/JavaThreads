import java.util.concurrent.locks.*;

class Message{
    private String msg;
    private ReentrantReadWriteLock rwlock;
    private Lock rdlock;
    private Lock wrlock;

    public Message(String s, ReentrantReadWriteLock rwlock)
    {
        this.msg = s;
        this.rwlock = rwlock;
        this.rdlock = rwlock.readLock();
        this.wrlock = rwlock.writeLock();
    }

    public String getMsg()
    {
        return this.msg;
    }

    public  void read()
    {
        rdlock.lock();
        System.out.println("inside read " + Thread.currentThread().getName() + " " + msg);
        rdlock.unlock();
    }

    public void write(String appnd)
    {
        wrlock.lock();
        this.msg = this.msg+appnd;
        System.out.println("inside write " + Thread.currentThread().getName() + " " + msg);
        wrlock.unlock(); 
    }
}

class Readers extends Thread{
    private Message msg;
    public Readers(Message m)
    {
        this.msg = m;
    }
    public void run(){
        System.out.println("Running " + this.getName());
        this.msg.read();
    }
}
class Writer extends Thread{
    private Message msg;
    public Writer(Message m)
    {
        this.msg = m;
    }
    public void run(){
        System.out.println("Running " + this.getName());
        this.msg.write(this.getName());
    }
}

public class ReadWriteLock1{
    public static void main(String[] args) {
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock(true);
        Message m = new Message("hello", rwlock);
        Readers r1 = new Readers(m);
        Readers r2 = new Readers(m);
        Readers r3 = new Readers(m);
        Writer w1 = new Writer(m);
        Writer w2 = new Writer(m);
        Writer w3 = new Writer(m);
        r1.start();r2.start();r3.start();w1.start();w2.start();w3.start();
    }
}