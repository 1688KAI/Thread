import java.sql.Time;
import java.util.List;
import java.util.concurrent.*;

/**
 * ArrayBlockingQueue :是一个基于数组结构的有界阻塞队列,此队列按FIFO(先进先出) 原则对元素进行排序
 * LinkedBlockingQueue: 一个基于链表结构的阻塞队列,此队列按FIFO (先进先出) 排序元素. 吞吐量通常要高于 ArrayBlockingQueue
 * SynchronousQueue :一个不存储元素的阻塞队列.每个插入操作必须等到另外一个线程调用移除的操作,否则插入操作一直处于阻塞状态,吞吐量通常要高于LinkedBlockingDeque
 *
 *
 * 1 队列
 *
 * 2 阻塞队列
 * 2.1 阻塞队列有没有好的一面
 * 2.2 不得不阻塞,你如何管理
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
//        ArrayBlockingQueue
//        LinkedBlockingQueue
//        SynchronousQue
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);


        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));

    }
}
