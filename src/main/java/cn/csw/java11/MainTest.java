package cn.csw.java11;

import com.alibaba.ttl.TtlCopier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 使用单个命令运行 Java 文件
 * isBlank
 *
 * @author Lier
 * @since 2023/4/20 16:14
 */
public class MainTest {

    /**
     * java.lang.String
     * isBlank()
     * strip()  [Character.isWhitespace(c)]
     * stripLeading()
     * stripTrailing()
     * repeat()
     * var 来声明隐式类型 lambda 表达式的形式参数
     * Files
     * readString()
     * writeString()
     * Predicate
     *  static not()
     *  and()
     *  or()
     *  negate()
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("\n".isBlank());//true
        System.out.println("\t".isBlank());//true


        System.out.println("".isBlank());//true
        System.out.println("".isEmpty());//true
        System.out.println(" ".isBlank());//true
        System.out.println(" ".isEmpty());//false

        String lineString = "第一行\n第二行\n第三行";
        lineString.lines().forEach(System.out::print);
        System.out.println();

        System.out.println("--------------------");
        String s = "\t abc \n";
        System.out.println("abc".equals(s.trim()));
        System.out.println("abc".equals(s.strip()));
        Character c = '\u2000';
        String s1 = c + "abc" + c;
        System.out.println(Character.isWhitespace(c));
        System.out.println("abc".equals(s1.trim()));
        System.out.println("abc".equals(s1.strip()));

        System.out.println("abc".repeat(3));


        Path path = Files.writeString(Files.createTempFile("test", ".txt"), "我写入的字符串");
        System.out.println(path);
        String string = Files.readString(path);
        System.out.println(string);



        Predicate<String> p1 = (String ss1) -> ss1.contains("a");
        Predicate<String> p2 = (String ss1) -> ss1.contains("b");
        Predicate<String> p3 = (String ss1) -> ss1.contains("c");
        Predicate<String> allAnd = p1.and(p2).and(p3);
        System.out.println(allAnd.test("abc"));//true
        System.out.println(allAnd.test("ab"));//false
        Predicate<String> allOr = p1.or(p2).or(p3);
        System.out.println(allOr.test("abc"));//true
        System.out.println(allOr.test("ab"));//true
        Predicate<String> negateP1 = p1.negate();
        System.out.println(negateP1.test("abc"));//false
        System.out.println(negateP1.test("ab"));//false
        Predicate<String> notP1 = Predicate.not(p1);
        System.out.println(notP1.test("abc"));//false
        System.out.println(notP1.test("ab"));//false

    }
}
