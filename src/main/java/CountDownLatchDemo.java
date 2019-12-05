import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CountDownLatchDemo {


    static CountDownLatch countDownLatch = new CountDownLatch(4);


    public static void main(String[] args) {

        for (int i = 1; i <=4 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            },FourSeasons_enum.findByCode(i).getSeasons()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"结束");

    }

}
