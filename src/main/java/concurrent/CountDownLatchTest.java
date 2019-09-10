package concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        //是通过一个计数器来实现的，计数器的初始值是线程的数量。
        // 每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CountDown countDown = new CountDown(countDownLatch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(countDown).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start));

    }

}

class CountDown implements Runnable {

    private CountDownLatch cdl;

    public CountDown(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 50000; i++) {
                if ((i & 1) == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            cdl.countDown();
        }

    }
}
