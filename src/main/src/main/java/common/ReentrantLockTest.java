package common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    static ReentrantLock lock = new ReentrantLock(false);//可重入排它锁  公平与否可以设置  默认不公平（添加吞吐量）
    static Semaphore share = new Semaphore(10, false);//可重入共享锁  公平与否可以设置 默认不公平（添加吞吐量）
    private static int count = 0;

    public static void doAdd() {
        lock.lock();
        System.out.println("可重入锁");
        System.out.println(lock.getHoldCount());
        System.out.println(lock.hasQueuedThread(Thread.currentThread()));
        lock.unlock();
    }

    //可重入锁测试
    public static void test1() {
        lock.lock();
        doAdd();
        lock.unlock();
    }

    //锁不同的线程  Java中，synchronized关键字和Lock的实现类都是悲观锁。
    public static void test2() {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Runnable runnable = () -> {
                lock.lock();
                System.out.println(Thread.currentThread());
                System.out.println(count++);
                lock.unlock();
            };
            Thread thread = new Thread(runnable, "myThread-csw-" + i);
            list.add(thread);
        }
        list.forEach(Thread::start);
    }

    public static void test3() {

        Thread thread1 = new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread() + "线程休眠开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "线程休眠结束");
            lock.unlock();
        });


        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println(Thread.currentThread() + "拿到锁");
            System.out.println(count);
            lock.unlock();
        });

        thread1.start();
        thread2.start();

    }


    public static void main(String[] args) {

//        test1();
//        test2();
        test3();

    }
}