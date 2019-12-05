import java.util.concurrent.TimeUnit;

/**
 *
 *
 *
 *
 * 死锁是指两个或两个以上的进程在执行过程中,
 * 因争夺资源而造成的的一种互相等待的现象
 * 若无外力干涉那他们都将无法推进下去
 */


class HoldLockThread implements Runnable{
    String lockA;
    String lockB;
    public HoldLockThread() {
    }
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"自己持有"+lockA+"要获取"+lockB);
            try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){  e.printStackTrace(); }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"自己持有"+lockB+"要获取"+lockA);
            }
        }

    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        new Thread(new HoldLockThread("AA","BB")).start();
        new Thread(new HoldLockThread("BB","AA")).start();
    }
}
