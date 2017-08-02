package com.zclau;

import org.apache.zookeeper.data.Stat;

/**
 * Created by liuzicong on 27/7/2017.
 */
public class TestUpdateNode extends TestingServer {
    @Override
    void doTest() throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("old version: " + stat.getVersion());
        System.out.println("success set node for : " + path + ", new version: " +
                        client.setData().withVersion(stat.getVersion()).forPath(path, "version-2".getBytes()).getVersion());
    }

    public static void main(String[] args) throws Exception {
        new TestUpdateNode().createTest();
    }
}
