package common;

public class DeadLockTest {

    public static void main(String[] args) {

        Object locka = new Object();
        Object lockb = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (locka) {
                System.out.println("thread1获取到locka");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockb) {
                    System.out.println("thread1获取到lockb");
                }
            }

        });

        Thread thread2 = new Thread(() -> {
            synchronized (lockb) {
                System.out.println("thread2获取到lockb");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (locka) {
                    System.out.println("thread2获取到locka");
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}
