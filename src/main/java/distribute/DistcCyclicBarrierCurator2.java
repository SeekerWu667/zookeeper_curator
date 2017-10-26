package distribute;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * @author xueqin
 * @Description: Modified By:
 * Created by xueqin on 17/10/26.
 */
public class DistcCyclicBarrierCurator2 {
    static String path = "/curator_barrier_path1";

    public static void main(String[] args) throws Exception{
        for (int i=0;i<5;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                        client.start();
                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, path, 5);
                        Thread.sleep(Math.round(Math.random()*3000));
                        System.out.println(Thread.currentThread().getName() + "号进入barrier");
                        barrier.enter();
                        System.out.println("启动。。。");
                        Thread.sleep(Math.round(Math.random()*3000));
                        barrier.leave();
                        System.out.println("退出....");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

    }
}

