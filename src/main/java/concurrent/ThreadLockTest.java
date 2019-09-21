package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，
 * 每个线程将自己的 ID 在屏幕上打印 10 遍，要 求输出的结果必须按顺序显示。
 * 如：ABCABCABC…… 依次递归
 */
public class ThreadLockTest {

    public static void main(String[] args) {
        Sequ sequ = new Sequ();
        new Thread(() -> sequ.loopA(), "A").start();
        new Thread(() -> sequ.loopB(), "B").start();
        new Thread(() -> sequ.loopC(), "C").start();
    }
}


class Sequ {

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void loopA() {
        for (int i = 1; i <= 20; i++) {
            lock.lock();
            try {
                while (number != 1) {
                    conditionA.await();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + i);
                ++number;
                conditionB.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void loopB() {
        for (int i = 1; i <= 20; i++) {
            lock.lock();
            try {
                while (number != 2) {
                    conditionB.await();
                }
                System.out.println(Thread.currentThread().getName()+ "\t" + i);
                ++number;
                conditionC.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void loopC() {
        for (int i = 1; i <= 20; i++) {
            lock.lock();
            try {
                while (number != 3) {
                    conditionC.await();
                }
                System.out.println(Thread.currentThread().getName()+ "\t" + i);
                number = 1;
                conditionA.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


}
