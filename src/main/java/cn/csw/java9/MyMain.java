package cn.csw.java9;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lier
 *
 *
 * Stream.iterate 添加中止生成数据的 Predicate
 * dropWhile
 * takeWhile
 * jdk自带新的httpClient HttpRequest HttpResponse
 * List.of() Set.of() Map.of() Map.ofEntries()
 *
 * @since 2023/4/13 15:50
 */
public class MyMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        Stream.iterate(1, i -> i + 1).limit(2).forEach(System.out::println);

        Stream.iterate(1, i -> i < 3, i -> i + 1).forEach(System.out::println);


        List<Integer> collect = Stream.iterate(1, i -> i + 1).limit(10)
                .takeWhile(i -> i <= 5).collect(Collectors.toList());
        System.out.println(collect);

        collect = Stream.iterate(1, i -> i + 1).limit(10)
                .dropWhile(i -> i <= 5).collect(Collectors.toList());
        System.out.println(collect);


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).GET().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.statusCode());
        System.out.println(httpResponse.version().name());
        System.out.println(httpResponse.body());



        Map<String, String> map = Map.ofEntries(Map.entry("key1", "value1"), Map.entry("key2", "value2"));
        System.out.println(map);
    }
}
