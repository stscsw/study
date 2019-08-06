package concurrent;

/**
 * 假设有一个生产者-消费者的场景：
 * 1、生产者有两个线程产生烤鸡；消费者有两个线程消费烤鸡
 * 2、四个线程一起执行，但同时只能有一个生产者线程生成烤鸡，一个消费者线程消费烤鸡。
 * 3、只有产生了烤鸡，才能通知消费线程去消费，否则只能等着；
 * 4、只有消费了烤鸡，才能通知生产者线程去生产，否则只能等着
 */
public class ChickenStoreSync {

    private int count = 0;

    private volatile boolean isHaveChicken = false;

    public synchronized void ProductChicken() {
        while (isHaveChicken) {
            try {
                System.out.println("有烤鸡了" + Thread.currentThread().getName() + "不生产了");
                this.wait();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "产生了第" + count + "个烤鸡，赶紧开始卖");
        isHaveChicken = true;
        notifyAll();//唤醒全部  三个 若使用notify唤醒的是生产者则四个线程都在等待唤醒了
    }

    public synchronized void SellChicken() {
        while (!isHaveChicken) {
            try {
                System.out.println("没有烤鸡了" + Thread.currentThread().getName() + "不卖了");
                this.wait();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        }
        count--;
        isHaveChicken = false;
        System.out.println(Thread.currentThread().getName() + "卖掉了第" + count + 1 + "个烤鸡，赶紧开始生产");
        notifyAll();//唤醒全部  三个 若使用notify唤醒的是消费者则四个线程都在等待唤醒了
    }

    public static void main(String[] args) {
        ChickenStoreSync chikenStore = new ChickenStoreSync();
        new Thread(() -> {
            Thread.currentThread().setName("生产者1号");
            while (true) {
                chikenStore.ProductChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("生产者2号");
            for (; ; ) {
                chikenStore.ProductChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("消费者1号");
            while (true) {
                chikenStore.SellChicken();
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("消费者2号");
            while (true) {
                chikenStore.SellChicken();
            }
        }).start();

    }
}
