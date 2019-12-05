import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in (～￣(OO)￣)ブ");
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoke myUnlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
            }finally {
                spinLockDemo.myUnlock();
            }
        },"AA").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
        }
        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.myUnlock();

        },"BB").start();
    }
}
