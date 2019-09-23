package concurrent;
import java.util.concurrent.*;

/**
 * CallAble Runnable 有返回值和可以抛异常
 * RunnableFuture  extends  Runnable,Future
 * FutureTask  implements  RunnableFuture
 */
public class CallAbleTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask<>(new Call());
        new Thread(futureTask).start();
        System.out.println(System.currentTimeMillis()+"开启线程完毕");
        Thread.sleep(5000L);
        String s = futureTask.get();//等待call方法执行完返回数据后在继续执行
        System.out.println(System.currentTimeMillis()+"执行结果"+s);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> submit = executorService.submit(new Call());
        System.out.println(submit.get());
        executorService.shutdown();
    }


    static class Call implements Callable<String> {

        @Override
        public String call() throws Exception {
            for (int i = 0;i< 10;i++){
                System.out.println("开始第"+i+"次休息");
                Thread.sleep(1000L);
            }
            return "10000ms";
        }
    }
}
