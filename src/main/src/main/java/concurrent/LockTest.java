package concurrent;

import org.junit.Test;
import sun.misc.Unsafe;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void compareAndSwap() throws NoSuchFieldException {
        //直接使用会报错  java.lang.SecurityException: Unsafe
        AtomicInteger ai = new AtomicInteger(10);
        Unsafe unsafe = Unsafe.getUnsafe();
        long valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        int intVolatile = unsafe.getIntVolatile(ai, valueOffset);
        System.out.println(intVolatile);
    }

    /**
     * ReentrantLock 可重入自旋独占锁 （公平与否由构造器确定 默认非公平）
     * AbstractQueueSynchronized(AQS) 基础并发组件 负责构建同步队列 控制同步状态
     * Sync（tryRelease）  FairSync(获取锁之前先要判断队列是否有节点)  NonFairSync（直接CAS改state值）
     */
    @Test
    public void ReentrantLockTest() {
        ReentrantLock lock = new ReentrantLock(false);
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread() + "获取锁后执行共享数据");
            } finally {
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                lock.unlock();
            }
        });
        thread.start();
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + "获取锁后执行共享数据");
            System.out.println(lock.getHoldCount());//当前线程加了几次锁
            System.out.println(lock.getQueueLength());//当前AQS 双向队列有多少个待获取锁
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    /**
     * offer(v) 立即插入 成功true  失敗false
     * offer(v,Long,TimeUnit)  最多等待多长时间  true false 或者中断
     * put(v) 一直等  true  或者中断
     *
     * poll() 立即取出 成功true  失敗false
     * poll(Long,TimeUnit)  最多等待多长时间  true false 或者中断
     * take() 一直等  true  或者中断
     * @throws InterruptedException
     */
    @Test
    public void blockingQueue() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        System.out.println(queue.offer("1"));
        long time = System.currentTimeMillis();
        System.out.println(queue.offer("2", 1L, TimeUnit.SECONDS));
        System.out.println("offer等待时间"+(System.currentTimeMillis()-time));
        System.out.println(queue.poll());
        time = System.currentTimeMillis();
        System.out.println(queue.poll( 1L, TimeUnit.SECONDS));
        System.out.println("poll等待时间"+(System.currentTimeMillis()-time));
    }


}
