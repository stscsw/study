package common;

public class DaemonTest {

    public static void main(String[] args) {
        Thread thread = new MyThread();
        thread.start();
    }


    static class MyThread extends Thread {

        @Override
        public void run() {

            try {
                System.out.println("try");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("catch");
            } finally {
                System.out.println("finally");
            }

        }
    }
}
