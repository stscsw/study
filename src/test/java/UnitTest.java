import cn.csw.model.Strawberry;
import cn.csw.util.CommonUtils;
import cn.csw.util.ULID;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import dto.OrderDto;
import dto.OrderItemDto;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Lier
 * @since 2022/9/5 16:58
 */
public class UnitTest {

    @Test
    public void test1() {
        System.out.println(String.join("_", null, null, null));
    }

    @Test
    public void test2() {
        BigDecimal bigDecimal = new BigDecimal("2.324324234");
        System.out.println(bigDecimal);
        BigDecimal bigDecimal1 = bigDecimal.setScale(2, RoundingMode.DOWN);
        System.out.println(bigDecimal1);
        BigDecimal bigDecimal2 = bigDecimal.setScale(2, RoundingMode.UP);
        System.out.println(bigDecimal2);
        BigDecimal bigDecimal3 = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        System.out.println(bigDecimal3);
    }

    @Test
    public void test3() {
        List<Integer> collect = Stream.of(1, 2, 1, 1, 3, 1).collect(Collectors.toList());
        collect.remove(Integer.valueOf(1));//删除一个元素
        System.out.println(collect);
        collect.removeAll(Collections.singletonList(1));//删除集合中所有出现的元素
        System.out.println(collect);
    }

    /**
     * interrupt 成员方法 设置中断标识为true
     * isInterrupted 成员方法 返回中断标识
     * interrupted 类方法 返回中断标识并且设置为false
     * Thread.sheelp() object.wait() 都会进行中断标识判断若是true 抛异常且重新设置false
     */
    @Test
    public void ThreadInterrupted() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.isInterrupted());//false
        thread.interrupt();
        System.out.println(thread.isInterrupted());//true
        Thread.interrupted();
        System.out.println(thread.isInterrupted());//false
        thread.interrupt();
        System.out.println(thread.isInterrupted());//true
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            System.out.println("异常了");
        }
        System.out.println(thread.isInterrupted());//false
    }

    @Test
    public void testStringJoin() {
        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add("csw").add("hhy");
        System.out.println(sj);//[csw:hhy]
    }


    @Test
    public void testTimeUnit() {
        System.out.println(TimeUnit.SECONDS.toMillis(2));
        System.out.println(TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS));
    }

    /**
     * PriorityQueue 有扩容方法 底层用数组实现
     */
    @Test
    public void test4() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(10);
        priorityQueue.add(5);
        priorityQueue.add(20);
        System.out.println(priorityQueue.remove());
        priorityQueue.add(1);
        System.out.println(priorityQueue.remove());
        IntStream.rangeClosed(1, 10).forEach(priorityQueue::add);
    }


    /**
     * 线程不安全
     *
     * @throws InterruptedException
     * @see java.util.LinkedList#add
     * {@link java.util.LinkedList#add}
     */
    @Test
    public void test5() throws InterruptedException {
        LinkedList<Integer> linkedList = new LinkedList<>();
        new Thread(() -> IntStream.rangeClosed(1, 1000).forEach(linkedList::add)).start();
        new Thread(() -> IntStream.rangeClosed(1, 1000).forEach(linkedList::add)).start();
        new Thread(() -> IntStream.rangeClosed(1, 1000).forEach(linkedList::add)).start();
        new Thread(() -> IntStream.rangeClosed(1, 1000).forEach(linkedList::add)).start();
        new Thread(() -> IntStream.rangeClosed(1, 1000).forEach(linkedList::add)).start();
        Thread.sleep(1000L);
        System.out.println(linkedList.size());
    }


    @Test
    public void test6() {
        String regex = "^[+]?(\\d+)$";
        System.out.println("0".matches(regex));

    }


    @Test
    public void testPriorityQueue() {
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.reverseOrder());
        queue.add("c");
        queue.add("z");
        queue.add("a");
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }

    /**
     * 需指定大小  是否公平（默认不公平）
     *
     * @throws InterruptedException
     */
    @Test
    public void testArrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        new Thread(() -> {
            IntStream.rangeClosed(1, 10).forEach(item -> {
                try {
                    System.out.println("放置" + item);
                    queue.put(item);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).start();
        new Thread(() -> {
            IntStream.rangeClosed(1, 10).forEach(item -> {
                try {
                    System.out.println("拿出" + queue.take());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).start();

        TimeUnit.SECONDS.sleep(10);
    }


    /**
     * 默认 Integer.MAX_VALUE 大小  两把锁 入队锁  出队锁
     *
     * @throws InterruptedException
     */
    @Test
    public void testLinkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(queue.remove());
        }).start();
        queue.put(4);
    }

    /**
     * 默认大小11 一把锁 自动扩容
     */
    @Test
    public void testPriorityBlockQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(2);
        queue.put(100);
        queue.put(50);
        queue.put(200);
        System.out.println(queue.peek());
    }

    /**
     * 放入的对象都是实现Delayed接口数据
     * 自动扩容 一把锁
     * 底层用PriorityQueue
     */
    @Test
    public void testDelayQueue() throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();
        queue.put(new MyDelayed("A", 1000L));
        queue.put(new MyDelayed("V", 200L));
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    @Test
    public void testArrayList() {
        MyDelayed a = new MyDelayed("A", 1L);
        MyDelayed b = new MyDelayed("B", 2L);

        ArrayList<MyDelayed> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        Object clone = list.clone();
        System.out.println(clone.getClass());
        System.out.println(clone);

        Object[] os = new Object[2];
        os[0] = a;
        System.out.println(os.length);
        System.out.println(os[1]);

        Object[] objects = Arrays.copyOf(list.toArray(), list.size(), Object[].class);
        System.out.println(objects[1]);


        System.out.println(7 >> 1);

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE + 1);
        System.out.println(Integer.MAX_VALUE + 2);
        System.out.println(Integer.MAX_VALUE + 3);

    }


    @Test
    public void test222() {
        System.out.println(true || true && false);
    }

    @Test
    public void test111() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1.1d).setScale(2, RoundingMode.HALF_UP);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal.doubleValue());
    }

    @Test
    public void test333() {
        List<String> list = Arrays.asList("1", "2");
        list.stream().collect(Collectors.joining(","));
        String s = "1,2,3,4";

        Arrays.stream(s.split(",")).collect(Collectors.toList());
    }

    @Test
    public void test444() {
        BigDecimal bigDecimal = BigDecimal.valueOf(2.20d);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        System.out.println(bigDecimal);
    }

    @Test
    public void test555() {
        LocalDate l1 = LocalDate.of(2023, 1, 1);
        LocalDate l2 = LocalDate.now();
        System.out.println(l1.toEpochDay());
        System.out.println(l2.toEpochDay());
        System.out.println(ChronoUnit.DAYS.between(l1, l2));
    }


    /**
     * ParsePosition
     * FieldPosition
     */
    @Test
    public void test666() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M.d");
        StringBuffer ss = new StringBuffer("我的字符串缓冲区");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);

        System.out.println(DateFormat.getDateInstance().format(new Date()));
        System.out.println(DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(new Date()));
    }

    @Test
    public void test111111() {
//        List<Long> list = Arrays.asList(1L, 2L, 3L);
//        Iterator<Long> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next() == 2L){
//                iterator.remove();
//            }
//        }
//        System.out.println(list);
        List<Boolean> l1 = new ArrayList<>();
        l1.add(Boolean.TRUE);
        l1.add(Boolean.FALSE);
        Collections.sort(l1);
        System.out.println(l1);
    }


    @Test
    public void test123456() {
        Object object;
        String memberId = "1221";
        if (memberId.isEmpty() ||
                Objects.isNull(object = new Object())) {
            System.out.println("进入if");
            return;
        }
        System.out.println(object);
    }

    /**
     * fruit 初始化静态变量
     * fruit 静态代码块
     * Strawberry 初始化静态变量
     * Strawberry 静态代码块
     * fruit 初始化成员变量
     * fruit 构造代码块
     * fruit 构造函数
     * Strawberry 初始化成员变量
     * Strawberry 构造代码块
     * Strawberry 构造函数
     */
    @Test
    public void test777() {
        new Strawberry();
    }

    @Test
    public void test888() {

        List<Integer> list1 = List.of(1, 2, 3, 4);
        List<Integer> list2 = List.of(11, 22, 33, 44);
        List<Integer> list3 = List.of(11, 22, 3, 4);

        List<List<Integer>> allList = List.of(list1, list2, list3);


        List<List<Integer>> collect = allList.stream().filter(item ->
                allList.stream().anyMatch(suItem -> suItem.containsAll(item)
                        || item.containsAll(suItem))).collect(Collectors.toList());

    }

    @Test
    public void test999() {
        List<Integer> list = List.of(1, 2, 3, 4);
        boolean a = list.stream().filter(item -> item > 5).anyMatch(item -> item > 5);
        System.out.println(a);
        boolean b = list.stream().filter(item -> item > 5).allMatch(item -> item > 5);
        System.out.println(b);
        boolean c = list.stream().filter(item -> item > 5).noneMatch(item -> item > 5);
        System.out.println(c);

    }


    @Test
    public void test01() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(list.subList(1, 9));


        List<String> collect1 = List.of("1", "2").stream().collect(Collectors.toList());
        List<String> collect2 = List.of("2", "3").stream().collect(Collectors.toList());

        System.out.println(collect1.retainAll(collect2));
        System.out.println(collect1);
    }

    @Test
    public void test02() {
        System.out.println(factorial(12));
    }

    /**
     * 一个正整数的阶乘（factorial）是所有小于及等于该数的正整数的积，并且0的阶乘为1。自然数n的阶乘写作n!。
     *
     * @param num
     * @return
     */
    public long factorial(long num) {
        return num <= 1 ? 1 : num * factorial(num - 1);
    }


    @Test
    public void test03() {
        String s = null;
        System.out.println(s instanceof String);
    }

    @Test
    public void test06() {
        byte[] data = new byte[10];
        //数据范围是-128到127
        ThreadLocalRandom.current().nextBytes(data);
        for (byte b : data) {
            System.out.println(b);
            System.out.println(Integer.toBinaryString(b));
            System.out.println(b & 0xff);
            System.out.println(Integer.toBinaryString(b & 0xff));
            System.out.println("----------------");
        }
    }

    @Test
    public void test07() {
        byte[] data = new byte[10];
        ThreadLocalRandom.current().nextBytes(data);
        ULID ulid = new ULID(System.currentTimeMillis(), data);
        System.out.println(ulid.getMostSignificantBits());
        System.out.println(ulid.getLeastSignificantBits());
        System.out.println(ulid.toString());
    }


    @Test
    public void test08() {
        int i = Runtime.getRuntime().availableProcessors();
        List<List<Integer>> lists = CommonUtils.averageAssign(IntStream.rangeClosed(1, 100)
                .mapToObj(Integer::valueOf).collect(Collectors.toList()), i);
        lists.forEach(item -> System.out.println(item.size()));
    }


    @Test
    public void test09() {
        Throwable throwable = new IOException();
        UnitTest.<RuntimeException>throwException(throwable);
    }


    public static <T extends Throwable> void throwException(Throwable t) throws T {
        throw (T) t;
    }


    @Test
    public void test10() {
        Formatter formatter = new Formatter();
        //将十进制的11转化成16进制 超过两位原封展示 少于两位高位补0
        System.out.println(formatter.format("%02x", 11));

        byte b = 11;
        System.out.println(Integer.toHexString((b & 0xFF) | 0x100));

    }

    @Test
    public void test11() {
        Arrays.stream("1,2,3,4,5".split(",")).forEach(System.out::println);
    }


    @Test
    public void test() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(1, 1));
        list.add(new Pair<>(1, 2));
        list.add(new Pair<>(2, 1));
        list.add(new Pair<>(2, 2));

        list.stream().sorted(Comparator.comparingInt((Pair<Integer, Integer> item) -> item.getKey())
                        .thenComparing(Comparator.comparingInt((Pair<Integer, Integer> item) -> item.getValue()).reversed()))
                .forEach(item -> System.out.printf("key:%d" +
                        ";value:%d%n", item.getKey(), item.getValue()));

        System.out.println(List.of("1234", "5678"));

    }

    @Test
    public void test20231117001() {
        IntStream.rangeClosed(0, 10).forEach(item -> System.out.println(Math.random()));
        System.out.println(Object.class.getSuperclass());
    }

    @Test
    public void test21321312321() {
        long l = Long.parseLong("10117185536");
        long l1 = l * 1000L;
        System.out.println(l1);
        System.out.println(Long.MAX_VALUE);
    }

    @Test
    public void test2eqwewqe() {
        Long outerId = null;
        System.out.println(-outerId);
    }

    @Test
    public void test3243242() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
        for (int i = 0; i < 10; i++) {
            id = snowflake.nextId();
            System.out.println(id);
        }
    }

    @Test
    public void test1321321() {
        IntStream.rangeClosed(1, 10).forEach(item -> {
            System.out.println(IdUtil.getSnowflakeNextIdStr());
        });
    }

    @Test
    public void test213123() {
        System.out.println(Stream.empty().collect(Collectors.toMap(Function.identity(), Function.identity())));
        System.out.println(System.currentTimeMillis() - 1710316103293L);
    }


    @Test
    public void test13213() {
        List<Integer> list = Stream.iterate(0, i -> i < 10, i -> i + 1).collect(Collectors.toList());
        System.out.println(list);
        list.removeFirst();
        System.out.println(list);
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        byte b = -11;
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%02x", b));


    }

    @Test
    public void test1312() {
        BigDecimal bigDecimal = BigDecimal.valueOf(10.00000).divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP)
                .add(BigDecimal.ONE).setScale(2, RoundingMode.UP);
        System.out.println(bigDecimal);
    }

    @Test
    public void test11321321() {

        String shopNo = "1065";
        String submitStartTime = "2024-04-01 00:00:00";
        String submitEndTime = "2024-05-01 00:00:00";
        String brandCodes = "KC";

        List<Object> params = new ArrayList<>(2);
        StringBuilder querySql = new StringBuilder();
        params.add(1);
        //params.add(brandCodes);
        //***对账逻辑 预存相关单据不对账，全渠道子单order_name=35的只取ORIGIN_ORDER_NO like 'OFA%' 开头的 add by Sera 2021/4/29***/
        querySql.append("select shopNo,shopName,brandCode,couponName,couponCode,discountRate,");
        querySql.append(" sum(prodCount) prodCount,sum(proportionPrice) proportionPrice,sum(amountSettle) amountSettle");
        querySql.append(" from (select main.shop_No shopNo,main.shop_name shopName,");
        querySql.append(" ot.brand_code brandCode,ot.coupon_name couponName,ot.coupon_code couponCode,ot.discount_rate discountRate,");
        querySql.append(" (CASE WHEN main.ORDER_SOURCE = 8 THEN 0.00 WHEN main.ORDER_SOURCE IN (3, 5,7) THEN CASE WHEN main.ORDER_TYPE = 1 THEN -ot.prod_count ELSE ot.prod_count END END) prodCount,");
        querySql.append(" (CASE WHEN main.ORDER_SOURCE = 8 THEN 0.00 WHEN main.ORDER_SOURCE IN (3, 5,7) THEN CASE WHEN main.ORDER_TYPE = 1 THEN -ot.proportion_price ELSE ot.proportion_price END END) proportionPrice,");
        querySql.append(" (CASE WHEN main.ORDER_SOURCE = 8 THEN 0.00 WHEN main.ORDER_SOURCE IN (3, 5,7) THEN CASE WHEN main.ORDER_TYPE = 1 THEN -ot.proportion_Price * (1 - ot.discount_rate) ELSE ot.proportion_Price * (1 - ot.discount_rate) END END) amountSettle");
        querySql.append(" from T_ORDER_ITEM ot");
        querySql.append(" left join T_ORDER_MAIN main on ot.order_id = main.order_id");
        querySql.append(" where 1 = ?");
        querySql.append(" and !(main.ORDER_CATEGORY = 2 and main.SALE_CATEGORY = 2 and main.ORDER_SOURCE in (3,5) and STATUS_DELIVERY = 1)");
        querySql.append(" and main.ORDER_CATEGORY = ").append(2);
        querySql.append(" and (main.order_source = ").append(3).append(" or main.order_source = ").append(5).append(" or main.order_source = ").append(7).append(")");
        querySql.append(" and main.STATUS_PAY = ").append(1);
        querySql.append(" and (ot.ACCOUNT_STATUS is null or ot.ACCOUNT_STATUS = 0) ");
        querySql.append(" and (main.STATUS_DEAL != -100 ");
        querySql.append(" or main.STATUS_DEAL is null) ");
        querySql.append(" and (main.ORDER_NAME !=33 or main.ORDER_NAME is null)");
        querySql.append(" and if((main.ORDER_NAME=35 and not EXISTS (select 1 from t_order_main p where p.order_id = main.ORIGIN_ORDER_ID and p.STATUS_DEAL=-500)),(main.ORIGIN_ORDER_NO like 'OFA%' or main.ORIGIN_ORDER_NO is null),true) ");//如果是全渠道子单且未做过差异的只取原单
        querySql.append(" and main.SUBMIT_TIME >= '").append(submitStartTime).append("'");
        querySql.append(" and main.SUBMIT_TIME <= '").append(submitEndTime).append("'");
        querySql.append(" and main.SHOP_NO in (").append(shopNo).append(")");
        querySql.append(" and ot.brand_code in (").append(brandCodes).append(")");
        querySql.append(" ) res group by shopNo,shopName,brandCode,couponName,couponCode,discountRate");
        System.out.println(querySql.toString());
    }

    @Test
    public void test1231() {
        System.out.println(new BigDecimal("1.000").stripTrailingZeros().toPlainString());
    }


    /**
     * 你好(数字1,数字2，数字3)
     */
    @Test
    public void test1121() {
        List<String> list = List.of("1", "2", "3");
        String collect = list.stream().map(item -> "数字" + item)
                .collect(Collectors.joining(",", "你好(", ")"));
        System.out.println(collect);
    }

    @Test
    public void test11221311() {
        List<String> list = List.of("1_1", "2_1", "3_1");
        List<String> list1 = list.stream()
                .flatMap(u -> Arrays.stream(u.split("_")).map(item -> item.repeat(5))).toList();
        System.out.println(list1);

    }


    @Test
    public void testsdadads() {
        Map<String,Object> map = new HashMap<>();
        Object put = map.put("2", 1);
        System.out.println(put);

        Object value = map.putIfAbsent("1", 2);
        System.out.println(value);
        System.out.println(map);

    }


    @Test
    public void test12132121(){
        System.out.println(String.valueOf(System.currentTimeMillis()).length());
    }



    @Test
    public void test21312312(){
        Map<Object, Integer> map = IntStream.rangeClosed(1, 2).boxed()
                .collect(Collectors.toMap(Object::toString, Function.identity()));
        map.compute("1", (k, v) -> v == null ? 1 : v + 1);
        map.merge("2", 1, Integer::sum);
        System.out.println(map);


        System.out.println(TimeUnit.SECONDS.toMillis(10));

        //指针退避方式重试 有初始值和最大值版本
        int initialDelay = 5;
        int maxDelay = 100;
        for (int retry = 0; retry < 10; retry++){
            System.out.println(Math.min(initialDelay * Math.pow(2, retry), maxDelay));
        }


        Map<Object, Integer> cache = IntStream.rangeClosed(1, 10).boxed()
                .collect(Collectors.toMap(Object::toString,
                        Function.identity(),(v1,v2)-> v1,ConcurrentHashMap::new));

        cache.forEach((key, entry) -> {
            if (key.equals("5")) {
                cache.remove(key, entry); // 使用CAS删除
            }
        });

        System.out.println(cache);


        Map<Object, Integer> cache1 = IntStream.rangeClosed(1, 10).boxed()
                .collect(Collectors.toMap(Object::toString,
                        Function.identity(),(v1,v2)-> v1,HashMap::new));
        cache1.forEach((key, entry) -> {
            if (key.equals("5")) {
                cache1.remove(key, entry); // 使用CAS删除
            }
        });



    }




    public static void main(String[] args) throws InterruptedException {
        // 创建并启动一个新的线程
        Thread sleepingThread = new Thread(new SleepingTask());
        sleepingThread.start();

        // 让主线程等待一会儿，确保SleepingTask中的线程已经开始睡眠
        Thread.sleep(1000);

        // 中断睡眠的线程
        sleepingThread.interrupt();

        Thread.sleep(10000);
    }

    // 定义一个任务类，该类的run方法会使当前线程进入睡眠
    static class SleepingTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("线程开始睡眠...");
                // 尝试睡眠10秒
                Thread.sleep(10000);
                System.out.println("线程完成睡眠。"); // 如果未被中断，则执行此行
            } catch (InterruptedException e) {
                // 当其他线程调用当前线程的interrupt()方法时，会抛出InterruptedException
                System.out.println("线程被中断，从睡眠中醒来: " + e.getMessage());
                // 注意：当捕获到InterruptedException时，线程的中断状态会被清除，
                // 如果需要保留中断状态，可以再次调用interrupt()
                Thread.currentThread().interrupt();
            }

        }
    }


    @Test
    public void teste1232131(){
        List<OrderDto> list = new ArrayList<>();
        list.add(new OrderDto("订单1",1L,Boolean.FALSE));
        list.add(new OrderDto("订单2",1L,Boolean.FALSE));
        list.add(new OrderDto("订单3",2L,Boolean.FALSE));

        list.stream().filter(item -> !item.getIsDone())
                .forEach(item -> {
                    list.getLast().setIsDone(Boolean.TRUE);
                    System.out.println(item.getName());
                });

    }


    @Test
    public void test12321312321(){
        testExceptionReturn();
        System.out.println("finish");
    }

    private void testExceptionReturn(){
        try {
            int i = 1/0;
        }catch (Exception e) {
            return;
        }
        System.out.println("123");
    }


    @Test
    public void test21321321321(){
        YearMonth yearMonth = YearMonth.of(2025, 10);
        LocalDate localDate = yearMonth.atDay(1);
        System.out.println(localDate);
        localDate = yearMonth.atEndOfMonth();
        System.out.println(localDate);
    }


    @Test
    public void test213213213211312(){
        System.out.println(UnitTest.class.getName());
    }



    @Test
    public void test123121(){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack.search(1));
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.peek());

        System.out.println("------------------------------------------------");


        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(1);
        deque.push(2);
        deque.push(3);
        deque.push(4);



        System.out.println(deque.peek());
        System.out.println(deque.size());
        System.out.println(deque.pop());
        System.out.println(deque.size());


    }
}
