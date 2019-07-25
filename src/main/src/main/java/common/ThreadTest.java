package common;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest extends Thread {

    public static void main(String[] args) {
        Thread t1 = new Thread(new RunTt3());
        Thread t2 = new Thread(new RunTt3());

        t1.start();
        t2.start();
        t2.interrupt();
    }
}

class RunTt3 implements Runnable {

    private static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            lock.lock();
//            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " running");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + " finished");
        } catch(InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        } finally {
            lock.unlock();
        }
    }
}
