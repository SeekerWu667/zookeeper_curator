package distribute;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Executors;

/**
 * @author xueqin
 * @Description: Modified By:
 * Created by xueqin on 17/10/26.
 */
public class DistCyclicBarrierCurator {
    static String path = "/curator_barrier_path";
    static DistributedBarrier barrier;

    public static void main(String[] args) throws Exception{
        for (int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                        client.start();
                        barrier = new DistributedBarrier(client, path);
                        System.out.println(Thread.currentThread().getName()+"号 barrier 设置");
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.err.println("启动。。。");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(2000);

        barrier.removeBarrier();
    }
}
