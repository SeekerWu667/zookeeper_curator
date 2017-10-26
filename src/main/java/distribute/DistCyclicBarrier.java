package distribute;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xueqin
 * @Description: Modified By:
 * Created by xueqin on 17/10/26.
 */
public class DistCyclicBarrier {
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1号选手")));
        executor.submit(new Thread(new Runner("2号选手")));
        executor.submit(new Thread(new Runner("3号选手")));
    }

}

class Runner implements Runnable {
    private String name;

    public Runner(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name+" 准备好了");
        try {
            DistCyclicBarrier.barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(name+" 起跑!");
    }
}
