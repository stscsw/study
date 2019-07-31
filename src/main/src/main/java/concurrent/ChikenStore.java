package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 假设有一个生产者-消费者的场景：
 * 1、生产者有两个线程产生烤鸡；消费者有两个线程消费烤鸡
 * 2、四个线程一起执行，但同时只能有一个生产者线程生成烤鸡，一个消费者线程消费烤鸡。
 * 3、只有产生了烤鸡，才能通知消费线程去消费，否则只能等着；
 * 4、只有消费了烤鸡，才能通知生产者线程去生产，否则只能等着
 */
public class ChikenStore {
    ReentrantLock reentrantLock = new ReentrantLock();

    Condition productCondition = reentrantLock.newCondition();

    Condition consumeCondition = reentrantLock.newCondition();

    private int count = 0;

    private volatile boolean isHaveChicken = false;

    //生产
    public void ProductChicken() {
        reentrantLock.lock();
        while (isHaveChicken) {
            try {
                System.out.println("有烤鸡了" + Thread.currentThread().getName() + "不生产了");
                productCondition.await();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "产生了第" + count + "个烤鸡，赶紧开始卖");
        isHaveChicken = true;
        consumeCondition.signal();
        reentrantLock.unlock();
    }

    public void SellChicken() {
        reentrantLock.lock();
        while (!isHaveChicken) {
            try {
                System.out.println("没有烤鸡了" + Thread.currentThread().getName() + "不卖了");
                consumeCondition.await();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        }
        count--;
        isHaveChicken = false;
        System.out.println(Thread.currentThread().getName() + "卖掉了第" + count + 1 + "个烤鸡，赶紧开始生产");
        productCondition.signal();
        reentrantLock.unlock();
    }

    public static void main(String[] args) {
        ChikenStore chikenStore = new ChikenStore();
        new Thread(() -> {
            Thread.currentThread().setName("生产者1号");
            while (true) {
                chikenStore.ProductChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("生产者2号");
            for (; ; ) {
                chikenStore.ProductChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("消费者1号");
            while (true) {
                chikenStore.SellChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("消费者2号");
            while (true) {
                chikenStore.SellChicken();
            }
        }).start();

    }
}
