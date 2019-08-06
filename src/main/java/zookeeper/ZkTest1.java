package zookeeper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

public class ZkTest1 {

    public static void main(String[] args) throws Exception {

        CuratorZookeeperClient client = new CuratorZookeeperClient("127.0.0.1:2181", 10000, 10000, null, new RetryOneTime(1));
        client.start();
        try {
            System.out.println(client.blockUntilConnectedOrTimedOut());//阻塞当前线程直到客户端连上zk或者连接超时了
            String path = client.getZooKeeper().create("/ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println(path);
        } finally {
            client.close();
        }

    }

}


