package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockSyncTest {

    public static void main(String[] args) {

        Person person = new Person();
        Product product = new Product(person);
        Consume consume = new Consume(person);

        new Thread(product, "生产1").start();
        new Thread(product, "生产2").start();
        new Thread(consume, "消费1").start();
        new Thread(consume, "消费2").start();

    }

    static class Person {

        private int product = 0;
        private ReentrantLock reentrantLock;
        Condition condition;

        public Person(){
            this.reentrantLock = new ReentrantLock();
            this.condition = reentrantLock.newCondition();
        }

        public  void add() throws InterruptedException {
            reentrantLock.lock();
            try {
                while (product >= 10) {//避免虚假唤醒
                    System.out.println(Thread.currentThread().getName()+"产品已满");
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + "-----------" + ++product);
                condition.signalAll();
            }finally {
                reentrantLock.unlock();
            }

        }

        public  void sale() throws InterruptedException {
            reentrantLock.lock();
            try {
                while (product <= 0) {
                    System.out.println(Thread.currentThread().getName()+"产品不存在");
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + "-----------" + --product);
                condition.signalAll();
            }finally {
                reentrantLock.unlock();
            }
        }

    }

    static class Product implements Runnable {

        private Person person;

        public Product(Person person) {
            this.person = person;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    person.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consume implements Runnable {

        private Person person;

        public Consume(Person person) {
            this.person = person;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    person.sale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

