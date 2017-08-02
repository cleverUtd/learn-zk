package com.zclau;

import com.zclau.server.TestingServer;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuzicong on 29/7/2017.
 */
public class CreateNodeBackground extends TestingServer {

    static CountDownLatch semaphore = new CountDownLatch(1);
    static ExecutorService tp = Executors.newFixedThreadPool(2);

    @Override
    void doTest() throws Exception {
        System.out.println("Main thread: " + Thread.currentThread().getName());
        client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .inBackground((client, event) -> {
                            System.out.println("event[code: " + event.getResultCode() + ", type: " + event.getType() + "]");
                            System.out.println("Thread of processResult: " + Thread.currentThread().getName());
                            semaphore.countDown();
                        }).forPath(path, "hello".getBytes());
    }

    @Override
    protected void closeClient() {
        try {
            semaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.closeClient();
    }

    @Override
    protected void shutdownServer() {
        super.shutdownServer();
    }

    public static void main(String[] args) throws Exception {
        new CreateNodeBackground().createTest();

    }
}
