import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {




    /**
     * 1 CAS 是什么？ ===》compareAndSet
     *  比较并交换
     *
     */
    public static void main(String[] args) {

        final AtomicInteger atomicInteger = new AtomicInteger(5);


        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.get());
//        System.out.println(atomicInteger.compareAndSet(5,2019)+"\t current data "+atomicInteger.get());
//        System.out.println(atomicInteger.compareAndSet(5,2049)+"\t current data "+atomicInteger.get());
    }
}
