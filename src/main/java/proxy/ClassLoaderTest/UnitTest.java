package proxy.ClassLoaderTest;

import org.junit.Test;

import java.net.URL;

public class UnitTest {

    /**
     * BootStrap ClassLoader：称为启动类加载器/引导类加载器  加载的资源 jre/lib
     *
     */
    @Test
    public void BootStrapTest(){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }
}
