import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        Thread thread = new Thread(futureTask, "AA");
        thread.start();
        System.out.println(Thread.currentThread().getName());
        while (!futureTask.isDone()) {
        }
        int result2 = 2;
        Integer result = futureTask.get();
        System.out.println(result + result2);

        // 要求获得Callable线程的计算结果
        //如果没有计算完成就要去强求,会导致阻塞, 值的计算完成
    }

}
