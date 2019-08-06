package java11;



import org.junit.Test;

import java.io.*;
import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class mainTest {

//    @Test
//    public void test1() {
//        var v = "abc";
//        System.out.println(v);
//        System.out.println(v.getClass());
////      jshell上没有问题 这里编译器报错
////        Consumer<String> consumer = (@Deprecated var t) -> System.out.println(t.toUpperCase());
//    }
//
//    @Test
//    public void test2() {
//        List<String> list = List.of("aa", "bb", "cc", "DD");
//        System.out.println(list);
//        System.out.println(list.getClass());//ImmutableCollections$ListN  内部類
//
//        //重读元素添加会抛异常
//        Set<Integer> set = Set.of(1, 2, 3, 4, 5);
//        System.out.println(set);
//        System.out.println(set.getClass());//ImmutableCollections$SetN 内部类
//    }
//
//    @Test
//    public void test3() {
//        Stream.of(1, 2, 5).forEach(System.out::println);
//        LocalDate localDate = LocalDate.of(2018, 11, 11);
//        System.out.println(localDate);
//    }
//
//    @Test
//    public void test4() {
//        Stream.generate(() -> new Random().nextInt(100)).forEach(System.out::println);
//        Stream.iterate(2, item -> item < 100, item -> item * 2 + 1).forEach(System.out::println);
//    }
//
//    @Test
//    public void test5() {
//        //抛出空指针异常
////        Stream.of(null).forEach(System.out::println);
//
//        Stream.ofNullable(null).forEach(System.out::println);//内部转化成Stream.empty()
//    }
//
//
//    @Test
//    public void test6() {
//        Stream<Integer> stream1 = Stream.of(1, 3, 20, 24, 5);
//        stream1.takeWhile(item -> item % 2 == 1).forEach(System.out::println);
//        stream1 = Stream.of(1, 3, 20, 24, 5);
//        stream1.dropWhile(item -> item % 2 == 1).forEach(System.out::println);
//    }
//
//    @Test
//    public void test7() {
//        String s1 = "\t   \r\n  abc  \t   ";
//        System.out.println(s1.isBlank());
//        String s2 = s1.trim();
//        System.out.println(s2);
//        System.out.println(s2.length());
//        String s3 = s1.strip();
//        System.out.println(s3);
//        System.out.println(s3.length());
//        System.out.println(s1.stripLeading());
//        System.out.println(s1.stripTrailing());
//    }
//
//    @Test
//    public void test8() throws IOException {
//        String s = "java";
//        System.out.println(s.repeat(3));
//        String s2 = "1\r\n2\r\n3";
//        s2.lines().forEach(System.out::println);
//
//        FileInputStream fis = new FileInputStream("src\\main\\java\\com\\csw\\java11\\mainTest.java");
//        byte[] bytes = new byte[fis.available()];
//        fis.read(bytes);
//        String ss = new String(bytes);
//        ss.lines().forEach(System.out::println);
//
//    }
//
//    @Test
//    public void test9() {
//        Optional<Object> o = Optional.ofNullable(null);
//        Object o2 = o.orElse(new Object());
//        System.out.println(o2);
//        Object o3 = o.orElseGet(() -> new Object());
//        System.out.println(o3);
//        Object o1 = o.orElseThrow(() -> new UnsupportedOperationException());
//    }
//
//
//    @Test
//    public void test10() throws IOException {
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        FileInputStream is = new FileInputStream("src\\main\\java\\com\\csw\\java11\\mainTest.java");
//        try (FileOutputStream fileOutputStream = new FileOutputStream("D:\\file")) {
//            is.transferTo(fileOutputStream);
//        }
//        is.close();
//    }
//
//
//    //同步调用
//    @Test
//    public void test11() throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder(URI.create("https://wei.topscore.com.cn/wechat-api/login/wechat/test"))
//                .GET().build();
//        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
//        HttpResponse<String> send = client.send(request, bodyHandler);
//        System.out.println(send.body());
//    }
//
//    //异步调用
//    @Test
//    public void test12() throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder(URI.create("https://wei.topscore.com.cn/wechat-api/login/wechat/test"))
//                .GET().build();
//        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
//        CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, bodyHandler);
//        HttpResponse<String> send = httpResponseCompletableFuture.get();
//        System.out.println(send.body());
//    }


}
