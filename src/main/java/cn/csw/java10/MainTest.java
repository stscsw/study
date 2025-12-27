package cn.csw.java10;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lier
 *
 * 局部变量的类型推断 var关键字
 * 新增API：ByteArrayOutputStream
 * 新增API：List、Map、Set copyOf
 * 新增API： Collectors收集器
 * toUnmodifiableList():
 * toUnmodifiableSet():
 * toUnmodifiableMap(Function, Function):
 * toUnmodifiableMap(Function, Function, BinaryOperator):
 * @since 2023/4/20 15:34
 */
public class MainTest {

    public static void main(String[] args) {
        var list = new ArrayList<String>();
        list.add("aaa");
        System.out.println(list);

        var list1 = new ArrayList<>();
        list1.add("abc");
        list1.add(1);
        System.out.println(list1);

//        编译报错 会显示 List<Integer> 不能转化为List<String>
//        list = new ArrayList<Integer>();


        for (var s : list) {
            System.out.println(s);
        }

        for (var i = 1; i < list1.size(); i++) {
            System.out.println(i);
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(1);
        outputStream.write(2);
        outputStream.write(5);
        byte[] byteArray = outputStream.toByteArray();
        for (byte b : byteArray) {
            System.out.println(b);
        }

        outputStream.reset();
        byteArray = outputStream.toByteArray();
        for (byte b : byteArray) {
            System.out.println(b);
        }

        List<String> l1 = List.copyOf(list);

        List<Integer> unmodifiableList = Stream.of(1, 2, 3).collect(Collectors.toUnmodifiableList());

    }
}
