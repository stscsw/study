package cn.csw.java9;

/**
 * @author Lier
 *
 * 接口可以有私有的方法实现（是否为静态按照调用方是静态方法还是Default方法决定）
 *
 * @since 2023/4/13 15:07
 */
public interface MyInterface {

    public static void staticFun() {
        System.out.println("staticFun");
    }


    private static void privateFun() {
        System.out.println("staticFun");
    }


    default void defaultFun() {
        System.out.println("staticFun");
    }

    private void ss(){
        System.out.println("ss");
    }
}
