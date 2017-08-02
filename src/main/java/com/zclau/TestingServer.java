package com.zclau;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by liuzicong on 26/7/2017.
 */
public abstract class TestingServer {

    org.apache.curator.test.TestingServer server;
    CuratorFramework client;

    static String path = "/zk-book/c1";


    public TestingServer() {
    }

    protected void initServer() {
        if (Objects.nonNull(server)) {
            return;
        }
        try {
            server = new org.apache.curator.test.TestingServer(2181, new File("/Users/zclau/workspace/learn-zk/data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initClient() {
        if (Objects.nonNull(client)) {
            return;
        }
        initServer();
        client = CuratorFrameworkFactory.builder()
                        .connectString(server.getConnectString())
                        .sessionTimeoutMs(5000)
                        .retryPolicy(new ExponentialBackoffRetry(1000,3 ))
                        .build();
    }

    protected void startClient() {
        if (Objects.nonNull(client)) {
            client.start();
        }
    }
    protected void closeClient() {
        if (Objects.nonNull(client)) {
            client.close();
        }
    }

    protected void shutdownServer() {
        if (Objects.nonNull(server)) {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTest() throws Exception {
        initServer();
        initClient();
        startClient();

        doTest();

        closeClient();
        shutdownServer();
    }

     abstract void doTest() throws Exception;
}
