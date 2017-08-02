package com.zclau;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by liuzicong on 2/8/2017.
 */
public class DistAtomicInt extends TestingServer {

    static String distatomicint_path = "/distatomicint_path";

    @Override
    void doTest() throws Exception {
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, distatomicint_path, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("Result: " + rc.succeeded());
        System.out.println(rc.preValue() + "," + rc.postValue());
    }

    public static void main(String[] args) throws Exception {
        new DistAtomicInt().createTest();
    }
}
