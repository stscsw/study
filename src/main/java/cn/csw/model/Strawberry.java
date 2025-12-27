package cn.csw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lier
 * @since 2023/3/29 14:52
 */
public class Strawberry extends Fruit {

    public static Map<String, Object> mf = new HashMap<String,Object>() {
        {
            System.out.println("Strawberry 初始化静态变量");
        }
    };

    static {
        System.out.println("Strawberry 静态代码块");
    }

    public Map<String, Object> f = new HashMap<String,Object>() {
        {
            System.out.println("Strawberry 初始化成员变量");
        }
    };

    {
        System.out.println("Strawberry 构造代码块");
    }

    public Strawberry() {
        System.out.println("Strawberry 构造函数");
    }
}
