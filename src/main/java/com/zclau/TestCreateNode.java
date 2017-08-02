package com.zclau;

import com.zclau.server.TestingServer;
import org.apache.zookeeper.CreateMode;

/**
 * Created by liuzicong on 26/7/2017.
 */
public class TestCreateNode extends TestingServer {


    @Override
    void doTest() throws Exception {
        client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(path, "init".getBytes());
    }


    public static void main(String[] args) {
        try {
            new TestCreateNode().createTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
