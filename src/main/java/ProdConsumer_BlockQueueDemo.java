import jdk.management.resource.ResourceType;
import org.omg.CORBA.TIMEOUT;

import javax.lang.model.element.VariableElement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


class MyResouce {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;
    public MyResouce() {
    }
    public MyResouce(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void myProd() throws Exception {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = String.valueOf(atomicInteger.incrementAndGet());
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "插入" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "插入" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"flag=="+FLAG);
    }
    public void myConsumer() throws Exception{
        String result = null;
        while (FLAG){
            result= blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 超时2秒 没有取得蛋糕 消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "消费" + result + "成功");
        }

    }
    public void stop(){
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        MyResouce myResouce = new MyResouce(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"生成启动");
            try {
                myResouce.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"消费启动");
            try {
                myResouce.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("五秒钟介结束活动结束");
        myResouce.stop();
    }
}
