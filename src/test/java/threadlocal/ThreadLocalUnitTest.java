package threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lier
 * @since 2022/6/14 17:27
 */
public class ThreadLocalUnitTest {

    /**
     * <a url="https://blog.csdn.net/baidu_22254181/article/details/82555485"></a>
     * 测试引用
     * 强引用  StrongReference
     * 软引用  SoftReference
     * 弱引用  WeakReference
     * 虚引用  PhantomReference
     */
    @Test
    public void reference() throws InterruptedException {

        //强引用
        Object o = new Object();
        //引用队列
        ReferenceQueue<String> queue = new ReferenceQueue<>();

        //软引用
        SoftReference<String> softReference = new SoftReference<>("abc", queue);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(queue.poll());

        //弱引用
        WeakReference<String> weakReference = new WeakReference<>("123", queue);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(queue.poll());

        //虚引用
        PhantomReference<String> phantomReference = new PhantomReference<>("!@#", queue);
        System.out.println(phantomReference.get());
        System.gc();
        System.out.println(queue.poll());
    }


    /**
     * ThreadLocal
     * InheritableThreadLocal
     * TransmittableThreadLocal
     */
    @Test
    public void threadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();
        threadLocal.set("abc");
        Thread thread = Thread.currentThread();
        inheritableThreadLocal.set("123");
        transmittableThreadLocal.set("init_value1");


        System.out.println(threadLocal.get());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println("threadLocal_" + threadLocal.get());
            System.out.println("inheritableThreadLocal_" + inheritableThreadLocal.get());
        });

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);
        ttlExecutorService.submit(() -> {
            System.out.println("transmittableThreadLocal_"+transmittableThreadLocal.get());
        });
        transmittableThreadLocal.set("init_value2");
        ttlExecutorService.submit(() -> {
            System.out.println("transmittableThreadLocal_"+transmittableThreadLocal.get());
        });





    }
}
