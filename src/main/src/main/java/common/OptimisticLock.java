package common;

import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticLock {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
    }
}
