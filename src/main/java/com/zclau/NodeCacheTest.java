package com.zclau;

import com.zclau.server.TestingServer;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;

/**
 * Created by liuzicong on 30/7/2017.
 */
public class NodeCacheTest extends TestingServer {

    static String nodeCachePath = "/zk-book/nodecache";

    @Override
    void doTest() throws Exception {
        client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(nodeCachePath, "init".getBytes());

        NodeCache nodeCache = new NodeCache(client, nodeCachePath, false);
        nodeCache.start(true); //设置为true，第一次启动就会立刻从zk上读取对应节点的数据内容并保存在Cache中
        nodeCache.getListenable().addListener(() ->
                        System.out.println("Node data update, new data: " + new String(nodeCache.getCurrentData().getData())));

        client.setData().forPath(nodeCachePath, "updated".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(nodeCachePath);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        new NodeCacheTest().createTest();
    }
}
