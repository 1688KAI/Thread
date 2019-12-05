import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * syncchronized 和Lock的有什么区别 ?  用新的Lock有什么好处 ? 你举例说明
 * <p>
 * <p>
 * 1.原始构成
 * synchronize 是关键字属于JVM层面
 * monitorenter (底层是通过monitor对象来完成,其实wait/notify等方法也依赖于monitor对象只有在同步块或者同步方法中wait/notify等方法)
 * Lock是具体类(java.util.concurrent.Lock)是API层面的的锁
 * <p>
 * 2.使用方法
 * synchronize 不需要用户去手动释放锁,当synchronize代码执行完成后系统会自动让现场释放对锁的占用
 * ReentrantLock 则需要用户手动去释放锁若没有主动释放锁,就有可能导致出现死锁现象.
 * 需要lock () 和unlock方法配置 tyr finally 语句块完成''
 * <p>
 * 3. 等待是否中断
 * synchronize 不可中断 除非抛出异常或者正常运行完成
 * ReentrantLock 可中断 1. 设置超时方法 tryLock(long timeout timeUnit unit)  2.lockInterruptibly()代码块中 调用 interrupt方法
 * <p>
 * <p>
 * 4. 加锁是否公平
 * synchronize非公平锁
 * ReentrantLoc两者都可以 默认公平锁 构造方法可以传入Boolean值 true是公平锁false是非用公平锁
 * <p>
 * <p>
 * 锁绑定多个条件condition
 * synchronize没有
 * ReentrantLock 用来实现分组唤醒需要唤醒的的线程们,可以精准唤醒 而不是像synchronize 要么随机唤醒一个线程 要么唤醒全部线程.
 */


/**
 *
 * 多线程之间按顺序调用 实现A->B->C   三个线程启动
 * A打印5次 B打印10次 次打印 15
 */

class ShareResource {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition cl = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                cl.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 2;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            c3.signal();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            cl.signal();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
          for (int i = 1; i <=10 ; i++) {
              shareResource.print5();
          }
      },"A").start();
        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                shareResource.print10();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                shareResource.print15();
            }
        },"C").start();
    }
}
