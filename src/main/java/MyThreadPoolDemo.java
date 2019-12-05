import java.util.concurrent.*;

/**
 * 第四种获取/使用java多线程的方式 线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
//                new ThreadPoolExecutor.CallerRunsPolicy());
//                new ThreadPoolExecutor.DiscardOldestPolicy());
//                new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 1; i <= 20; i++) {
                final int tempInt = i;
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "这个是" + tempInt);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }

    }

    private static void threadPoll_Init() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //一池固定个线程数
//        ExecutorService executorService = Executors.newSingleThreadExecutor ();
//        //一池一线线程
//        ExecutorService executorService = Executors.newCachedThreadPool ();
        // 一池N个线程

//模拟10个用户来办理业务,每个用户就是一个来自外部的请求线程
        try {
            for (int i = 1; i <= 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
