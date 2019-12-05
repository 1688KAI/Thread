import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * 集合类不安全的问题
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {

//        final HashMap<Object, Object> map = new HashMap<>();
//        final Map<Object, Object> map = Collections.synchronizedMap(new HashMap<>());
        final Map<Object, Object> map = new ConcurrentHashMap<>();

        for (int i = 1; i <=30 ; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }


    }

    private static void setNotSafe() {
        //        final Set<Object> set = new HashSet<>();
//        final Set<String> set = Collections.synchronizedSet(new HashSet<>());
        final Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i < 50; i++) {
          new Thread(()->{
              set.add(UUID.randomUUID().toString().substring(0,8));
              System.out.println(Thread.currentThread().getName()+"\t"+set);
          }).start();
        }
    }

    private static void listNotSafe() {
        //        Collections.synchronizedList()
//        Collections.synchronizedMap()
//        Collections.synchronizedSet()
        final List<String> list = new CopyOnWriteArrayList<>();
//        final List<String> list = Collections.synchronizedList(new ArrayList<>());
//        final List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(Thread.currentThread().getName()+"\t"+list);
            },String.valueOf(i)).start();
        }
        /**
         *
         *    不要只是会用
         * 1 .故障现象
         *  java.util.ConcurrentModificationException
         *
         * 2 .导致原因会用 只不过是一个API调用工程师
         *     多个线程并发修改 异常
         * 3 .解决方案
         *      3 .1 new Vector<>()
         *      3 .2 Collections.synchronizedList
         *      3. 3 new CopyOnWriteArrayList<>();
         * 4 .优化建议（同样的错误不犯第2次）
         *
         *
         *
         */}
}
