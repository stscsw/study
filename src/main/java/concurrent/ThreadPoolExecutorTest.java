package concurrent;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class ThreadPoolExecutorTest {


    /**
     * 优先级队列
     * 什么是堆
     * 它是一个完全二叉树，即除了最后一层节点不是满的，其他层节点都是满的，即左右节点都有。
     * 它不是二叉搜索树，即左节点的值都比父节点值小，右节点的值都不比父节点值小，这样查找的时候，就可以通过二分的方式，效率是(log N)。
     * 它是特殊的二叉树，它要求父节点的值不能小于子节点的值。这样保证大的值在上面，小的值在下面。所以堆遍历和查找都是低效的，因为我们只知道
     * 从根节点到子叶节点的每条路径都是降序的，但是各个路径之间都是没有联系的，查找一个值时，你不知道应该从左节点查找还是从右节点开始查找。
     * 它可以实现快速的插入和删除，效率都在(log N)左右。所以它可以实现优先级队列。
     * <p>
     * int left = 2 * n + 1; // 左子节点
     * int right = 2 * n + 2; // 右子节点
     * int parent = (n - 1) / 2; // 父节点，当然n要大于0，根节点是没有父节点的
     */
    @Test
    public void test() {

        //Executors
        //ThreadPoolExecutor
        //ScheduledThreadPoolExecutor

        //核心线程1  最大线程1  最大空闲时间0  无限大的阻塞队列
        //单线程的线程池
        ExecutorService single = Executors.newSingleThreadExecutor();

        //核心线程0 最大线程Integer.max 同步阻塞队列(放进去拿出来同步) 最大空闲时间1min
        //缓存线程池 允许无限创建线程
        ExecutorService cache = Executors.newCachedThreadPool();

        //指定核心线程和最大线程一样  最大空闲时间0  无限大的阻塞队列
        //固定大小线程池
        ExecutorService fixed = Executors.newFixedThreadPool(10);

        //指定核心线程  最大线程Integer.max  最大空闲时间0
        //固定大小线程池 优先级队列DelayedWorkQueue PriorityBlockingQueue
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(20);
    }

    /**
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);
        scheduled.scheduleAtFixedRate(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "进入等待" + System.currentTimeMillis());
                Thread.sleep(500L);
            } catch (Exception e) {
            }
            System.out.println(Thread.currentThread().getName() + "结束等待" + System.currentTimeMillis());
        }, 0, 1000L, TimeUnit.MILLISECONDS);
        Thread.sleep(10000L);
        scheduled.shutdown();
        System.out.println("-----------------------------------");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "进入等待" + System.currentTimeMillis());
                    Thread.sleep(500L);
                } catch (Exception e) {
                }
                System.out.println(Thread.currentThread().getName() + "结束等待" + System.currentTimeMillis());
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000L);
        Thread.sleep(10000L);
    }

    @Test
    public void testThrowException() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                throw new RuntimeException("我抛出了异常");
            });
            //如果不理会任务结果（Feture.get()），那么此异常将被线程池吃掉。
            //f.get();   throws ExecutionException, InterruptedException
        }
    }

    /**
     * 线程异常抓取
     */
    @Test
    public void test11() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("线程" + t.getName());
            System.out.println(e.getMessage());
        });
        new Thread(() -> {
            throw new RuntimeException("我的异常");
        }).start();
    }

    /**
     * 线程池异常抓取
     */
    @Test
    public void test111() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(t.getClass());
                System.out.println(t.getMessage());
//                super.afterExecute(r, t);
            }
        };
        threadPoolExecutor.execute(() -> {
            throw new IllegalArgumentException("我的异常");
        });


        Thread.sleep(1000L);
    }

}
