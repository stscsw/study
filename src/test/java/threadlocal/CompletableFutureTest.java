package threadlocal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author Lier
 * @since 2024/9/23 17:38
 */
public class CompletableFutureTest {


    @Test
    public void test() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> System.out.println("runAsync"));
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future1 = future.thenApply(i -> i * 10);

    }


    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync1");
            return "supplyAsync1";
        });
        CompletableFuture<String> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync2");
            return "supplyAsync2";
        });

        CompletableFuture<String> supplyAsync3 = supplyAsync1.thenCombine(supplyAsync2, (s1, s2) -> {
            System.out.println(String.join("------", Thread.currentThread().getName(), "thenCombine", s1, s2));
            return s1 + s2;
        });


        System.out.println(Thread.currentThread().getName() + "___" + supplyAsync3.get());
    }

    @Test
    public void test2() {
        CompletableFuture.runAsync(this::method1).whenComplete((s, throwable) -> {
            System.out.println(Thread.currentThread().getName() + "____" + "s" + s);
            System.out.println(Thread.currentThread().getName() + "____" + "throwable" + throwable);
        });
        System.out.println(Thread.currentThread().getName() + "____" + "main1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "____" + "main2");
    }


    public void method1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "____" + "method1");
    }


    @Test
    public void test1321() throws ExecutionException, InterruptedException {

        Random random = new Random();
        //异步执行  有返回值和无返回值 supplyAsync和runAsync
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> random.nextInt(100));
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> random.nextInt(100));

        //一个任务环节处理完成后，下一个任务环节接着上一环节处理结果继续处理
        // thenApply thenCompose thenAccept thenRun
        CompletableFuture<String> thenApply = CompletableFuture.supplyAsync(System::currentTimeMillis).thenApply(String::valueOf);
        CompletableFuture<String> thenCompose = CompletableFuture.supplyAsync(System::currentTimeMillis).thenCompose(item -> CompletableFuture.supplyAsync(item::toString));
        CompletableFuture<Void> thenAccept = CompletableFuture.supplyAsync(System::currentTimeMillis).thenAccept(System.out::println);
        CompletableFuture<Void> thenRun = CompletableFuture.supplyAsync(System::currentTimeMillis).thenRun(() -> System.out.println("thenRun"));


        //异常处理 handle  whenComplete
        CompletableFuture<Integer> handle = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException(" handle exception");
            }
            return System.currentTimeMillis();
        }).handle((timeMillis, ex) -> {
            if (Objects.isNull(ex)) {
                System.out.println(timeMillis);
            } else {
                System.out.println(ex.getMessage());
            }
            return random.nextInt(100);
        });

        CompletableFuture<Long> whenComplete = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException(" whenComplete exception");
            }
            return System.currentTimeMillis();
        }).whenComplete((timeMillis, ex) -> {
            if (Objects.isNull(ex)) {
                System.out.println(timeMillis);
            } else {
                System.out.println(ex.getMessage());
            }
        });

        //多个并行环节的合并处理
        CompletableFuture<String> thenCombine = CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."第一次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."第一次返回:\{System.currentTimeMillis()}");
            return "2024-11-08";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."第二次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."第二次返回:\{System.currentTimeMillis()}");
            return "15:16:57";
        }), (s1, s2) -> STR."\{s1} \{s2}");

        System.out.println(STR."\{thenCombine.get()} \{System.currentTimeMillis()}");


        CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."第一次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."第一次返回:\{System.currentTimeMillis()}");
            return "2024-11-08";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."第二次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."第二次返回:\{System.currentTimeMillis()}");
            return "15:16:57";
        }), (s1, s2) -> System.out.println(STR."thenAcceptBoth \{s1} \{s2}")).join();


        CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."runAfterBoth第一次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."runAfterBoth第一次返回:\{System.currentTimeMillis()}");
            return "2024-11-08";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(STR."runAfterBoth第二次进入:\{System.currentTimeMillis()}");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."runAfterBoth第二次返回:\{System.currentTimeMillis()}");
            return "15:16:57";
        }), () -> System.out.println(STR."runAfterBoth:\{System.currentTimeMillis()}")).join();


        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."applyToEither  supplyAsync :\{System.currentTimeMillis()}");
            return "supplyAsync";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."applyToEither:\{System.currentTimeMillis()}");
            return "applyToEither";
        }), str -> {
            System.out.println(str);
            return System.currentTimeMillis();
        }).join();


        Thread.sleep(2000L);


        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."acceptEither  supplyAsync :\{System.currentTimeMillis()}");
            return "acceptEither1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."acceptEither:\{System.currentTimeMillis()}");
            return "acceptEither2";
        }), System.out::println).join();


        Thread.sleep(2000L);

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."runAfterEither  supplyAsync :\{System.currentTimeMillis()}");
            return "runAfterEither1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(STR."runAfterEither  runAfterEither:\{System.currentTimeMillis()}");
            return "runAfterEither2";
        }), () -> System.out.println(STR."runAfterEither:\{System.currentTimeMillis()}")).join();

    }


    @Test
    public void testAsync() {
        // 构造自定义线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(STR."supplyAsync \{Thread.currentThread().getName()}");
                    return System.currentTimeMillis();
                },
                executor
        ).thenCombineAsync(
                CompletableFuture.supplyAsync(() -> {
                    System.out.println(STR."thenCombineAsync supplyAsync  \{Thread.currentThread().getName()}");
                    return System.currentTimeMillis();
                }),
                (s1, s2) -> {
                    System.out.println(STR."thenCombineAsync BiFunction \{Thread.currentThread().getName()}");
                    return Long.sum(s1, s2);
                },
                executor
        ).join();

    }


    @Test
    public void test132123() {
        Date date = new Date();
        System.out.println(date.compareTo(date) <= 0);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println(set.contains(null));

        String string = "会议指出，明年要坚持稳中求进、以进促稳，守正创新、先立后破，系统集成、协同配合，实施更加积极的财政政策和适度宽松的货币政策，充实完善政策工具箱，加强超常规逆周期调节，打好政策“组合拳”，提高宏观调控的前瞻性、针对性、有效性。要大力提振消费、提高投资效益，全方位扩大国内需求。要以科技创新引领新质生产力发展，建设现代化产业体系。要发挥经济体制改革牵引作用，推动标志性改革举措落地见效。要扩大高水平对外开放，稳外贸、稳外资。要有效防范化解重点领域风险，牢牢守住不发生系统性风险底线。要持续巩固拓展脱贫攻坚成果，统筹推进新型城镇化和乡村全面振兴，促进城乡融合发展。要加大区域战略实施力度，增强区域发展活力。要协同推进降碳减污扩绿增长，加快经济社会发展全面绿色转型。要加大保障和改善民生力度，增强人民群众获得感幸福感安全感。";
        System.out.println(string.length());
        System.out.println(string.substring(0, 255));
    }


    /**
     * pmt  partial match table 部分匹配表
     */
    @Test
    public void test132132() {
        // 双指针计算pmt数组
        char[] p = "abcabxabcab".toCharArray();
        int[] pmt = new int[p.length];
        pmt[0] = 0;
        // 双指针，i在j右边
        for (int i = 1, j = 0; i < p.length; ) {
            if (j == 0 && p[i] != p[j]) {
                pmt[i] = 0;
                i++;
            } else if (p[i] == p[j]) {
                pmt[i] = j + 1;
                i++;
                j++;
            } else { // p[i]!= p[j] && j!= 0
                // 在i、j处失配，但是之前的都是匹配的
                j = pmt[j - 1];
            }
        }
        for (int i : pmt) {
            System.out.print(i);
        }
        System.out.println();
    }

    @Test
    public void test12132(){
        System.out.println(kmp("abcaba", "aba"));
    }


    int kmp(String s, String p) {
        int[] next = next(p.toCharArray());

        // s指针
        int i = 0 /* s指针 */, j = 0 /* p指针 */; // 双指针
        while (i < s.length() && j < p.length()) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else if (j == 0) { // p[0] 匹配不上，那么s指针右移一位
                i++;
            } else { // p 右移
                j = next[j-1];
            }
        }
        return j == p.length() ? i - j : -1;
    }

    int[] next(char[] p) {
        int[] next = pmt(p);
        System.out.println("p: " + Arrays.toString(p));
        System.out.println("pmt: " + Arrays.toString(next));
        if (next.length > 0) System.arraycopy(next, 0, next, 1, next.length - 1);
        next[0] = 0;
        return next;
    }

    // 双指针计算pmt数组
    int[] pmt(char[] p) {
        int[] pmt = new int[p.length];
        pmt[0] = 0;
        // 双指针，i在j右边
        for (int i = 1, j = 0; i < p.length; ) {
            if (j == 0 && p[i] != p[j]) {
                pmt[i] = 0;
                i++;
            } else if (p[i] == p[j]) {
                pmt[i] = j + 1;
                i++;
                j++;
            } else { // p[i]!= p[j] && j!= 0
                // 在i、j处失配，但是之前的都是匹配的
                j = pmt[j - 1];
            }
        }
        return pmt;
    }


    @Test
    public void test1213123(){
        Stream<Integer> empty = Stream.empty();
//        System.out.println(empty.anyMatch(item -> item > 1));
        List<Integer> integers = List.of(1, 2, 3, 4);
        System.out.println(empty.anyMatch(item -> integers.stream().noneMatch(item1 -> item1 > item)));

        System.out.println(BigDecimal.ONE.add(BigDecimal.ONE).divide(BigDecimal.valueOf(100l), 4, RoundingMode.HALF_UP));
        try {
            testException();
        }catch (Exception e){
            throw new RuntimeException("故意的异常",e);
            /**
             * java.lang.RuntimeException: 故意的异常
             *
             * 	at threadlocal.CompletableFutureTest.test1213123(CompletableFutureTest.java:391)
             * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
             * 	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
             * 	... 68 more
             * Caused by: java.lang.ArithmeticException: / by zero
             * 	at threadlocal.CompletableFutureTest.testException(CompletableFutureTest.java:397)
             * 	at threadlocal.CompletableFutureTest.test1213123(CompletableFutureTest.java:389)
             * 	... 68 more
             */
        }
    }


    public void testException(){
        int i = 10 / 0;
    }


    @Test
    public void test2131(){
        Map<String,Object> param = new HashMap<>();
        param.put("a",null);

        System.out.println(param.get("a"));
        System.out.println(param.getOrDefault("a","未知"));

        System.out.println("adas".substring(3,3));
    }
}
