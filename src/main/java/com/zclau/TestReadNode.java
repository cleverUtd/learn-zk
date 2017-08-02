package com.zclau;

import com.zclau.server.TestingServer;
import org.apache.zookeeper.data.Stat;

/**
 * Created by liuzicong on 27/7/2017.
 */
public class TestReadNode extends TestingServer {
    @Override
    void doTest() throws Exception {
        System.out.println(new String(client.getData().storingStatIn(new Stat()).forPath(path)));
    }

    public static void main(String[] args) throws Exception {
        new TestReadNode().createTest();
    }
}
