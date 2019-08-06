package proxy.ClassLoaderTest;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MyClassLoaderTest {

    @Test
    public void test1() {
        ClassLoader classLoader = MyClassLoaderTest.class.getClassLoader();
        StringBuilder split = new StringBuilder("|--");
        boolean needContinue = true;
        while (needContinue) {
            System.out.println(split.toString() + classLoader);
            if (classLoader == null) {
                needContinue = false;
            } else {
                classLoader = classLoader.getParent();
                split.insert(0, "\t");
            }
        }
    }

    @Test
    public void test2() {
        String[] ss = {"a", "b", "c"};
        System.out.println(ss[0].getClass().getClassLoader());
        System.out.println(ss.getClass().getClassLoader());
    }


    @Test
    public void test3() {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            System.out.println(key + " : " + properties.getProperty((String) key));
        }
    }

    @Test
    public void test4() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
        System.out.println(simpleDateFormat.format(new Date()));
    }

}
