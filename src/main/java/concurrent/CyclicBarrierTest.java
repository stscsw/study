package concurrent;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        //parties 是参与线程的个数
        //第二个构造方法有一个 Runnable 参数，这个参数的意思是最后一个到达线程要做的任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,() -> {
            System.out.println("完成任务"+System.currentTimeMillis());
        });
        Run run  = new Run(cyclicBarrier);
        for (int i = 0; i < 5; i++) {
            new Thread(run).start();
        }
    }

}

class Run implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public Run(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            if ((i % 1000) == 0) {
                System.out.println(i);
            }
        }
        try {
            //线程调用 await() 表示自己已经到达栅栏
            System.out.println(Thread.currentThread().getName()+"完成,完成后有"+cyclicBarrier.await());
        }catch (Exception e){

        }

    }
}
