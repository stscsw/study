package common;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueTest {


    public static void main(String[] args) throws InterruptedException {
        Collection<String> collection = Arrays.asList("a", "b", "c");
        BlockingQueue<String> bq = new ArrayBlockingQueue(3, true, collection);
//        testAddMethod(bq);
//        testOfferMethod(bq);
//        testPutMethod(bq);
//        testPollMethod(bq);
//        testRemoveMethod(bq);
        testTakeMethod(bq);
        System.out.println("结束");
    }



    public static void testAddMethod(BlockingQueue<String> bq) {
        bq.add("d");//添加元素  如果队列已满报错IllegalStateException
    }

    public static void testOfferMethod(BlockingQueue<String> bq) {
        System.out.println(bq.offer("d"));//添加元素  如果队列已满返回false
    }


    public static void testPutMethod(BlockingQueue<String> bq) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(bq.poll());
        };
        new Thread(runnable).start();
        bq.put("d");//添加元素  如果队列已满阻塞
    }


    public static void testPollMethod(BlockingQueue<String> bq){
        System.out.println(bq.poll());
        System.out.println(bq.poll());
        System.out.println(bq.poll());
        System.out.println(bq.poll());
    }


    public static void testRemoveMethod(BlockingQueue<String> bq){
        System.out.println(bq.remove("a"));
        System.out.println(bq.remove("d"));
    }


    public static void testTakeMethod(BlockingQueue<String> bq) throws InterruptedException {
        System.out.println(bq.take());
        System.out.println(bq.take());
        System.out.println(bq.take());
        System.out.println(bq.take());
    }

}
