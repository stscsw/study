package concurrent;

import org.junit.Test;
import sun.misc.Unsafe;


import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {


    /**
     * 其一，从getUnsafe方法的使用限制条件出发，通过Java命令行命令-Xbootclasspath/a
     * 把调用Unsafe相关方法的类A所在jar包路径追加到默认的bootstrap路径中，
     * 使得A被引导类加载器加载，从而通过Unsafe.getUnsafe方法安全的获取Unsafe实例。
     * java -Xbootclasspath/a: ${path}   // 其中path为调用Unsafe相关方法的类所在jar包路径
     * 其二，通过反射获取单例对象theUnsafe。
     */
    @Test
    public void testUnsafe() throws NoSuchFieldException, IllegalAccessException {
        //  Unsafe unsafe = Unsafe.getUnsafe(); //java.lang.SecurityException: Unsafe
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        Field value = AtomicInteger.class.getDeclaredField("value");
        long valueOffset = unsafe.objectFieldOffset(value);//获取AtomicInteger对象中value值的内存偏移量
        int intVolatile = unsafe.getIntVolatile(atomicInteger, valueOffset);//直接访问内存地址获取数据
        System.out.println(intVolatile);
        System.out.println(unsafe.compareAndSwapInt(atomicInteger, valueOffset, 1, 10));//CAS算法
        System.out.println(atomicInteger);
    }


    /**
     * https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void lock() throws NoSuchFieldException {
        //悲观锁
        synchronized (this) {

        }
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        //乐光锁 Unsafe 操作硬件级别的原子操作 compareAndSwap(CAS)
        //直接使用会报错  java.lang.SecurityException: Unsafe
        AtomicInteger ai = new AtomicInteger(10);
        Unsafe unsafe = Unsafe.getUnsafe();
        long valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        //volatile 内存可见性 屏蔽指令重排序  内存栅栏 弱内存模型 使用内存栅栏（cpu指令）完成寄存器数据一致
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
                    Thread.sleep(2000L);
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
     * add(v)  成功返回true 失败则抛异常
     * offer(v) 立即插入 成功true  失敗false
     * offer(v,Long,TimeUnit)  最多等待多长时间  true false 或者中断
     * put(v) 一直等  true  或者中断
     * <p>
     * remove(v) 删除指定元素 成功true 失败false
     * remove() 删除元素  成功true 失败抛出异常
     * poll() 立即取出 成功true  失敗false
     * poll(Long,TimeUnit)  最多等待多长时间  true false 或者中断
     * take() 一直等  true  或者中断
     * 继承自Queue接口的方法
     * 获取但不移除此队列的头元素,没有则跑异常NoSuchElementException
     * E element();
     * 获取但不移除此队列的头；如果此队列为空，则返回 null。
     * E peek();
     * 获取并移除此队列的头，如果此队列为空，则返回 null。
     * E poll();
     *
     * @throws InterruptedException
     */
    @Test
    public void blockingQueue() throws InterruptedException {
        LinkedBlockingQueue<String> add = new LinkedBlockingQueue<>(4);
        System.out.println(add.offer("2"));
        System.out.println(add.offer("3", 1000L, TimeUnit.NANOSECONDS));
        add.put("4");
        System.out.println("添加完4");
        System.out.println(add.add("1"));

        BlockingQueue<String> remove = new ArrayBlockingQueue<>(4);
        for (int i = 0; i < 4; i++) {
            remove.offer(i + "");
        }
        System.out.println(remove.poll());
        System.out.println(remove.poll(1000L, TimeUnit.NANOSECONDS));
        System.out.println(remove.take());
        System.out.println(remove.remove("1"));


        BlockingQueue<String> query = new LinkedBlockingQueue<>();
        query.add("a");
        System.out.println(query.element());
        System.out.println(query.peek());
        System.out.println(query.size());

    }

    /**
     * SynchronousQueue 是一个同步阻塞队列，它的每个插入操作都要等待其他线程相应的移除操作，
     * 反之亦然。SynchronousQueue 像是生产者和消费者的会合通道，它比较适合“切换”或“传递”这种场景：
     * 一个线程必须同步等待另外一个线程把相关信息/时间/任务传递给它。
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        System.out.println(queue.offer(1) + " ");
        System.out.println(queue.offer(2) + " ");
        System.out.println(queue.offer(3) + " ");
        System.out.println(queue.take() + " ");
        System.out.println(queue.size());
    }


}
