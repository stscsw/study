package common;



import org.junit.Test;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UnitTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        Integer a = 3;
//        Integer b = 5;
//        System.out.format("a=%d,b=%d\n", a, b);
//        swamp(a, b);
//        System.out.format("a=%d,b=%d\n", a, b);


        System.out.println(getNum());
    }

    private static void swamp(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {
        Integer tempa = new Integer(a);
        Integer tempb = new Integer(b);
        Class clazz = Integer.class;
        Field field = clazz.getDeclaredField("value");
        field.setAccessible(true);
        field.set(a, tempb);
        field.set(b, tempa);
    }

    public static int getNum() {
        int a = 10;
        try {
            a = 20;
            return a;
        } catch (Exception e) {
            a = 30;
            return a;
        } finally {
            a = 40;
            //return a;
        }
    }


    @Test
    public void test() {
        int i = 0;
        i += 1;
        i += 2;
        System.out.println(i);
    }


    @Test
    public void test1() {
        Optional.ofNullable(null).ifPresent(System.out::println);
        Optional.ofNullable("1").ifPresent(item -> {
            throw new RuntimeException();
        });
        Object a = null;
        Long l = (Long) a;
    }

    @Test
    public void test2() throws InterruptedException {
        Instant instant1 = Instant.now();
        Thread.sleep(1000);
        Instant instant2 = Instant.now();
        System.out.println(Duration.between(instant1, instant2).toMillis());
    }

    @Test
    public void test3() {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallelStream().forEach(item -> {
            if (item == 2) {
                return;
            }
            System.out.println(item);
        });
    }


    @Test
    public void test4() {
        LocalDate now1 = LocalDate.of(2018, 12, 31);
        LocalDate now2 = LocalDate.of(2019, 01, 01);
        LocalDate startDate1 = LocalDate.of(now1.getYear(), now1.getMonth().plus(-2), 1);
        LocalDate startDate2 = LocalDate.of(now1.getYear(), now2.getMonth().plus(-2), 1);
        System.out.println(startDate1);
        System.out.println(startDate2);
    }


    @Test
    public void test5() {
        LocalDate now1 = LocalDate.of(2018, 11, 30);
        LocalDate now2 = LocalDate.of(2019, 12, 1);
        LocalDate startDate1 = LocalDate.of(now1.getYear(), now1.getMonth().plus(-2), 1);
        LocalDate startDate2 = LocalDate.of(now1.getYear(), now2.getMonth().plus(-2), 1);
        System.out.println(startDate1);
        System.out.println(startDate2);
    }


    @Test
    public void test6() {
        Map<String,Object> map = new HashMap<>();
        map.put("1","11");
        map.put("2","21");
        map.put("3","31");
        map.put("4","41");
        map.put("5","51");
        Object remove = map.remove("3");
        System.out.println(remove);


    }

}
