class App{
    private static App app= new App();
    private App()
    {

    }
    public static App getInstance()
    {
        
            try{
                Thread.sleep(3000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        
        return app;
    }
}
class MyThread extends Thread{
    public void run()
    {
        App app = App.getInstance();
        System.out.println(app.hashCode());
    }
}
public class Locks8{
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        MyThread t4 = new MyThread();
        MyThread t5 = new MyThread();
        t1.start();t2.start();t3.start();t4.start();t5.start();
    }
}