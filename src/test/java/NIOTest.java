import cn.csw.model.Employee;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 零拷贝（尽量减少IO次数）
 * <see url="https://mp.weixin.qq.com/s?__biz=Mzg3NzU5NTIwNg==&mid=2247505986&idx=1&sn=18d2f62f53b51f6a735f103e1f0e9864&chksm=cf22076bf8558e7d7841f552e9d2c6feeec4e5f9db26e79356283758ffc830c96c1cf881ded6&scene=126&sessionid=1690852459#rd" />
 * <p>
 * <p>
 * 分片上传和断点续传
 * <see url="https://mp.weixin.qq.com/s?__biz=MzIxNjA5MTM2MA==&mid=2652456407&idx=1&sn=7dc2d3a581a1a1b414e3b70b952568f8&chksm=8c63ba58bb14334e2be016905c6992dd08c420fb2c236f03947569dac12a1ab5216133615bb2&scene=126&sessionid=1691111710#rd" />
 *
 * @author Lier
 * @since 2023/8/1 14:10
 */
public class NIOTest {

    public static final String OLD_FILE_PATH = "C:\\Users\\87973\\Desktop\\copy\\old\\jdk-8u333-windows-x64.exe";
    public static final String NEW_FILE_PATH = "C:\\Users\\87973\\Desktop\\copy\\new\\jdk-8u333-windows-x64.exe";


    private static final String FILE_PATH = "C:\\Users\\87973\\Desktop\\employee.txt";

    /**
     * 传统IO
     */
    @Test
    @SneakyThrows
    public void test1() {
        Instant startTime = Instant.now();
        File sourceFile = new File(OLD_FILE_PATH);
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(NEW_FILE_PATH)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        Instant endTime = Instant.now();
        System.out.println("传统IO传输 " + formatFileSize(sourceFile.length())
                + "到目标文件 耗时:" + Duration.between(startTime, endTime).toMillis() + "毫秒");
    }

    /**
     * transferTo
     */
    @Test
    @SneakyThrows
    public void test2() {
        Instant startTime = Instant.now();
        File sourceFile = new File(OLD_FILE_PATH);
        File targetFile = new File(NEW_FILE_PATH);
        try (FileChannel sourceChannel = new RandomAccessFile(sourceFile, "r").getChannel();
             FileChannel targetChannel = new RandomAccessFile(targetFile, "rw").getChannel()) {
            sourceChannel.transferTo(0, targetChannel.size(), targetChannel);
        }
        Instant endTime = Instant.now();
        System.out.println("NIO传输 transferTo " + formatFileSize(sourceFile.length())
                + "到目标文件 耗时:" + Duration.between(startTime, endTime).toMillis() + "毫秒");
    }

    /**
     * transferFrom
     */
    @Test
    @SneakyThrows
    public void test3() {
        Instant startTime = Instant.now();
        File sourceFile = new File(OLD_FILE_PATH);
        File targetFile = new File(NEW_FILE_PATH);
        try (FileChannel sourceChannel = new RandomAccessFile(sourceFile, "r").getChannel();
             FileChannel targetChannel = new RandomAccessFile(targetFile, "rw").getChannel()) {
            targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
        Instant endTime = Instant.now();
        System.out.println("NIO传输 transferFrom " + formatFileSize(sourceFile.length())
                + "到目标文件 耗时:" + Duration.between(startTime, endTime).toMillis() + "毫秒");
    }

    /**
     * 内存映射文件
     */
    @Test
    @SneakyThrows
    public void test4() {
        Instant startTime = Instant.now();
        File sourceFile = new File(OLD_FILE_PATH);
        File targetFile = new File(NEW_FILE_PATH);
        try (FileChannel sourceChannel = new RandomAccessFile(sourceFile, "r").getChannel();
             FileChannel targetChannel = new RandomAccessFile(targetFile, "rw").getChannel()) {
            MappedByteBuffer buffer = sourceChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceChannel.size());
            targetChannel.write(buffer);
        }
        Instant endTime = Instant.now();
        System.out.println("NIO传输 MappedByteBuffer " + formatFileSize(sourceFile.length())
                + "到目标文件 耗时:" + Duration.between(startTime, endTime).toMillis() + "毫秒");
    }


    /**
     * 计算文件大小
     * 大于900就除1024
     *
     * @param byteLength
     * @return
     */
    public String formatFileSize(long byteLength) {
        List<String> list = List.of("B", "KB", "MB", "G", "T");
        int count = 0;
        while (byteLength > 900) {
            byteLength = byteLength / 1024;
            count += 1;
        }
        return String.format("%d%s", byteLength, list.get(count));
    }

    /**
     * * UTF-8和GBK 对于英文字母都是一个字节长度  中文 UTF-8三个字节  GBK两个字节
     */
    @Test
    @SneakyThrows
    public void test5_1() {
        String str1 = "hello";
        String str2 = "你好abc";
        System.out.println(str1.getBytes("GBK").length);
        System.out.println(str2.getBytes("GBK").length);
        System.out.println(str1.getBytes("UTF-8").length);
        System.out.println(str2.getBytes("UTF-8").length);


        System.out.println(str1.length() == str1.getBytes().length);
        System.out.println(str2.length() == str2.getBytes().length);
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str2);
        System.out.print("" + str2 + " 中的汉字为:");
        while (matcher.find()) {
            System.out.print(matcher.group());
        }
    }

    /**
     * RandomAccessFile Rondom是指方便在文件的任何位置进行读写
     * seek         指定字节开始读取
     * skipBytes    跳过指定字节长度数据
     */
    @Test
    @SneakyThrows
    public void test5() {
        Employee e1 = new Employee("zhangsan", 23);
        Employee e2 = new Employee("lisi", 24);
        Employee e3 = new Employee("wangwu", 25);
        RandomAccessFile ra = new RandomAccessFile(FILE_PATH, "rw");
        ra.write(e1.getName().getBytes(StandardCharsets.UTF_8));//防止写入文件乱码
        ra.writeInt(e1.getAge());
        ra.write(e2.getName().getBytes());
        ra.writeInt(e2.getAge());
        ra.write(e3.getName().getBytes());
        ra.writeInt(e3.getAge());
        ra.close();
        RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r");
        int len = 8;
        raf.skipBytes(12);//跳过第一个员工的信息，其姓名8字节，年龄4字节
        System.out.println("第二个员工信息：");
        String str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        System.out.println("第一个员工信息：");
        raf.seek(0);//将文件指针移动到文件开始位置
        str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        System.out.println("第三个员工信息：");
        raf.skipBytes(12);//跳过第二个员工的信息
        str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        raf.close();
    }


    @Test
    public void test6() {
        System.out.println(String.class.getTypeName());
    }

    @Test
    public void test7() {
        Set<Integer> integers = Set.of(1);
        Set<Integer> integers1 = Set.of(2);
        System.out.println(new HashSet<>(integers).retainAll(integers1));


        Set<String> strings = Set.of("125_CAW200002-002");
        System.out.println(new HashSet<>(strings).retainAll(Set.of()));


        System.out.println("123456789".substring(0,100));
    }

    @Test
    public void test8() {
        Date date = new Date();
        System.out.println(date.before(date));
        System.out.println(date.after(date));
    }

}
