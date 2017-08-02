package com.zclau;

import com.zclau.server.TestingServer;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liuzicong on 2/8/2017.
 */
public class TestLock extends TestingServer {

    static String lock_path = "/lock_path";


    @Override
    void doTest() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        CountDownLatch beginLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    beginLatch.await();
                    lock.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                String orderNo = sdf.format(new Date());
                System.out.println("生成的订单号是：" + orderNo);
                try {
                    lock.release();
                    endLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        beginLatch.countDown();
        endLatch.await();
    }

    public static void main(String[] args) throws Exception {
        new TestLock().createTest();
    }
}
