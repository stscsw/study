package common;

import redis.clients.jedis.Jedis;

public class JedisTest {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("10.10.0.232", 6379);
        jedis.auth("123456");
        jedis.connect();//连接
//        jedis.set("hello",null);
        jedis.set("hello",Thread.currentThread().getName());
        jedis.close();
    }

}
