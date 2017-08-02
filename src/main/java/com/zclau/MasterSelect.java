package com.zclau;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * Created by liuzicong on 30/7/2017.
 */
public class MasterSelect extends TestingServer {
    /**
     * 场景：对于一个复杂的任务，仅需要从集群中选举出一台进行处理即可。借助zk可以比较方便地实现
     *
     * 大体思路如下：
     *  选择一个根节点，例如/master_select，多台机器同时向该节点创建一个子节点/master_select/lock，最终只有一台机器
     *  能创建成功
     */

    static String master_path = "/master_path";

    @Override
    void doTest() throws Exception {
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为master角色");
                Thread.sleep(3000);
                System.out.println("完成master操作，释放master权利");
            }
        });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }


    public static void main(String[] args) throws Exception {
        new MasterSelect().createTest();
    }

}
