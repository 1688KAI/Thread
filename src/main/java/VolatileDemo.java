import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int number = 0;
    public void addTo60() {
        this.number = 60;
    }
    //请注意， 此时number前面是加了volatile 关键字修饰的，volatile不保证原子性
    public void addPlusPlus() {
        number++;
    }
    AtomicInteger atomicInteger=new AtomicInteger();
    public void myAtomicInteger(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1 验证volatile的可见性
 *  1.1 假如 int number=0; number 变量之前根本没有添加volatile 关键字修饰没有可见性
 *  1.2 添加volatile, 可以解决可见性问题
 * 2 验证volatile不保证原子性
 *  2.1 原子性指的是什么意思？
 *      不可分割，完整性，也及某个线程在做某个业务时，中不可以被加塞或者被分割。
 *      需要整体完整 要么同时成功 要么同时失败。
 *  2.2 如何解决原子性
 *      加sync
 *      使用juc下的AtomicInteger
 *
 *
 *
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 1; i <=20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 100000; j++) {
                    myData.addPlusPlus();
                    myData.myAtomicInteger();
                }
            },String.valueOf(i)).start();
        }
        //需要等待上面20个线程都全部计算完成后，再用main线程去的最终的结果只看是多少？
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t int type finally number value "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger type finally number value "+myData.atomicInteger);

    }
    // volatile 可以保证可见性， 及时通知其他线程， 主物理内存的值已经被修改。
    private static void visibilityByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t updated number value\t" + myData.number);
            myData.addTo60();
        }, "AA").start();
        while (myData.number == 0) {
        }
        System.out.println(Thread.currentThread().getName());
    }
}


