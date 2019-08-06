package proxy.dynamicaop;

import java.lang.reflect.Proxy;

public class ProxyMain {

    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件(代理类的字节码内容保存在了项目根目录下，文件名为$Proxy0.class)
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //需要代理的类接口，被代理类实现的多个接口都必须在这这里定义
        Class[] proxyInterface = new Class[]{IBusiness.class, IBusiness2.class};
        //构建AOP的Advice，这里需要传入业务类的实例
        LogInvocationHandler handler = new LogInvocationHandler(new Business());
        //生成代理类的字节码加载器
        ClassLoader classLoader = Business.class.getClassLoader();
        //织入器，织入代码并生成代理类
        IBusiness2 proxyBusiness = (IBusiness2) Proxy.newProxyInstance(classLoader, Business.class.getInterfaces(), handler);
        proxyBusiness.doSomeThing2();
        ((IBusiness) proxyBusiness).doSomeThing();
    }
}
