package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnitTest {

    String host = "10.10.0.31";
    int port = 6379;
    String password = "oQHQYdWi5jg/6o7J/g==";


    @Test
    public void scan() {
        Jedis jedis = new Jedis(host, port, 1000);
        jedis.auth(password);
        ScanParams scanParams = new ScanParams();
        scanParams.count(10);
        scanParams.match("*");
        ScanResult<String> scan = jedis.scan("0", scanParams);
        System.out.println(scan.getStringCursor());
        System.out.println(scan.getResult().size());
        jedis.close();
    }

    @Test
    public void forScan() {
        Jedis jedis = new Jedis(host, port, 1000);
        jedis.auth(password);
        ScanParams scanParams = new ScanParams();
        List<String> result = new ArrayList<>();
        scanParams.count(100);
        scanParams.match("*");
        ScanResult<String> scan = jedis.scan("0", scanParams);
        result.addAll(scan.getResult());
        while (!"0".equals(scan.getStringCursor())) {
            scan = jedis.scan(scan.getStringCursor(), scanParams);
            result.addAll(scan.getResult());
        }
        System.out.println(scan.getStringCursor());
        System.out.println(result.size());
        jedis.close();
    }

}
