package concurrent;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class RecursiveTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Calc calc = new Calc(0L, 100000000L);
        ForkJoinPool pool = new ForkJoinPool();
        Instant start = Instant.now();
        pool.execute(calc);
        System.out.println(calc.get());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
        Thread.sleep(1000L);
        start = Instant.now();
        LongStream.rangeClosed(0L, 100000000L).parallel().reduce(0L, Long::sum);
        end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}

class Calc extends RecursiveTask<Long> {
    private long start;
    private long end;
    private long split = 10000L;

    public Calc(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= split) {
            return LongStream.rangeClosed(start, end).reduce(0L, Long::sum);
        } else {
            long middle = (start + end) / 2;
            Calc left = new Calc(start, middle);
            left.fork();
            Calc right = new Calc(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
