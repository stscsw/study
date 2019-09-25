package concurrent;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock:读写锁
 * 写写/写读  需要排斥
 * 读读      可以一起执行
 * Synchronized 作用在方法级别的时候根据方法签名锁对象不同
 * 静态方法锁对象为类  实例方法锁对象为实例
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        ReadWriteLockResource resource = new ReadWriteLockResource();
        Thread write = new Thread(() -> {
            resource.setNumber(new Random().nextInt());
        }, "write");

        for (int i = 0; i < 100; i++) {
            if (i==20){
                write.start();
            }
            new Thread(() -> {
                resource.getNumber();
            }, "read:" + i).start();
        }
    }

}


class ReadWriteLockResource {

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void getNumber() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setNumber(int number) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "設置共享值number为" + number);
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}