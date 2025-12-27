package cn.csw.projectreactor;

import com.google.common.base.Throwables;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Lier
 * @since 2025/5/14 11:03
 */
public class UnitTest {

    @Test
    public void test1() throws InterruptedException {

        Mono<Object> objectMono = Mono.just("null");

        Flux<String> flux = Flux.just("1", "2", "3");
        System.out.println("just:" + flux);//flux:FluxArray

        List<String> list = List.of("a", "b", "c");
        flux = Flux.fromIterable(list);
        System.out.println("fromIterable:" + flux);//fromIterable:FluxIterable

        String[] fruits = {"apple", "banana", "cherry"};
        flux = Flux.fromArray(fruits);
        System.out.println("fromArray:" + flux);//fromArray:FluxArray

        Stream<String> stream = Stream.of("1", "2", "3");
        flux = Flux.fromStream(stream);
        System.out.println("fromStream:" + flux);

        flux.subscribe((String s) -> {
            System.out.println(Thread.currentThread());
            System.out.println(s);
        });

        Flux<Integer> iFlux = Flux.range(1, 5);
        System.out.println("range:" + iFlux);

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1));
        System.out.println("interval:" + longFlux);

        longFlux.subscribe(System.out::println);


        Flux<Object> empty = Flux.empty();
        System.out.println(STR."empty:\{empty}");

        Flux<String> error = Flux.error(new RuntimeException("An error occurred"));
        System.out.println(STR."error:\{error}");


        Flux<Object> objectFlux = Flux.create(sink -> {
            sink.next("Apple");
            sink.next("Banana");
            sink.next("Cherry");
            sink.complete();
        });
        objectFlux.subscribe(System.out::println);


        Flux<Integer> generateFlux = Flux.generate(() -> 0, (state, sink) -> {
            if (state >= 5) {
                sink.complete();
            } else {
                sink.next(state);
            }
            return state + 1;
        });
        generateFlux.subscribe(System.out::println);

    }

    @Test
    public void test2() {
        Flux<String> flux = Flux.just("Apple", "Banana", "Cherry");
        flux.subscribe(
                item -> {
                    if (item.length() == 6) {
                        throw new RuntimeException(item + ":长度大于6");
                    }
                    System.out.println("Received: " + item);
                },
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Completed")
        );

    }

    @Test
    public void test3() {
        Flux<Integer> flux = Flux.just("Apple", "Banana", "Cherry")
                .map(String::length);
        flux.subscribe(System.out::println);

    }

    @Test
    public void test4() {
        Flux<String> flux = Flux.just("Apple", "Banana", "Cherry")
                .filter(fruit -> fruit.startsWith("A"));
        flux.subscribe(System.out::println);
    }

    @Test
    public void tes5() {
        Flux<String> flux1 = Flux.just("Apple", "Banana");
        Flux<String> flux2 = Flux.just("Cherry", "Date");

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);
        Flux<String> concatenatedFlux = Flux.concat(flux1, flux2);


        mergedFlux.subscribe(System.out::println);
        concatenatedFlux.subscribe(System.out::println);
    }

    @Test
    public void tes6() {
        Flux<List<String>> bufferedFlux = Flux.just("Apple", "Banana", "Cherry", "Date")
                .buffer(2);

        Flux<Flux<String>> windowedFlux = Flux.just("Apple", "Banana", "Cherry", "Date")
                .window(2);


        Flux<String> flatMapFlux = Flux.just("Apple$1", "Banana$2", "Cherry$3", "Date$4")
                .flatMap(item -> Flux.fromArray(item.split("\\$")));

        bufferedFlux.subscribe(System.out::println);
        windowedFlux.subscribe(item -> item.subscribe(System.out::println));
        flatMapFlux.subscribe(System.out::println);
    }

    @Test
    public void tes7() {
        Flux<String> flux = Flux.just("Apple", "Banana", "Cherry")
                .map(fruit -> {
                    if ("Banana".equals(fruit)) {
                        throw new RuntimeException("Banana error");
                    }
                    return fruit.toUpperCase();
                })
                .onErrorResume(e -> Flux.just("Recovered"));

        flux.subscribe(System.out::println);


        flux = Flux.just(1, 2).map(item -> {
                    System.out.printf("map1:%s%n", Thread.currentThread());
                    return item + 10;
                })
                .map(item -> {
                    System.out.printf("map2:%s%n", Thread.currentThread());
                    return "value:" + item;
                });
        System.out.println("--------------");
        flux.subscribe(item -> System.out.println(Thread.currentThread()+item));
    }


    @Test
    public void test8() throws InterruptedException {
        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
        Flux<String> flux = Flux.just(1, 2).map(item -> {
                    System.out.printf("map1:%s%n", Thread.currentThread());
                    return item + 10;
                }).publishOn(scheduler)
                .map(item -> {
                    System.out.printf("map2:%s%n", Thread.currentThread());
                    return "value:" + item;
                });
        flux.subscribe(item -> System.out.println(Thread.currentThread()+item));
    }

    @Test
    public void test9(){
        System.out.println(Thread.currentThread());
        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
        Flux<String> flux  = Flux.just(1, 2).map(item -> {
                    System.out.printf("map1:%s%n", Thread.currentThread());
                    return item + 10;
                }).subscribeOn(scheduler)
                .map(item -> {
                    System.out.printf("map2:%s%n", Thread.currentThread());
                    return "value:" + item;
                });
        System.out.println("----------------------------");
        flux.subscribe(item -> System.out.println(Thread.currentThread()+item));
    }


    @Test
    public void test100() throws InterruptedException {
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("virtualThread:"+Thread.currentThread());

        });
        virtualThread.join();
    }

    @Test
    public void test112(){
        Map<String, Integer> map1 = new HashMap<>(Map.of("key1", 1, "key2", 2));
        Map<String, Integer> map2 = new HashMap<>(Map.of("key1", 3, "key3", 4));
        map1.forEach((key, value) -> map2.merge(key, value, Integer::sum));
        System.out.println(map2);
    }

    /**
     * 正数的反码是本身  补码是本身
     * 负数的补码每一位取反  补码是反码+1
     * 然后负数在计算机中已反码的形式存在
     */
    @Test
    public void test1312(){
        System.out.println(-1L << 5);
        /*
           原码  0000 0001
           反码  1111 1110
           补码  1111 1111
           向左位移五位
                1110 0000
           补码-1
                1101 1111
           取反
                0010 0000
           即 -32
         */
        System.out.println(~(-1L << 5));
        /**
         * 原码  0000 0001
         * 反码  1111 1110
         * 补码  1111 1111
         * 向左位移五位
         *      1110 0000
         * ~每一位取反
         *      0001 1111
         * 即 31
         */
    }

    @Test
    public void test12312(){
        System.out.println(Long.toBinaryString(System.currentTimeMillis()).length());

        System.out.println(Long.toBinaryString(-1));

    }


    @Test
    public void test12312131231(){

        try{
            int i = 1/0;
        }catch (Exception e){
            System.out.println(Throwables.getStackTraceAsString(e));
            System.out.println(e.getCause());
        }

    }


}
