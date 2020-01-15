package timer;


import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class MainTest {

    public static void main(String[] args) {
//        testTimer();
//        testScheduledExecutorService();
        testHashedWheelTimer();
    }

    /**
     * 后台调度任务的线程只有一个，所以导致任务是阻塞运行的，一旦其中一个任务执行周期过长将会影响到其他任务。
     * Timer 本身没有捕获其他异常（只捕获了 InterruptedException），一旦任务出现异常（比如空指针）将导致后续任务不会被执行。
     */
    public static void testTimer() {
        Timer timer = new Timer();
        System.out.println("main" + System.currentTimeMillis());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("run1" + System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + "run1");
            }
        }, 5000L);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("run2" + System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + "run2");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                }
            }
        }, 3000L);
    }


    /**
     * 基于数组来实现的一个最小堆，它可以让每次写入的定时任务都按照执行时间进行排序，保证在堆顶的任务执行时间是最小的
     */
    public static void testScheduledExecutorService() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        System.out.println("main" + System.currentTimeMillis());
        scheduledExecutorService.schedule(() -> {
            System.out.println("run1" + System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + "run1");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> {
            System.out.println("run2" + System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + "run2");
        }, 5, TimeUnit.SECONDS);
    }


    /**
     * 基于时间轮实现的定时器 
     */
    public static  void testHashedWheelTimer(){
        HashedWheelTimer timer = new HashedWheelTimer();
        System.out.println("main" + System.currentTimeMillis());
        timer.newTimeout((Timeout timeout) -> {
            System.out.println("run1" + System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + "run1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);

        timer.newTimeout((Timeout timeout) -> {
            System.out.println("run2" + System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + "run2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);
    }
}
