package com.zclau;

import org.apache.zookeeper.data.Stat;

/**
 * Created by liuzicong on 27/7/2017.
 */
public class TestDeleteNode extends TestingServer {
    @Override
    void doTest() throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
    }

    public static void main(String[] args) throws Exception {
        new TestDeleteNode().createTest();
    }
}
