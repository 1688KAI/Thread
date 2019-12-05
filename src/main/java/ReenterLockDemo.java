import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable {

    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked senSMS()");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t ##########invoked sendEmail()");
    }
    ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ************ invoked get()");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked set()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class ReenterLockDemo {
    /**
     * 可重入锁 (也叫递归锁)
     *
     * @param args 指的是同一个线程外层函数获得锁之后,内存递归函数仍然能获取该锁的代码
     *             在同一个线程在外层方法获取锁的时候 在进入内层方法会自动获取锁
     *             <p>
     *             <p>
     *             case one synchronized 典型的可重入锁
     *             也即是 线程可以进入任何一个他已经拥有的锁所同步着的代码块
     *             t1	 invoked senSMS()
     *             t1	 ##########invoked sendEmail()
     *             t2	 invoked senSMS()
     *             t2	 ##########invoked sendEmail()
     *             <p>
     *             case two ReentrantLock 典型的可重入锁
     *             t1	 ************ invoked get()
     *             t1	 invoked set()
     *             t2	 ************ invoked get()
     *             t2	 invoked set()
     */
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(phone, "t1").start();
        new Thread(phone, "t2").start();
    }
    private static void caseOne() {
        final Phone phone = new Phone();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    phone.sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    phone.sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
