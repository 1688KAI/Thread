import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueDemo {

    public static void main(String[] args) {
        SyncQueue();

    }

    private static void SyncQueue() {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();
        new Thread(() -> {
            try {
                try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){  e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + blockingQueue.take());
                try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){  e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + blockingQueue.take());
                try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){  e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }
}
