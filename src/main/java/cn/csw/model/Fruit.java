package cn.csw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lier
 * @since 2023/3/29 14:49
 */
public class Fruit {

    public static Map<String, Object> mf = new HashMap<String,Object>() {
        {
            System.out.println("fruit 初始化静态变量");
        }
    };

    static {
        System.out.println("fruit 静态代码块");
    }

    public Map<String, Object> f = new HashMap<String,Object>() {
        {
            System.out.println("fruit 初始化成员变量");
        }
    };

    {
        System.out.println("fruit 构造代码块");
    }

    public Fruit() {
        System.out.println("fruit 构造函数");
    }
}
