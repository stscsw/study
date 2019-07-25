package common;

/**
 * 线程中断测试
 */
public class InterruptionTest implements Runnable {

    private volatile static boolean on = false;

    @Override
    public void run() {
        while (!on) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Yes,I am interruted,but I am still running");
                return;
            } else {
                System.out.println("not yet interrupted");
            }
//            try {
//                Thread.sleep(10000000);
//            } catch (InterruptedException e) {
//                System.out.println("caught exception: "+e);
//            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread(new InterruptionTest(),"InterruptionInJava");
        //start thread
        testThread.start();
//        Thread.sleep(1000);
        testThread.interrupt();
//        InterruptionTest.on = true;//这种开关的方式看起来包治百病，但是当遇到线程阻塞时需使用interrupt
        System.out.println("main end");
    }
}
