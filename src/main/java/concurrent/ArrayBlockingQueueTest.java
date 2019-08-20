package concurrent;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
    //定义吃鸡队列，队列大小是1
    private ArrayBlockingQueue<Chicken> blockingQueue = new ArrayBlockingQueue<>(1);

    private void product() {
        Chicken chicken = new Chicken();
        try {
            blockingQueue.put(chicken);
            System.out.println(Thread.currentThread().getName()+" has produced a Chicken");
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    private void consume(){
        try {
            //每次消费前先睡一秒钟
            Thread.sleep(1000L);
            blockingQueue.take();
            System.out.println(Thread.currentThread().getName()+" has eaten a Chicken");
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]){
        ArrayBlockingQueueTest arrayBlockingQueueTest = new ArrayBlockingQueueTest();
        new Thread( ()->{
            while (true){
                Thread.currentThread().setName("生产者一号");
                arrayBlockingQueueTest.product();
            }
        }
        ).start();
        new Thread( ()->{
            while (true){
                Thread.currentThread().setName("生产者二号");
                arrayBlockingQueueTest.product();
            }
        }
        ).start();
        new Thread( ()->{
            while (true){
                Thread.currentThread().setName("吃鸡者一号");
                arrayBlockingQueueTest.consume();
            }
        }
        ).start();
        new Thread( ()->{
            while (true){
                Thread.currentThread().setName("吃鸡者二号");
                arrayBlockingQueueTest.consume();
            }
        }
        ).start();
    }
}
